package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.ports.out.AlunoRepository;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAlunoAtivo implements ValidadorAgendamentoInstrucao {
    private final AlunoRepository alunoRepository;

    public ValidadorAlunoAtivo(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {
        Boolean alunoAtivo = alunoRepository.isAtivo(dados.idAluno());

        if(!alunoAtivo) {
            throw new ValidacaoException("Instrução não pode ser agendada para aluno inativo!");
        }
    }
}