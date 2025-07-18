package com.agt.desafio_tecnico.controllers;

import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.RegistrarFimViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.VisualizarRegistroViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.servico.RegistroViagemServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagens")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Registro de Viagens", description = "Endpoints para gerenciamento de registros de viagens")
public class RegistroViagensController {
    private final RegistroViagemServico registroViagemServico;

    @PostMapping("/saida")
    @Operation(summary = "Registrar início de viagem",
               description = "Registra o início de uma viagem, associando um veículo e um motorista.")
    public ResponseEntity<VisualizarRegistroViagemDTO> registrarSaidaViagem(@RequestBody @Valid CriarViagemDTO dto) {
        VisualizarRegistroViagemDTO visualizarRegistroViagemDTO = registroViagemServico.registrarInicioViagem(dto);
        return ResponseEntity.ok(visualizarRegistroViagemDTO);
    }

    @PostMapping("/retorno")
    @Operation(summary = "Registrar fim de viagem",
               description = "Registra o fim de uma viagem, atualizando o status do veículo e do registro de viagem.")
    public ResponseEntity<VisualizarRegistroViagemDTO> registrarRetornoViagem(@RequestBody @Valid RegistrarFimViagemDTO dto) {
        VisualizarRegistroViagemDTO visualizarRegistroViagemDTO = registroViagemServico.registrarFimViagem(dto);
        return ResponseEntity.ok(visualizarRegistroViagemDTO);
    }

    @GetMapping("/registros")
    @Operation(summary = "Listar registros de viagens",
               description = "Lista todos os registros de viagens com a opção de aplicar filtros.")
    public ResponseEntity<List<VisualizarRegistroViagemDTO>> listarRegistrosViagens(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String placaVeiculo,
            @RequestParam(required = false) String dataSaida,
            @RequestParam(required = false) String dataRetorno,
            @RequestParam(required = false) String funcionarioMotoristaId,
            @RequestParam(required = false) String destino) {
        return ResponseEntity.ok(registroViagemServico.listarRegistrosViagensComFiltros());
    }
}
