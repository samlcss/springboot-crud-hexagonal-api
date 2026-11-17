package br.com.fiap3es.springbootproject.adapters.in.rabbitmq.message;

import java.util.List;

public record EmailEventMessage(
        Long ocorrenciaId,
        List<String> emails,
        String assunto,
        String mensagem) {
}