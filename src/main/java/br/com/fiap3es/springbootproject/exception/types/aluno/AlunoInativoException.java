package br.com.fiap3es.springbootproject.exception.types.aluno;

public class AlunoInativoException extends RuntimeException {
    public AlunoInativoException(String message) {
        super(message);
    }
}