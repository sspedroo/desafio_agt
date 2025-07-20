package com.agt.desafio_tecnico.dominio.registros_viagens.servico;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import com.agt.desafio_tecnico.dominio.funcionarios.repositorio.FuncionarioRepositorio;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.CriarViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.RegistrarFimViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.dto.VisualizarRegistroViagemDTO;
import com.agt.desafio_tecnico.dominio.registros_viagens.enums.RegistroViagemStatus;
import com.agt.desafio_tecnico.dominio.registros_viagens.modelo.RegistroViagem;
import com.agt.desafio_tecnico.dominio.registros_viagens.repositorio.RegistroViagemRepositorio;
import com.agt.desafio_tecnico.dominio.registros_viagens.validacao.InicioViagemValidacao;
import com.agt.desafio_tecnico.dominio.registros_viagens.validacao.RetornoViagemValidacao;
import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import com.agt.desafio_tecnico.dominio.veiculos.repositorio.VeiculoRepositorio;
import com.agt.desafio_tecnico.excecoes.RecursoNaoEncontradoException;
import com.agt.desafio_tecnico.excecoes.VeiculoJaEstaEmViagemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final List<InicioViagemValidacao> validacoesDeInicioDeRegistroViagem;
    private final List<RetornoViagemValidacao> validacoesDeRetornoDeRegistroViagem;

    @Transactional
    @CacheEvict(value = "listaDeVeiculos", allEntries = true) // Invalida TODAS as entradas do cache
    public VisualizarRegistroViagemDTO registrarInicioViagem(CriarViagemDTO dto) {
        Veiculo veiculo = veiculoRepositorio.findByPlaca(dto.placaVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com a placa: " + dto.placaVeiculo()));
        Funcionario funcionario = funcionarioRepositorio.findById(dto.funcionarioMotoristaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + dto.funcionarioMotoristaId()));

        // Validações de início de registro de viagem
        for (InicioViagemValidacao validator : validacoesDeInicioDeRegistroViagem) {
            validator.validate(veiculo, funcionario, dto);
        }

        veiculo.setStatus(VeiculoStatus.EM_VIAGEM);
        funcionario.setEmViagem(true);
        RegistroViagem registroViagem = RegistroViagem.builder()
                .veiculo(veiculo)
                .funcionarioMotorista(funcionario)
                .dataSaida(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .destino(dto.destino())
                .passageiros(dto.passageiros())
                .build();

        veiculoRepositorio.save(veiculo);
        registroViagemRepositorio.save(registroViagem);
        funcionarioRepositorio.save(funcionario);

        return VisualizarRegistroViagemDTO.fromEntity(registroViagem);
    }

    @Transactional
    @CacheEvict(value = "listaDeVeiculos", allEntries = true) // Invalida TODAS as entradas do cache
    public VisualizarRegistroViagemDTO registrarFimViagem(RegistrarFimViagemDTO dto) {
        Veiculo veiculo = veiculoRepositorio.findByPlaca(dto.placaVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com a placa: " + dto.placaVeiculo()));

       for (RetornoViagemValidacao validator : validacoesDeRetornoDeRegistroViagem) {
            validator.validate(veiculo, dto);
        }

        RegistroViagem registroViagem = registroViagemRepositorio.findByStatusAbertoAndByPlacaVeiculo(dto.placaVeiculo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de viagem com status ABERTO não encontrado para o veículo de " +
                        "placa: " + dto.placaVeiculo()));

        registroViagem.setDataRetorno(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        registroViagem.setStatus(RegistroViagemStatus.FINALIZADO);
        veiculo.setStatus(VeiculoStatus.NO_PATIO);
        registroViagem.getFuncionarioMotorista().setEmViagem(false);

        registroViagemRepositorio.save(registroViagem);
        veiculoRepositorio.save(veiculo);
        funcionarioRepositorio.save(registroViagem.getFuncionarioMotorista());

        return VisualizarRegistroViagemDTO.fromEntity(registroViagem);
    }

    @Transactional
    public List<VisualizarRegistroViagemDTO> listarRegistrosViagensComFiltros() {
        List<RegistroViagem> registrosViagens = registroViagemRepositorio.findAll();
        return registrosViagens.stream()
                .sorted((rv1, rv2) -> rv2.getCriadoEm().compareTo(rv1.getCriadoEm())) // Ordena por data de criação decrescente
                .map(VisualizarRegistroViagemDTO::fromEntity)
                .toList();
    }
}
