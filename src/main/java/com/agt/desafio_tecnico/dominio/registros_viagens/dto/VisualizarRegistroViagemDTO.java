package com.agt.desafio_tecnico.dominio.registros_viagens.dto;

import com.agt.desafio_tecnico.dominio.funcionarios.dto.VisualizarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.enums.RegistroViagemStatus;
import com.agt.desafio_tecnico.dominio.registros_viagens.modelo.RegistroViagem;
import com.agt.desafio_tecnico.dominio.veiculos.dto.VisualizarVeiculoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VisualizarRegistroViagemDTO (
        UUID id,
        VisualizarVeiculoDTO veiculo,
        VisualizarFuncionarioDTO motorista,
        String destino,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataSaida,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataRetorno,
        List<String> passageiros,
        RegistroViagemStatus status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
){
    public static VisualizarRegistroViagemDTO fromEntity(RegistroViagem registroViagem) {
        return new VisualizarRegistroViagemDTO(
                registroViagem.getId(),
                VisualizarVeiculoDTO.fromEntity(registroViagem.getVeiculo()),
                VisualizarFuncionarioDTO.fromEntity(registroViagem.getFuncionarioMotorista()),
                registroViagem.getDestino(),
                registroViagem.getDataSaida(),
                registroViagem.getDataRetorno() != null ? registroViagem.getDataRetorno() : null,
                registroViagem.getPassageiros(),
                registroViagem.getStatus(),
                registroViagem.getCriadoEm(),
                registroViagem.getAtualizadoEm() != null ? registroViagem.getAtualizadoEm() : null
        );
    }
}
