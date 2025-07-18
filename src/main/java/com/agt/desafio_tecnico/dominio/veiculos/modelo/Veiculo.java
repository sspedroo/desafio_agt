package com.agt.desafio_tecnico.dominio.veiculos.modelo;

import com.agt.desafio_tecnico.dominio.veiculos.dto.AtualizarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_veiculo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@DynamicInsert
@DynamicUpdate
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String placa;
    private String modelo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VeiculoStatus status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.status = VeiculoStatus.NO_PATIO; // Define o status como DISPONÍVEL ao criar
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public void atualizarVeiculo(AtualizarVeiculoDTO dto) {
        if (dto.placa() != null && StringUtils.hasText(dto.placa())) {
            this.placa = dto.placa();
        }
    }

}
