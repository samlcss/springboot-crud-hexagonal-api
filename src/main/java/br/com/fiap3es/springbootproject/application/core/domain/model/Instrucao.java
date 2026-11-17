package br.com.fiap3es.springbootproject.application.core.domain.model;

import br.com.fiap3es.springbootproject.application.core.domain.enums.MotivoCancelamento;

import java.time.LocalDateTime;

public class Instrucao {
    private Long id;
    private Aluno aluno;
    private Instrutor instrutor;
    private LocalDateTime data;
    private Boolean cancelada = false;
    private MotivoCancelamento motivoCancelamento;
    private LocalDateTime dataCancelamento;

    public Instrucao() {}

    public Instrucao(
            Long id,
            Aluno aluno,
            Instrutor instrutor,
            LocalDateTime data,
            Boolean cancelada,
            MotivoCancelamento motivoCancelamento,
            LocalDateTime dataCancelamento) {
        this.id = id;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.data = data;
        this.cancelada = cancelada;
        this.motivoCancelamento = motivoCancelamento;
        this.dataCancelamento = dataCancelamento;
    }

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public MotivoCancelamento getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void cancelar(MotivoCancelamento motivoCancelamento) {
        this.cancelada = true;
        this.motivoCancelamento = motivoCancelamento;
        this.dataCancelamento = LocalDateTime.now();
    }

    public boolean isCancelada() {
        return cancelada;
    }
}