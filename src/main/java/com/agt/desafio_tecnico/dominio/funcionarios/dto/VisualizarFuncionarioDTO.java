package com.agt.desafio_tecnico.dominio.funcionarios.dto;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record VisualizarFuncionarioDTO(
        UUID id,
        String nome,
        String cnh,
        boolean emViagem,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
) {
    public static VisualizarFuncionarioDTO fromEntity(Funcionario funcionario) {
        return new VisualizarFuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCnh(),
                funcionario.isEmViagem(),
                funcionario.getCriadoEm(),
                funcionario.getAtualizadoEm() == null ? null : funcionario.getAtualizadoEm()
        );
    }
}
