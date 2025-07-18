package com.agt.desafio_tecnico.dominio.registros_viagens.repositorio;

import com.agt.desafio_tecnico.dominio.registros_viagens.modelo.RegistroViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
