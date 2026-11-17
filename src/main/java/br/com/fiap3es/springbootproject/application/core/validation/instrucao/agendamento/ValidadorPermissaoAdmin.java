package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorPermissoesAgendamento;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPermissaoAdmin implements ValidadorPermissoesAgendamento {

    @Override
    public void validar(DadosAgendamentoInstrucao dados, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(!isAdmin) {
            return;
        }
        if(dados.idAluno() == null) {
            throw new ValidacaoException("É obrigatório informar o ID do Aluno");
        }
    }
}