package br.com.fiap3es.springbootproject.application.core.validation.interfaces;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import org.springframework.security.core.Authentication;

public interface ValidadorPermissoesAgendamento {
    void validar(DadosAgendamentoInstrucao dados, Authentication authentication);
}