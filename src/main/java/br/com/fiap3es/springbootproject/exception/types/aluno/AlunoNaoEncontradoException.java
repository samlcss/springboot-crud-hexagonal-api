package br.com.fiap3es.springbootproject.exception.types.aluno;

public class AlunoNaoEncontradoException extends RuntimeException {
    public AlunoNaoEncontradoException(String message) {
        super(message);
    }
}