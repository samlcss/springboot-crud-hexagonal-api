package br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Role;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(

        @NotNull
        Long id,
        Boolean ativo,
        String login,
        Role perfil) {
}