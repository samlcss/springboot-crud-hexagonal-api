package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.ports.out.InstrucaoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorConflitoHorarioInstrutor implements ValidadorAgendamentoInstrucao {
    private final InstrucaoRepository instrucaoRepository;

    public ValidadorConflitoHorarioInstrutor(InstrucaoRepository instrucaoRepository) {
        this.instrucaoRepository = instrucaoRepository;
    }

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {
        boolean instrutorOcupado = instrucaoRepository.existsByInstrutorIdAndDataAndCanceladaFalse(dados.idInstrutor(), dados.data());

        if(instrutorOcupado) {
           throw new ValidacaoException("Instrutor ocupado na data e horário informados!");
        }
    }
}