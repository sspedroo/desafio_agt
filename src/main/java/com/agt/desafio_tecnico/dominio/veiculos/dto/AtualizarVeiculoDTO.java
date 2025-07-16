package com.agt.desafio_tecnico.dominio.veiculos.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizarVeiculoDTO(
        @NotBlank(message = "A placa do veículo não pode estar em branco")
        String placa
) {
}
