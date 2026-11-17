package br.com.fiap3es.springbootproject.adapters.in.controller.mapper;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {
    private final EnderecoMapper enderecoMapper;

    public AlunoMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Aluno toEntity(DadosCadastroAluno dados) {
        return new Aluno(
                null,
                true,
                dados.nome(),
                dados.email(),
                dados.telefone(),
                dados.cpf(),
                enderecoMapper.toEntity(dados.endereco()),
                null
        );
    }

    public DadosDetalhamentoAluno toDetalhamentoDTO(Aluno aluno) {
        return new DadosDetalhamentoAluno(
                aluno.getId(),
                aluno.getAtivo(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf(),
                enderecoMapper.toDto(aluno.getEndereco())
        );
    }

    public DadosListagemAluno toListagemDTO(Aluno aluno) {
        return new DadosListagemAluno(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCpf()
        );
    }
}