package com.agt.desafio_tecnico.dominio.registros_viagens.modelo;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.registros_viagens.enums.RegistroViagemStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class RegistroViagem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
    @ManyToOne
    @JoinColumn(name = "funcionario_motorista_id", nullable = false)
    private Funcionario funcionarioMotorista;
    @Column(nullable = false)
    private LocalDate dataSaida;
    private LocalDate dataRetorno;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistroViagemStatus status;
    @Column(nullable = false)
    private String destino;
    @Column(nullable = false)
    private List<String> passageiros;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.dataRetorno = null;
        this.status = RegistroViagemStatus.ABERTO;
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
