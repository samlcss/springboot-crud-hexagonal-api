package br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao;

import br.com.fiap3es.springbootproject.application.core.domain.enums.MotivoCancelamento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhamentoCancelamento(
        Long id,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCancelamento,
        MotivoCancelamento motivo) {
}