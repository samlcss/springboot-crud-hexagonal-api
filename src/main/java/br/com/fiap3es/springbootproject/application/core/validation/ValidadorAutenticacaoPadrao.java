package br.com.fiap3es.springbootproject.application.core.validation;

import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAutenticacao;
import br.com.fiap3es.springbootproject.exception.types.usuario.AcessoNegadoException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAutenticacaoPadrao implements ValidadorAutenticacao {

    @Override
    public void validar(Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new AcessoNegadoException("Usuário não autenticado!");
        }
    }
}