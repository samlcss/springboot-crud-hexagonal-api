package br.com.fiap3es.springbootproject.adapters.in.controller.mapper;

import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosDetalhamentoCancelamento;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosDetalhamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosListagemInstrucao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrucao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InstrucaoMapper {
    public Instrucao toEntity(
            Aluno aluno,
            Instrutor instrutor,
            LocalDateTime data) {
        return new Instrucao(
                null,
                aluno,
                instrutor,
                data,
                false,
                null,
                null
        );
    }

    public DadosDetalhamentoInstrucao toDetalhamentoDTO(Instrucao instrucao) {
        return new DadosDetalhamentoInstrucao(
                instrucao.getId(),
                instrucao.getAluno().getNome(),
                instrucao.getInstrutor().getNome(),
                instrucao.getInstrutor().getEspecialidade(),
                instrucao.getData()
        );
    }

    public DadosListagemInstrucao toListagemDTO(Instrucao instrucao) {
        return new DadosListagemInstrucao(
                instrucao.getId(),
                instrucao.getAluno().getNome(),
                instrucao.getInstrutor().getNome(),
                instrucao.getData()
        );
    }

    public DadosDetalhamentoCancelamento toDetalhamentoCancelamentoDTO(Instrucao instrucao) {
        return new DadosDetalhamentoCancelamento(
                instrucao.getId(),
                instrucao.getDataCancelamento(),
                instrucao.getMotivoCancelamento()
        );
    }
}