package br.com.fiap3es.springbootproject.application.core.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public Endereco() {}

    public Endereco(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf,
            String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }

    public void atualizarInformacoes(Endereco endereco) {
        if(endereco.logradouro != null && !endereco.logradouro.isBlank()) {
            this.logradouro = endereco.logradouro;
        }
        if(endereco.numero != null && !endereco.numero.isBlank()) {
            this.numero = endereco.numero;
        }
        if(endereco.complemento != null && !endereco.complemento.isBlank()) {
            this.complemento = endereco.complemento;
        }
        if(endereco.bairro != null && !endereco.bairro.isBlank()) {
            this.bairro = endereco.bairro;
        }
        if(endereco.cidade != null && !endereco.cidade.isBlank()) {
            this.cidade = endereco.cidade;
        }
        if(endereco.uf != null && !endereco.uf.isBlank()) {
            this.uf = endereco.uf;
        }
        if(endereco.cep != null && !endereco.cep.isBlank()) {
            this.cep = endereco.cep;
        }
    }
}