package br.com.fiap3es.springbootproject.application.core.domain.model;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Especialidade;
import br.com.fiap3es.springbootproject.application.core.domain.vo.Endereco;

public class Instrutor {
    private Long id;
    private Boolean ativo;
    private String nome;
    private String email;
    private String telefone;
    private String cnh;
    private Especialidade especialidade;
    private Endereco endereco;
    private Usuario usuario;

    public Instrutor() {}

    public Instrutor(
            Long id,
            Boolean ativo,
            String nome,
            String email,
            String telefone,
            String cnh,
            Especialidade especialidade,
            Endereco endereco,
            Usuario usuario) {
        this.id = id;
        this.ativo = ativo;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cnh = cnh;
        this.especialidade = especialidade;
        this.endereco = endereco;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void atribuirUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void atualizarInformacoes(
            String nome,
            String telefone,
            Endereco endereco) {
        if(nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
        if(telefone != null && !telefone.isBlank()) {
            this.telefone = telefone;
        }
        if(endereco != null) {
            this.endereco.atualizarInformacoes(endereco);
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    public boolean isAtivo() {
        return Boolean.TRUE.equals(this.ativo);
    }
}