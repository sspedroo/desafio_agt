package com.agt.desafio_tecnico.controllers;

import com.agt.desafio_tecnico.dominio.funcionarios.dto.AtualizarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.dto.CriarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.dto.VisualizarFuncionarioDTO;
import com.agt.desafio_tecnico.dominio.funcionarios.servico.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Tag(name = "Funcionários", description = "Endpoints para gerenciamento de funcionários")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    @PostMapping
    @Operation(summary = "Criar Funcionário", description = "Cria um novo funcionário no sistema")
    public ResponseEntity<VisualizarFuncionarioDTO> criarFuncionario(@RequestBody @Valid CriarFuncionarioDTO dto) {
        VisualizarFuncionarioDTO funcionarioCriado = funcionarioService.criarFuncionario(dto);

        return ResponseEntity.created(URI.create("/funcionarios/" + funcionarioCriado.id()))
                .body(funcionarioCriado);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar Funcionário", description = "Atualiza os dados de um funcionário existente")
    public ResponseEntity<VisualizarFuncionarioDTO> atualizarFuncionario(
            @PathVariable("id") UUID id,
            @RequestBody @Valid AtualizarFuncionarioDTO dto) {
        VisualizarFuncionarioDTO funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, dto);

        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @GetMapping
    @Operation(summary = "Listar Funcionários", description = "Lista todos os funcionários cadastrados no sistema")
    public ResponseEntity<List<VisualizarFuncionarioDTO>> listarFuncionarios() {
        List<VisualizarFuncionarioDTO> visualizarFuncionarioDTOS = funcionarioService.listarFuncionarios();

        return ResponseEntity.ok(visualizarFuncionarioDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Visualizar Funcionário por ID", description = "Obtém os detalhes de um funcionário específico pelo ID")
    public ResponseEntity<VisualizarFuncionarioDTO> visualizarFuncionarioPorId(@PathVariable("id") UUID id) {
        VisualizarFuncionarioDTO funcionario = funcionarioService.visualizarFuncionarioPorId(id);

        return ResponseEntity.ok(funcionario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Funcionário", description = "Exclui um funcionário do sistema pelo ID")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable("id") UUID id) {
        funcionarioService.excluirFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}
