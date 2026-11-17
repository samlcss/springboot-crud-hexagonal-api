package br.com.fiap3es.springbootproject.adapters.in.rabbitmq.mapper;

import br.com.fiap3es.springbootproject.adapters.in.rabbitmq.message.EmailEventMessage;
import br.com.fiap3es.springbootproject.adapters.out.rabbitmq.events.EmailOcorrenciaEvent;
import org.springframework.stereotype.Component;

@Component
public class EmailEventMapper {
    public EmailOcorrenciaEvent toDomain(EmailEventMessage msg) {
        return new EmailOcorrenciaEvent(
                msg.ocorrenciaId(),
                msg.emails(),
                msg.assunto(),
                msg.mensagem()
        );
    }
}