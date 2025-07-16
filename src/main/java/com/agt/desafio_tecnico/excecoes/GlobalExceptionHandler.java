package com.agt.desafio_tecnico.excecoes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pb.setTitle("Recurso não encontrado");
        pb.setType(URI.create("about:blank"));

        if (request instanceof ServletWebRequest) {
            pb.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        }

        pb.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pb);
    }

    @ExceptionHandler(ConflitoDeDadosException.class)
    public ResponseEntity<ProblemDetail> handleConflitoDeDadosException(ConflitoDeDadosException ex, WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        pb.setTitle("Conflito de Dados");
        pb.setType(URI.create("about:blank"));

        if (request instanceof ServletWebRequest) {
            pb.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        }

        pb.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return ResponseEntity.of(pb).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidacoesExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos da requisição estão inválidos.");
        pb.setTitle("Erro de Validação");
        pb.setType(URI.create("about:blank"));

        if (request instanceof ServletWebRequest) {
            pb.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        }

        List<Map<String, String>> erros = ex.getBindingResult()
                .getAllErrors().
                stream()
                .map(error -> {
                    return Map.of("campo", ((FieldError) error).getField(), "mensagem", error.getDefaultMessage());
                }).toList();

        pb.setProperty("erros", erros);
        pb.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return ResponseEntity.of(pb).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex, WebRequest request) {
        log.error("Erro interno do servidor: {}", ex.getMessage(), ex);

        ProblemDetail pb = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno no servidor.");
        pb.setTitle("Erro Interno do Servidor");
        pb.setType(URI.create("about:blank"));

        if (request instanceof ServletWebRequest) {
            pb.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        }

        pb.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pb);
    }
}
