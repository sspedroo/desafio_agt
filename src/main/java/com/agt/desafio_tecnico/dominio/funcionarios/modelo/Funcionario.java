package com.agt.desafio_tecnico.dominio.funcionarios.modelo;


import com.agt.desafio_tecnico.dominio.funcionarios.dto.AtualizarFuncionarioDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@DynamicUpdate
@DynamicInsert
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true)
    private String cnh;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private boolean emViagem;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.emViagem = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public void atualizarDados(AtualizarFuncionarioDTO dto) {
        if (StringUtils.hasText(dto.nome())) {
            this.nome = dto.nome();
        }
        if (StringUtils.hasText(dto.cnh())) {
            this.cnh = dto.cnh();
        }
    }
}
