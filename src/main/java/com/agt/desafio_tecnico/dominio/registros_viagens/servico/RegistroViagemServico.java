package com.agt.desafio_tecnico.dominio.registros_viagens.servico;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.funcionarios.repositorio.FuncionarioRepositorio;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.RegistrarFimViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.VisualizarRegistroViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.enums.RegistroViagemStatus;
import com.agt.desafio_tecnico.dominio.registros_viagens.modelo.RegistroViagem;
import com.agt.desafio_tecnico.dominio.registros_viagens.repositorio.RegistroViagemRepositorio;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.agt.desafio_tecnico.dominio.veiculos.repositorio.VeiculoRepositorio;
import com.agt.desafio_tecnico.excecoes.RecursoNaoEncontradoException;
import com.agt.desafio_tecnico.excecoes.VeiculoJaEstaEmViagemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistroViagemServico {
    private final RegistroViagemRepositorio registroViagemRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final FuncionarioRepositorio funcionarioRepositorio;

    @Transactional
    public VisualizarRegistroViagemDTO registrarInicioViagem(CriarViagemDTO dto) {
        Veiculo veiculo = veiculoRepositorio.findByPlaca(dto.placaVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com a placa: " + dto.placaVeiculo()));
        Funcionario funcionario = funcionarioRepositorio.findById(dto.funcionarioMotoristaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + dto.funcionarioMotoristaId()));

        if (!veiculo.getStatus().equals(VeiculoStatus.NO_PATIO)) {
            throw new VeiculoJaEstaEmViagemException("Não é possível iniciar uma viagem com o veículo de placa " + dto.placaVeiculo() + " pois" +
                    " o status do veículo é " + veiculo.getStatus() + ". O veículo deve estar no pátio para iniciar uma viagem.");
        }

        veiculo.setStatus(VeiculoStatus.EM_VIAGEM);
        RegistroViagem registroViagem = RegistroViagem.builder()
                .veiculo(veiculo)
                .funcionarioMotorista(funcionario)
                .dataSaida(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .destino(dto.destino())
                .passageiros(dto.passageiros())
                .build();

        veiculoRepositorio.save(veiculo);
        registroViagemRepositorio.save(registroViagem);

        return VisualizarRegistroViagemDTO.fromEntity(registroViagem);
    }

    @Transactional
    public VisualizarRegistroViagemDTO registrarFimViagem(RegistrarFimViagemDTO dto) {
        Veiculo veiculo = veiculoRepositorio.findByPlaca(dto.placaVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com a placa: " + dto.placaVeiculo()));

        if (!veiculo.getStatus().equals(VeiculoStatus.EM_VIAGEM)) {
            throw new VeiculoJaEstaEmViagemException("Não é possível finalizar a viagem do veículo de placa " + dto.placaVeiculo() + " pois" +
                    " o status do veículo é " + veiculo.getStatus() + ". O veículo deve estar em viagem para finalizar.");
        }

        RegistroViagem registroViagem = registroViagemRepositorio.findByStatusAbertoAndByPlacaVeiculo(dto.placaVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de viagem com status ABERTO não encontrado para o veículo de " +
                        "placa: " + dto.placaVeiculo()));

        registroViagem.setDataRetorno(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        registroViagem.setStatus(RegistroViagemStatus.FINALIZADO);
        veiculo.setStatus(VeiculoStatus.NO_PATIO);

        registroViagemRepositorio.save(registroViagem);
        veiculoRepositorio.save(veiculo);

        return VisualizarRegistroViagemDTO.fromEntity(registroViagem);
    }

    @Transactional
    public List<VisualizarRegistroViagemDTO> listarRegistrosViagensComFiltros() {
        List<RegistroViagem> registrosViagens = registroViagemRepositorio.findAll();
        return registrosViagens.stream()
                .map(VisualizarRegistroViagemDTO::fromEntity)
                .toList();
    }
}
