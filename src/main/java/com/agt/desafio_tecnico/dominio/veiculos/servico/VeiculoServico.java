package com.agt.desafio_tecnico.dominio.veiculos.servico;

import com.agt.desafio_tecnico.dominio.veiculos.dto.AtualizarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.dto.CriarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.dto.VisualizarVeiculoDTO;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.agt.desafio_tecnico.dominio.veiculos.repositorio.VeiculoRepositorio;
import com.agt.desafio_tecnico.excecoes.ConflitoDeDadosException;
import com.agt.desafio_tecnico.excecoes.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VeiculoServico {
    private final VeiculoRepositorio veiculoRepositorio;

    @Transactional
    public VisualizarVeiculoDTO criarVeiculo(CriarVeiculoDTO dto) {

        if (veiculoRepositorio.existsByPlaca(dto.placa())) {
            throw new ConflitoDeDadosException("Já existe um veículo cadastrado com a placa: " + dto.placa());
        }

        Veiculo veiculo = Veiculo.builder()
                .placa(dto.placa())
                .modelo(dto.modelo())
                .build();

        veiculoRepositorio.save(veiculo);

        return VisualizarVeiculoDTO.fromEntity(veiculo);
    }

    @Transactional
    public VisualizarVeiculoDTO atualizarVeiculo(UUID id, AtualizarVeiculoDTO dto) {
        Veiculo veiculo = veiculoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com o ID: " + id));

        if (veiculo.getPlaca().equals(dto.placa())) {
            throw new ConflitoDeDadosException("A placa informada já é a mesma do veículo com ID: " + id);
        } else if (veiculoRepositorio.existsByPlaca(dto.placa())) {
            throw new ConflitoDeDadosException("Já existe um veículo cadastrado com a placa: " + dto.placa());
        }

        veiculo.atualizarVeiculo(dto);

        veiculoRepositorio.save(veiculo);

        return VisualizarVeiculoDTO.fromEntity(veiculo);
    }

    @Transactional(readOnly = true)
    public VisualizarVeiculoDTO visualizarVeiculoPorId(UUID id) {
        return veiculoRepositorio.findById(id)
                .map(VisualizarVeiculoDTO::fromEntity)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<VisualizarVeiculoDTO> listarVeiculos(VeiculoStatus status, String placa, String modelo, Pageable pageable) {
        Page<Veiculo> veiculos = veiculoRepositorio.findByFilters(status, placa, modelo, pageable);

        return veiculos.map(VisualizarVeiculoDTO::fromEntity);
    }

    @Transactional
    public void excluirVeiculo(UUID id) {
        if (!veiculoRepositorio.existsById(id)) {
            throw new RecursoNaoEncontradoException("Veículo não encontrado com o ID: " + id);
        }
        veiculoRepositorio.deleteById(id);
    }
}
