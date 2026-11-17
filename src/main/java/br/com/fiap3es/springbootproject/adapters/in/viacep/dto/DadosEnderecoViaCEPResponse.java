package br.com.fiap3es.springbootproject.adapters.in.viacep.dto;

public record DadosEnderecoViaCEPResponse(
        String logradouro,
        String bairro,
        String cidade,
        String uf) {
}