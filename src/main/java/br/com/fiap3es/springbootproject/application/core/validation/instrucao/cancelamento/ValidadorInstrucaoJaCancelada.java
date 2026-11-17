package br.com.fiap3es.springbootproject.application.core.validation.instrucao.cancelamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosCancelamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorCancelamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrucao;
import br.com.fiap3es.springbootproject.application.ports.out.InstrucaoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInstrucaoJaCancelada implements ValidadorCancelamentoInstrucao {
    private final InstrucaoRepository instrucaoRepository;

    public ValidadorInstrucaoJaCancelada(InstrucaoRepository instrucaoRepository) {
        this.instrucaoRepository = instrucaoRepository;
    }

    @Override
    public void validar(DadosCancelamentoInstrucao dados) {
        Instrucao instrucao = instrucaoRepository.findById(dados.idInstrucao())
                .orElseThrow(() -> new EntityNotFoundException("Instrução não encontrada!"));
        if(instrucao.isCancelada()) {
            throw new ValidacaoException("Instrução já cancelada anteriormente!");
        }
    }
}