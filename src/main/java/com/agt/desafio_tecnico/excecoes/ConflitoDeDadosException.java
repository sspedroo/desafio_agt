package com.agt.desafio_tecnico.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitoDeDadosException extends RuntimeException {
    public ConflitoDeDadosException(String message) {
        super(message);
    }
}
