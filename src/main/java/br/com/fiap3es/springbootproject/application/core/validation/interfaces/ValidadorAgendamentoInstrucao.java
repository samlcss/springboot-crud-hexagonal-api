package br.com.fiap3es.springbootproject.application.core.validation.interfaces;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;

public interface ValidadorAgendamentoInstrucao {
    void validar(DadosAgendamentoInstrucao dados);
}