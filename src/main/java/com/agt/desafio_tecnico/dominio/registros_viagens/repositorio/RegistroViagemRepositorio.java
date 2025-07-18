package com.agt.desafio_tecnico.dominio.registros_viagens.repositorio;

import com.agt.desafio_tecnico.dominio.registros_viagens.enums.RegistroViagemStatus;
import com.agt.desafio_tecnico.dominio.registros_viagens.modelo.RegistroViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroViagemRepositorio extends JpaRepository<RegistroViagem, UUID> {

    @Query("""
           SELECT rv
           FROM RegistroViagem rv
           WHERE rv.status = 'ABERTO'
           AND rv.veiculo.placa = :placaVeiculo
           """)
    Optional<RegistroViagem> findByStatusAbertoAndByPlacaVeiculo(String placaVeiculo);


    @Query("""
           SELECT rv
           FROM RegistroViagem rv
           WHERE (:status IS NULL OR rv.status = :status)
           AND (:placaVeiculo IS NULL OR rv.veiculo.placa = :placaVeiculo)
           AND (:dataSaida IS NULL OR rv.dataSaida = :dataSaida)
           AND (:dataRetorno IS NULL OR rv.dataRetorno = :dataRetorno)
           AND (:funcionarioMotoristaId IS NULL OR rv.funcionarioMotorista.id = :funcionarioMotoristaId)
           AND (:destino IS NULL OR rv.destino = :destino)
           ORDER BY rv.dataSaida DESC
           """)
    List<RegistroViagem> findAllByFilters(@Param("status") RegistroViagemStatus status,
                                          @Param("placaVeiculo") String placaVeiculo,
                                          @Param("dataSaida") String dataSaida,
                                          @Param("dataRetorno") String dataRetorno,
                                          @Param("funcionarioMotoristaId") UUID funcionarioMotoristaId,
                                          @Param("destino") String destino
                                          );
}
