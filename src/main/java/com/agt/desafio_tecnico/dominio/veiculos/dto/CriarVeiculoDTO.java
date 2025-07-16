package com.agt.desafio_tecnico.dominio.veiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CriarVeiculoDTO(
        @NotBlank(message = "A placa do veículo não pode estar em branco")
        @Schema(
                description = "Placa do veículo",
                example = "ABC1D23",
                defaultValue = "BRA-2E19"
        )
        String placa,
        @NotBlank(message = "O modelo do veículo não pode estar em branco")
        @Schema(
                description = "Modelo do veículo",
                example = "Gol",
                defaultValue = "Gol"
        )
        String modelo
) {
}
