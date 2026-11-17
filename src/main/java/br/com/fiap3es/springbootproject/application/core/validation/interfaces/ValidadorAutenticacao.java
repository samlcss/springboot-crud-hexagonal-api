package br.com.fiap3es.springbootproject.application.core.validation.interfaces;

import org.springframework.security.core.Authentication;

public interface ValidadorAutenticacao {
    void validar(Authentication authentication);
}