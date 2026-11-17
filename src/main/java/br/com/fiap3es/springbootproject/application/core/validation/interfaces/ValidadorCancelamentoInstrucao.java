package br.com.fiap3es.springbootproject.application.core.validation.interfaces;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosCancelamentoInstrucao;

public interface ValidadorCancelamentoInstrucao {
    void validar(DadosCancelamentoInstrucao dados);
}