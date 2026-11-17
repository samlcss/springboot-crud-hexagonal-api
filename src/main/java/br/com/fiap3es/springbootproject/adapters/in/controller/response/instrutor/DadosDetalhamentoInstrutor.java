package br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.endereco.DadosEndereco;
import br.com.fiap3es.springbootproject.application.core.domain.enums.Especialidade;

public record DadosDetalhamentoInstrutor(
        Long id,
        Boolean ativo,
        String nome,
        String email,
        String telefone,
        String cnh,
        Especialidade especialidade,
        DadosEndereco endereco) {
}