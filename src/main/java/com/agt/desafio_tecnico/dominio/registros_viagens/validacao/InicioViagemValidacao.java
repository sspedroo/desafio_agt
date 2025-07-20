package com.agt.desafio_tecnico.dominio.registros_viagens.validacao;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import org.springframework.stereotype.Component;

@Component
public interface InicioViagemValidacao {
    void validate(Veiculo veiculo, Funcionario funcionario, CriarViagemDTO criarViagemDTO);
}
