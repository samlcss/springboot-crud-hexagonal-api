package br.com.fiap3es.springbootproject.config.rabbitmq;

import br.com.fiap3es.springbootproject.application.core.services.EmailNotificacaoService;
import br.com.fiap3es.springbootproject.application.ports.out.EmailEventPublisher;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurations {

    @Bean
    public Queue emailOcorrenciaQueue() {
        return new Queue("email.ocorrencia", true);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public EmailNotificacaoService emailNotificacaoService(EmailEventPublisher emailEventPublisher) {
        return new EmailNotificacaoService(emailEventPublisher);
    }
}