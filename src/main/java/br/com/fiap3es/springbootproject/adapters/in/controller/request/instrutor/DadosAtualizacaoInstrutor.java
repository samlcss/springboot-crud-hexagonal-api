package br.com.fiap3es.springbootproject.adapters.in.controller.request.instrutor;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoInstrutor(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}