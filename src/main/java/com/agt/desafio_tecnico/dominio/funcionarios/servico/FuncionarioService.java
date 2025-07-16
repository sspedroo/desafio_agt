package com.agt.desafio_tecnico.dominio.funcionarios.servico;

import com.agt.desafio_tecnico.dominio.funcionarios.dto.CriarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.dto.VisualizarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.funcionarios.repositorio.FuncionarioRepositorio;
import com.agt.desafio_tecnico.excecoes.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {
    private final FuncionarioRepositorio funcionarioRepositorio;

    @Transactional
    public VisualizarFuncionarioDTO criarFuncionario(CriarFuncionarioDTO dto) {
        Funcionario funcionario = Funcionario.builder()
                .nome(dto.nome())
                .cnh(dto.cnh())
                .build();

        funcionarioRepositorio.save(funcionario);

        return VisualizarFuncionarioDTO.fromEntity(funcionario);
    }

    @Transactional(readOnly = true)
    public VisualizarFuncionarioDTO visualizarFuncionarioPorId(UUID id) {
        return funcionarioRepositorio.findById(id)
                .map(VisualizarFuncionarioDTO::fromEntity)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));
    }

    @Transactional
    public List<VisualizarFuncionarioDTO> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepositorio.findAll();
        return funcionarios.stream()
                .map(VisualizarFuncionarioDTO::fromEntity)
                .toList();
    }
}
