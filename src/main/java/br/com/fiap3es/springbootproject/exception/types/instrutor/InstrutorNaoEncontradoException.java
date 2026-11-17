package br.com.fiap3es.springbootproject.exception.types.instrutor;

public class InstrutorNaoEncontradoException extends RuntimeException {
    public InstrutorNaoEncontradoException(String message) {
        super(message);
    }
}