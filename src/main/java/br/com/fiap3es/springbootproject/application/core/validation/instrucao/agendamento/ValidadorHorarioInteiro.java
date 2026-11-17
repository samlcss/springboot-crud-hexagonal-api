package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorHorarioInteiro implements ValidadorAgendamentoInstrucao {

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {
        LocalDateTime data = dados.data();

        if(data.getMinute() != 0 || data.getSecond() != 0) {
            throw new ValidacaoException("O horário deve ser preenchido em horas inteiras (ex: 08:00, 09:00, ...)!");
        }
    }
}