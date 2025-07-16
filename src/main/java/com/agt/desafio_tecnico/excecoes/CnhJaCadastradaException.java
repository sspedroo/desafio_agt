package com.agt.desafio_tecnico.excecoes;

public class CnhJaCadastradaException extends ConflitoDeDadosException {
    public CnhJaCadastradaException(String cnh) {
        super("Já existe um funcionário cadastrado com a CNH: " + cnh);
    }
}
