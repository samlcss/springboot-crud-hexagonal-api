package br.com.fiap3es.springbootproject.exception.types.usuario;

public class UsuarioAlunoNaoEncontradoException extends RuntimeException {
    public UsuarioAlunoNaoEncontradoException(String message) {
        super(message);
    }
}