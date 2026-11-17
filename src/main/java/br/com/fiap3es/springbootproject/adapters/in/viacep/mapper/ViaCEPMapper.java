package br.com.fiap3es.springbootproject.adapters.in.viacep.mapper;

import br.com.fiap3es.springbootproject.adapters.in.viacep.dto.DadosEnderecoViaCEPResponse;
import br.com.fiap3es.springbootproject.adapters.out.viacep.dto.DadosViaCEPResponse;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.endereco.DadosEndereco;
import br.com.fiap3es.springbootproject.application.core.domain.vo.Endereco;
import org.springframework.stereotype.Component;

@Component
public class ViaCEPMapper {
    public DadosEnderecoViaCEPResponse toDetailsDTO(Endereco endereco) {
        return new DadosEnderecoViaCEPResponse(
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf()
        );
    }

    public DadosEndereco toDadosEndereco(DadosViaCEPResponse dados) {
        return new DadosEndereco(
                dados.logradouro(),
                null,
                null,
                dados.bairro(),
                dados.localidade(),
                dados.uf(),
                dados.cep()
        );
    }

}