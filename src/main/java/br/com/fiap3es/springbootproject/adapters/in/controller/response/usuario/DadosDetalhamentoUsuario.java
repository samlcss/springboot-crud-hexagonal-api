package br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Role;

public record DadosDetalhamentoUsuario(
    Long id,
    Boolean ativo,
    String login,
    Role perfil) {
}