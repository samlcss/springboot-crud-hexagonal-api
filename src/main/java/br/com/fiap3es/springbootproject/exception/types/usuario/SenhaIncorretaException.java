package br.com.fiap3es.springbootproject.exception.types.usuario;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }
}