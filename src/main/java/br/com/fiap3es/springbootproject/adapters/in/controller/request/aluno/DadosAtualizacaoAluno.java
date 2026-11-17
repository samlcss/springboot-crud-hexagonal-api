package br.com.fiap3es.springbootproject.adapters.in.controller.request.aluno;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}