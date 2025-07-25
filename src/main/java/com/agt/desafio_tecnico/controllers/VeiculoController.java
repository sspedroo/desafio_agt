package com.agt.desafio_tecnico.controllers;


import com.agt.desafio_tecnico.dominio.veiculos.dto.AtualizarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.dto.CriarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.dto.VisualizarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.servico.VeiculoServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Veículo", description = "Endpoints de Gerenciamento de veículos")
public class VeiculoController {
    private final VeiculoServico veiculoServico;

    @PostMapping
    @Operation(summary = "Criar Veículo", description = "Cria um novo veículo no sistema.")
    public ResponseEntity<VisualizarVeiculoDTO> criarVeiculo(@RequestBody @Valid CriarVeiculoDTO dto) {
        VisualizarVeiculoDTO visualizarVeiculoDTO = veiculoServico.criarVeiculo(dto);

        return ResponseEntity
                .created(URI.create("/veiculos/" + visualizarVeiculoDTO.id()))
                .body(visualizarVeiculoDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar Veículo", description = "Atualiza os detalhes de um veículo existente pelo ID.")
    public ResponseEntity<VisualizarVeiculoDTO> atualizarVeiculo(@PathVariable("id") UUID id,
                                                                  @RequestBody @Valid AtualizarVeiculoDTO dto) {
        VisualizarVeiculoDTO visualizarVeiculoDTO = veiculoServico.atualizarVeiculo(id, dto);

        return ResponseEntity.ok(visualizarVeiculoDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Visualizar Veículo por ID", description = "Obtém os detalhes de um veículo específico pelo ID.")
    public ResponseEntity<VisualizarVeiculoDTO> visualizarVeiculoPorId(@PathVariable("id") UUID id) {
        VisualizarVeiculoDTO visualizarVeiculoDTO = veiculoServico.visualizarVeiculoPorId(id);

        return ResponseEntity.ok(visualizarVeiculoDTO);
    }

    @GetMapping
    @Operation(summary = "Listar Veículos", description = "Lista todos os veículos com filtros opcionais.")
    public ResponseEntity<List<VisualizarVeiculoDTO>> listarVeiculos(@RequestParam(required=false) VeiculoStatus status,
                                                                     @RequestParam(required=false) String placa,
                                                                     @RequestParam(required=false) String modelo
                                                                     ) {

        List<VisualizarVeiculoDTO> visualizarVeiculoDTOS = veiculoServico.listarVeiculos(status, placa, modelo);

        return ResponseEntity.ok(visualizarVeiculoDTOS);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Veículo", description = "Exclui um veículo do sistema pelo ID.")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable("id") UUID id) {
        veiculoServico.excluirVeiculo(id);
        return ResponseEntity.noContent().build();
    }
}
