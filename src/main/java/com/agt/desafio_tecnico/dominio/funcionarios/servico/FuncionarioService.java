package com.agt.desafio_tecnico.dominio.funcionarios.servico;

import com.agt.desafio_tecnico.dominio.funcionarios.repositorio.FuncionarioRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {
    private final FuncionarioRepositorio funcionarioRepositorio;

}
