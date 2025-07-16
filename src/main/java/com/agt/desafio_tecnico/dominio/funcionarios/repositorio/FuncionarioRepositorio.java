package com.agt.desafio_tecnico.dominio.funcionarios.repositorio;

import com.agt.desafio_tecnico.dominio.funcionarios.modelo.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FuncionarioRepositorio extends JpaRepository<Funcionario, UUID> {

    boolean existsByCnh(String cnh);
}
