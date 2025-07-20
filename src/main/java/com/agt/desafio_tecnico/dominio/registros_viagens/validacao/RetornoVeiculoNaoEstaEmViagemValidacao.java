package com.agt.desafio_tecnico.dominio.registros_viagens.validacao;

import com.agt.desafio_tecnico.dominio.registros_viagens.dto.RegistrarFimViagemDTO;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.agt.desafio_tecnico.excecoes.VeiculoJaEstaEmViagemException;

public class RetornoVeiculoNaoEstaEmViagemValidacao implements RetornoViagemValidacao{
    @Override
    public void validate(Veiculo veiculo, RegistrarFimViagemDTO registrarFimViagemDTO) {
        if (!veiculo.getStatus().equals(VeiculoStatus.EM_VIAGEM)) {
            throw new VeiculoJaEstaEmViagemException("Não é possível finalizar a viagem do veículo de placa " + registrarFimViagemDTO.placaVeiculo() + " pois" +
                    " o status do veículo é " + veiculo.getStatus() + ". O veículo deve estar em viagem para finalizar.");
        }
    }
}
