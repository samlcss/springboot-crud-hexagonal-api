package br.com.fiap3es.springbootproject.exception.types.usuario;

public class UsuarioInstrutorNaoEncontradoException extends RuntimeException {
    public UsuarioInstrutorNaoEncontradoException(String message) {
        super(message);
    }
}