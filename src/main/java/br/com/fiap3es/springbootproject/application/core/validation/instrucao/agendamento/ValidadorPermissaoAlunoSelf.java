package br.com.fiap3es.springbootproject.application.core.validation.instrucao.agendamento;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorPermissoesAgendamento;
import br.com.fiap3es.springbootproject.application.ports.out.AlunoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import br.com.fiap3es.springbootproject.exception.types.usuario.UsuarioAlunoNaoEncontradoException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPermissaoAlunoSelf implements ValidadorPermissoesAgendamento {
    private final AlunoRepository alunoRepository;

    public ValidadorPermissaoAlunoSelf(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void validar(DadosAgendamentoInstrucao dados, Authentication authentication) {
        Boolean isAluno = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ALUNO"));
        if(!isAluno) {
            return;
        }

        Aluno alunoLogado = alunoRepository.findByUsuarioLogin(authentication.getName())
                .orElseThrow(() -> new UsuarioAlunoNaoEncontradoException("Aluno não encontrado para o usuário autenticado!"));
        if(!dados.idAluno().equals(alunoLogado.getId())) {
            throw new ValidacaoException("Você só pode agendar instruções para si mesmo!");
        }
    }
}