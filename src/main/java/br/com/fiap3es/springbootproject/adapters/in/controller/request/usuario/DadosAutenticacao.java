package br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(

        @NotBlank
        String login,

        @NotBlank
        String senha) {
}