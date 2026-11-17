package br.com.fiap3es.springbootproject.exception.types.instrucao;

public class InstrucaoNaoEncontradaException extends RuntimeException {
    public InstrucaoNaoEncontradaException(String message) {
        super(message);
    }
}