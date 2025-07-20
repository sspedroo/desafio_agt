package com.agt.desafio_tecnico.dominio.registros_viagens.validacao;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.agt.desafio_tecnico.excecoes.VeiculoJaEstaEmViagemException;
import org.springframework.stereotype.Component;

@Component
public class InicioVeiculoNoPatioValidacaoInicio implements InicioViagemValidacao {
    @Override
    public void validate(Veiculo veiculo, Funcionario funcionario, CriarViagemDTO criarViagemDTO) {
        if (!veiculo.getStatus().equals(VeiculoStatus.NO_PATIO)) {
            throw new VeiculoJaEstaEmViagemException("Não é possível iniciar uma viagem com o veículo de placa " + criarViagemDTO.placaVeiculo() + " pois" +
                    " o status do veículo é " + veiculo.getStatus() + ". O veículo deve estar no pátio para iniciar uma viagem.");
        }
    }
}
