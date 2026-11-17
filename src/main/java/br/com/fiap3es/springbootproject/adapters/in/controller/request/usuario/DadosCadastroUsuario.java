package br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(

        @NotBlank
        String login,

        @NotBlank
        String senha,

        @NotNull
        Role perfil) {
}