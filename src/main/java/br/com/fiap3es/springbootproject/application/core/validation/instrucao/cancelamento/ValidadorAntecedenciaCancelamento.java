package br.com.fiap3es.springbootproject.application.core.validation.instrucao.cancelamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosCancelamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorCancelamentoInstrucao;
import br.com.fiap3es.springbootproject.application.ports.out.InstrucaoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaCancelamento implements ValidadorCancelamentoInstrucao {
    private final InstrucaoRepository instrucaoRepository;

    public ValidadorAntecedenciaCancelamento(InstrucaoRepository instrucaoRepository) {
        this.instrucaoRepository = instrucaoRepository;
    }

    @Override
    public void validar(DadosCancelamentoInstrucao dados) {
        LocalDateTime dataInstrucao = instrucaoRepository.findById(dados.idInstrucao())
                .orElseThrow(() -> new EntityNotFoundException("Instrução não encontrada!")).getData();
        LocalDateTime agora = LocalDateTime.now();

        Long antecedencia = Duration.between(agora, dataInstrucao).toHours();

        if(antecedencia < 24) {
            throw new ValidacaoException("Instrução deve ser cancelada com antecedência mínima de 24 horas!");
        }
    }
}