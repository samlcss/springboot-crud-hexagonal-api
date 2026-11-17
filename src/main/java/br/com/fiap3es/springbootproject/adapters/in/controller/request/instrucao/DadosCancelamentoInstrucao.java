package br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao;

import br.com.fiap3es.springbootproject.application.core.domain.enums.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoInstrucao(

        @NotNull
        Long idInstrucao,

        @NotNull
        MotivoCancelamento motivoCancelamento) {
}