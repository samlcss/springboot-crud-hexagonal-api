package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoInstrucao {

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {
        LocalDateTime dataInstrucao = dados.data();

        Boolean domingo = dataInstrucao.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        Boolean preAbertura = dataInstrucao.getHour() < 6;
        Boolean posFechamento = dataInstrucao.getHour() > (21 - 1);

        if(domingo || preAbertura || posFechamento) {
            throw new ValidacaoException("Tentativa de agendamento fora do horário de funcionamento da Auto-Escola!");
        }
    }
}