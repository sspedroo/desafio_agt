package com.agt.desafio_tecnico.dominio.registros_viagens.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CriarViagemDTO(
        @NotBlank(message = "A placa do veículo não pode estar em branco")
        String placaVeiculo,
        @NotNull(message = "O ID do funcionário motorista não pode ser nulo")
        UUID funcionarioMotoristaId,
        @NotBlank(message = "O destino não pode estar em branco")
        @Schema(defaultValue = "Maracai")
        String destino,
        String passageiros
) {
}
