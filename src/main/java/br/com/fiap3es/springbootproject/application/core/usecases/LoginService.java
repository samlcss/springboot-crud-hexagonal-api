package br.com.fiap3es.springbootproject.application.core.usecases;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAutenticacao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Usuario;
import br.com.fiap3es.springbootproject.config.security.dto.DadosTokenJWT;
import br.com.fiap3es.springbootproject.config.security.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class LoginService {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public LoginService(
            AuthenticationManager manager,
            TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    public DadosTokenJWT logar(DadosAutenticacao dados) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = manager.authenticate(token);
        String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return new DadosTokenJWT(tokenJWT);
    }
}