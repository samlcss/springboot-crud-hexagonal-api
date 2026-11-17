package br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao;

import br.com.fiap3es.springbootproject.application.core.domain.enums.Especialidade;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhamentoInstrucao(
        Long id,
        String nomeAluno,
        String nomeInstrutor,
        Especialidade especialidade,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data) {
}