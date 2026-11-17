package br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno;

public record DadosListagemAluno(
        Long id,
        String nome,
        String email,
        String cpf) {
}