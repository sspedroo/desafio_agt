package com.agt.desafio_tecnico.dominio.veiculos.repositorio;

import com.agt.desafio_tecnico.dominio.veiculos.enums.VeiculoStatus;
import com.agt.desafio_tecnico.dominio.veiculos.modelo.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepositorio extends JpaRepository<Veiculo, UUID> {

    @Query("""
           SELECT v
           FROM Veiculo v
           WHERE (:status IS NULL OR v.status = :status)
           AND (:placa IS NULL OR v.placa = :placa)
           AND (:modelo IS NULL OR v.modelo = :modelo)
           """)
    Page<Veiculo> findByFilters(@Param("status") VeiculoStatus status,
                                @Param("placa") String placa,
                                @Param("modelo") String modelo,
                                Pageable pageable);


    boolean existsByPlaca(String placa);

    Optional<Veiculo> findByPlaca(String placa);
}
