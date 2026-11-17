package br.com.fiap3es.springbootproject.exception.types.usuario;

public class UsuarioInativoException extends RuntimeException {
    public UsuarioInativoException(String message) {
        super(message);
    }
}