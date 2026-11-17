package br.com.fiap3es.springbootproject.application.core.domain.model;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Usuario implements UserDetails {
    private Long id;
    private Boolean ativo;
    private String login;
    private String senha;
    private Role perfil;

    public Usuario() {}

    public Usuario(
            Long id,
            Boolean ativo,
            String login,
            String senha,
            Role perfil) {
        this.id = id;
        this.ativo = ativo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Role getPerfil() {
        return perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo != null && ativo;
    }

    public void atualizarInformacoes(
            Boolean ativo,
            String login,
            Role perfil) {
        if(ativo != null) {
            this.ativo = ativo;
        }
        if(login != null && !login.isBlank()) {
            this.ativo = ativo;
        }
        if(perfil != null) {
            this.ativo = ativo;
        }
    }

    public void atualizarPerfil(Role perfil) {
        this.perfil = perfil;
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public void excluir() {
        this.ativo = false;
    }

    public boolean isAtivo() {
        return Boolean.TRUE.equals(this.ativo);
    }
}