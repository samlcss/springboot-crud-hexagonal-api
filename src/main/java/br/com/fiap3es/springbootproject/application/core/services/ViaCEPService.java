package br.com.fiap3es.springbootproject.application.core.services;

import br.com.fiap3es.springbootproject.adapters.in.viacep.dto.DadosEnderecoViaCEPResponse;
import br.com.fiap3es.springbootproject.adapters.in.viacep.mapper.ViaCEPMapper;
import br.com.fiap3es.springbootproject.application.ports.out.ViaCEPPort;
import br.com.fiap3es.springbootproject.application.core.domain.vo.Endereco;
import org.springframework.stereotype.Service;

@Service
public class ViaCEPService {
    private final ViaCEPPort viaCEPPort;
    private final ViaCEPMapper viaCEPMapper;

    public ViaCEPService(
            ViaCEPPort viaCEPPort,
            ViaCEPMapper viaCEPMapper) {
        this.viaCEPPort = viaCEPPort;
        this.viaCEPMapper = viaCEPMapper;
    }

    public DadosEnderecoViaCEPResponse buscar(String cep) {
        Endereco endereco = viaCEPPort.buscarEnderecoPorCEP(cep);
        return viaCEPMapper.toDetailsDTO(endereco);
    }
}