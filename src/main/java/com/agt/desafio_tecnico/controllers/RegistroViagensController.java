package com.agt.desafio_tecnico.controllers;

import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.RegistrarFimViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.VisualizarRegistroViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.servico.RegistroViagemServico;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viagens")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Registro de Viagens", description = "Endpoints para gerenciamento de registros de viagens")
public class RegistroViagensController {
    private final RegistroViagemServico registroViagemServico;

    @PostMapping("/saida")
    public ResponseEntity<VisualizarRegistroViagemDTO> registrarSaidaViagem(@RequestBody @Valid CriarViagemDTO dto) {
        VisualizarRegistroViagemDTO visualizarRegistroViagemDTO = registroViagemServico.registrarInicioViagem(dto);
        return ResponseEntity.ok(visualizarRegistroViagemDTO);
    }

    @PostMapping("/retorno")
    public ResponseEntity<VisualizarRegistroViagemDTO> registrarRetornoViagem(@RequestBody @Valid RegistrarFimViagemDTO dto) {
        VisualizarRegistroViagemDTO visualizarRegistroViagemDTO = registroViagemServico.registrarFimViagem(dto);
        return ResponseEntity.ok(visualizarRegistroViagemDTO);
    }
}
