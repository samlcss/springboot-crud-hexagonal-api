package br.com.fiap3es.springbootproject.application.core.services;

import br.com.fiap3es.springbootproject.adapters.out.rabbitmq.events.EmailOcorrenciaEvent;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrucao;
import br.com.fiap3es.springbootproject.application.ports.out.EmailEventPublisher;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmailNotificacaoService {
    private final EmailEventPublisher emailEventPublisher;

    public EmailNotificacaoService(EmailEventPublisher emailEventPublisher) {
        this.emailEventPublisher = emailEventPublisher;
    }

    public String carregarTemplate(String template) {
        try {
            return new String(
                    Files.readAllBytes(Paths.get(getClass()
                            .getClassLoader()
                            .getResource(template)
                            .toURI()
                    )),
                    StandardCharsets.UTF_8
            );
        } catch(Exception e) {
            throw new RuntimeException("Erro ao carregar template: " + template, e);
        }
    }

    public void enviarNotificacaoInstrucao(Instrucao instrucao, String acao) {
        List<String> emails = List.of(
                "cemoliveir@outlook.com",
                instrucao.getAluno().getEmail(),
                instrucao.getInstrutor().getEmail()
        );
        String assunto = "Nova instrucao " + acao;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = instrucao.getData().format(formatter);

        /*String mensagem = "\nUma nova instrução foi " + acao + "!"
                + "\nID: " + instrucao.getId()
                + "\nAluno: " + instrucao.getAluno().getNome()
                + "\nInstrutor: " + instrucao.getInstrutor().getNome()
                + "\nData: " + dataFormatada;*/

        String template = carregarTemplate("templates/email_instrucao.html");
        String mensagem = template
                .replace("{{acao}}", acao)
                .replace("{{id}}", instrucao.getId().toString())
                .replace("{{aluno}}", instrucao.getAluno().getNome())
                .replace("{{instrutor}}", instrucao.getInstrutor().getNome())
                .replace("{{data}}", dataFormatada);
        EmailOcorrenciaEvent event = new EmailOcorrenciaEvent(
                instrucao.getId(),
                emails,
                assunto,
                mensagem
        );
        emailEventPublisher.publishOcorrenciaEvent(event);
    }
}