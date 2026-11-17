package br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosListagemInstrucao(
        Long id,
        String nomeAluno,
        String nomeInstrutor,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data) {
}