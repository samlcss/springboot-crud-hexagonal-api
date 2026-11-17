package br.com.fiap3es.springbootproject.exception.types.instrucao;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String message) {
        super(message);
    }
}