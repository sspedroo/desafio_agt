package com.agt.desafio_tecnico.dominio.veiculos.dto;

import com.agt.desafio_tecnico.dominio.veiculos.enums.CarroStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record VisualizarVeiculoDTO (
        UUID id,
        String placa,
        String modelo,
        CarroStatus status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
) {
    public static VisualizarVeiculoDTO fromEntity(Veiculo veiculo) {
        return new VisualizarVeiculoDTO(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getStatus(),
                veiculo.getCriadoEm(),
                veiculo.getAtualizadoEm() != null ? veiculo.getAtualizadoEm() : null
        );
    }
}

