package com.agt.desafio_tecnico.dominio.funcionarios.servico;

import com.agt.desafio_tecnico.dominio.funcionarios.dto.AtualizarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.dto.CriarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.dto.VisualizarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.funcionarios.repositorio.FuncionarioRepositorio;
import com.agt.desafio_tecnico.excecoes.CnhJaCadastradaException;
import com.agt.desafio_tecnico.excecoes.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {
    private final FuncionarioRepositorio funcionarioRepositorio;

    @Transactional
    public VisualizarFuncionarioDTO criarFuncionario(CriarFuncionarioDTO dto) {
        if (!dto.cnh().isBlank() && funcionarioRepositorio.existsByCnh(dto.cnh())) {
            log.warn("Tentativa de criar funcionário com CNH já existente: {}", dto.cnh());
            throw new CnhJaCadastradaException(dto.cnh());
        }

        Funcionario funcionario = Funcionario.builder()
                .nome(dto.nome())
                .cnh(dto.cnh())
                .build();

        funcionarioRepositorio.save(funcionario);

        return VisualizarFuncionarioDTO.fromEntity(funcionario);
    }

    @Transactional
    public VisualizarFuncionarioDTO atualizarFuncionario(UUID id, AtualizarFuncionarioDTO dto) {
        Funcionario funcionario = funcionarioRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));

        if (dto != null && StringUtils.hasText(dto.cnh()) && !funcionario.getCnh().equals(dto.cnh()) && funcionarioRepositorio.existsByCnh(dto.cnh())) {
            log.warn("Tentativa de atualizar funcionário com CNH já existente: {}", dto.cnh());
            throw new CnhJaCadastradaException(dto.cnh());
        }

        funcionario.atualizarDados(dto);

        funcionarioRepositorio.save(funcionario);

        return VisualizarFuncionarioDTO.fromEntity(funcionario);
    }

    @Transactional(readOnly = true)
    public VisualizarFuncionarioDTO visualizarFuncionarioPorId(UUID id) {
        return funcionarioRepositorio.findById(id)
                .map(VisualizarFuncionarioDTO::fromEntity)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<VisualizarFuncionarioDTO> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepositorio.findAll();
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getCriadoEm).reversed())
                .map(VisualizarFuncionarioDTO::fromEntity)
                .toList();
    }

    @Transactional
    public void excluirFuncionario(UUID id) {

        if (!funcionarioRepositorio.existsById(id)) {
            log.warn("Tentativa de excluir funcionário com ID não encontrado: {}", id);
            throw new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id);
        }

        funcionarioRepositorio.deleteById(id);
    }
}
