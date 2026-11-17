package br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.endereco.DadosEndereco;

public record DadosDetalhamentoAluno(
        Long id,
        Boolean ativo,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco) {
}