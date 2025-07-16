package com.agt.desafio_tecnico.dominio.funcionarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AtualizarFuncionarioDTO(
        String nome,
        @Pattern(regexp = "^\\d{11}$", message = "CNH inválida, deve ter 11 dígitos")
        String cnh
) {
}
