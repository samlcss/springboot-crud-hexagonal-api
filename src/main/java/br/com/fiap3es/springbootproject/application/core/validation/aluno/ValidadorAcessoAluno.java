package br.com.fiap3es.springbootproject.application.core.validation.aluno;

import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAcessoAluno {
    public String getUsernameAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new ValidacaoException("Usuário não autenticado!");
        }
        return authentication.getName();
    }

    public void validarProprioAcesso(Aluno aluno) {
        String usernameAutenticado = getUsernameAutenticado();
        String usernameAluno = aluno.getUsuario().getUsername();
        if(!usernameAutenticado.equals(usernameAluno)) {
            throw new ValidacaoException("Acesso negado: o aluno pode acessar apenas os próprios dados!");
        }
    }

    public void validarAtualizacao(Aluno aluno) {
        String usernameAutenticado = getUsernameAutenticado();
        String usernameAluno = aluno.getUsuario().getUsername();
        if(!usernameAutenticado.equals(usernameAluno)) {
            throw new ValidacaoException("Acesso negado: o aluno pode atualizar apenas os próprios dados!");
        }
    }

    public void validarExclusao(Aluno aluno) {
        String usernameAutenticado = getUsernameAutenticado();
        String usernameAluno = aluno.getUsuario().getUsername();
        if(!usernameAutenticado.equals(usernameAluno)) {
            throw new ValidacaoException("Acesso negado: o aluno pode excluir apenas os próprios dados!");
        }
    }
}