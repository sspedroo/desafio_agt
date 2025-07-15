package com.agt.desafio_tecnico.dominio.funcionarios.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarFuncionarioDTO(
        @NotBlank(message = "O nome do funcionário não pode estar vazio")
        String nome,
        String cnh
) {
}
