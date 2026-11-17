package br.com.fiap3es.springbootproject.adapters.in.controller.mapper;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrutor.DadosCadastroInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor.DadosListagemInstrutor;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrutor;
import org.springframework.stereotype.Component;

@Component
public class InstrutorMapper {
    private final EnderecoMapper enderecoMapper;

    public InstrutorMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Instrutor toEntity(DadosCadastroInstrutor dados) {
        return new Instrutor(
                null,
                true,
                dados.nome(),
                dados.email(),
                dados.telefone(),
                dados.cnh(),
                dados.especialidade(),
                enderecoMapper.toEntity(dados.endereco()),
                null
        );
    }

    public DadosDetalhamentoInstrutor toDetalhamentoDTO(Instrutor instrutor) {
        return new DadosDetalhamentoInstrutor(
                instrutor.getId(),
                instrutor.getAtivo(),
                instrutor.getNome(),
                instrutor.getEmail(),
                instrutor.getTelefone(),
                instrutor.getCnh(),
                instrutor.getEspecialidade(),
                enderecoMapper.toDto(instrutor.getEndereco())
        );
    }

    public DadosListagemInstrutor toListagemDTO(Instrutor instrutor) {
        return new DadosListagemInstrutor(
                instrutor.getId(),
                instrutor.getNome(),
                instrutor.getEmail(),
                instrutor.getCnh(),
                instrutor.getEspecialidade()
        );
    }
}