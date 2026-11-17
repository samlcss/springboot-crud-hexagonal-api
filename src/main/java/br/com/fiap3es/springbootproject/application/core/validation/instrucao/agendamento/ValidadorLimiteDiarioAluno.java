package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.ports.out.InstrucaoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamentoInstrucao {
    private final InstrucaoRepository instrucaoRepository;

    public ValidadorLimiteDiarioAluno(InstrucaoRepository instrucaoRepository) {
        this.instrucaoRepository = instrucaoRepository;
    }

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {
        LocalDateTime inicioExpediente = dados.data().withHour(6);
        LocalDateTime fimExpediente = dados.data().withHour(21 - 1);
        Boolean alunoReincidenciaDiaria = instrucaoRepository.existsByAlunoIdAndDataBetweenAndCanceladaFalse(dados.idAluno(), inicioExpediente, fimExpediente);

        if(alunoReincidenciaDiaria) {
            throw new ValidacaoException("Permitido o agendamento diário de apenas uma instrução por aluno!");
        }
    }
}