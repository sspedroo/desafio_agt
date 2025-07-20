package com.agt.desafio_tecnico.dominio.registros_viagens.validacao;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.agt.desafio_tecnico.excecoes.FuncionarioJaEmViagemException;

public class InicioFuncionarioEmViagemValidacao implements InicioViagemValidacao {
    @Override
    public void validate(Veiculo veiculo, Funcionario funcionario, CriarViagemDTO criarViagemDTO) {
        if (funcionario.isEmViagem()) {
            throw new FuncionarioJaEmViagemException("Não é possível iniciar uma viagem com o funcionário " + funcionario.getNome()
                    + " pois ele já está em viagem.");
        }
    }
}
