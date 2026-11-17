package br.com.fiap3es.springbootproject.adapters.in.rabbitmq;

import br.com.fiap3es.springbootproject.adapters.in.rabbitmq.mapper.EmailEventMapper;
import br.com.fiap3es.springbootproject.adapters.in.rabbitmq.message.EmailEventMessage;
import br.com.fiap3es.springbootproject.adapters.out.rabbitmq.events.EmailOcorrenciaEvent;
import br.com.fiap3es.springbootproject.application.ports.out.EmailSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailEventConsumer {
    private final EmailEventMapper emailEventMapper;
    private final EmailSender emailSender;

    public EmailEventConsumer(
            EmailEventMapper emailEventMapper,
            EmailSender emailSender) {
        this.emailEventMapper = emailEventMapper;
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "email.ocorrencia")
    public void receberMensagem(EmailEventMessage message) {
        EmailOcorrenciaEvent event = emailEventMapper.toDomain(message);
        //System.out.println("Consumindo event: " + event);
        for(String email : event.emails()) {
            emailSender.enviar(
                    email,
                    event.assunto(),
                    event.mensagem()
            );
        }
    }
}