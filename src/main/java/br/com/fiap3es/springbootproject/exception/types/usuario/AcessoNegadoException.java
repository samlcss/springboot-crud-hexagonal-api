package br.com.fiap3es.springbootproject.exception.types.usuario;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String message) {
        super(message);
    }
}