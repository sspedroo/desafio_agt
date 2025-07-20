package com.agt.desafio_tecnico.dominio.registros_viagens.validacao;

import com.agt.desafio_tecnico.dominio.registros_viagens.dto.RegistrarFimViagemDTO;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;

public interface RetornoViagemValidacao {
    void validate(Veiculo veiculo, RegistrarFimViagemDTO registrarFimViagemDTO);
}
