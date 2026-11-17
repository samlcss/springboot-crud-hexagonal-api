package br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Especialidade;

public record DadosListagemInstrutor(
        Long id,
        String nome,
        String email,
        String cnh,
        Especialidade especialidade) {
}