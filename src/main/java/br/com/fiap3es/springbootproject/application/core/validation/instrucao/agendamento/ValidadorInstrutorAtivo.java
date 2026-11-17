package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.ports.out.InstrutorRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInstrutorAtivo implements ValidadorAgendamentoInstrucao {
    private final InstrutorRepository instrutorRepository;

    public ValidadorInstrutorAtivo(InstrutorRepository instrutorRepository) {
        this.instrutorRepository = instrutorRepository;
    }

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {
        if(dados.idInstrutor() == null) {
            return;
        }

        Boolean instrutorAtivo = instrutorRepository.isAtivo(dados.idInstrutor());

        if(!instrutorAtivo) {
            throw new ValidacaoException("Instrução não pode ser agendada com instrutor inativo!");
        }
    }
}