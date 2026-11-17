package br.com.fiap3es.springbootproject.application.core.validation.instrucao;

import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAcessoAlunoInstrucao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import br.com.fiap3es.springbootproject.application.ports.out.AlunoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidadorAcessoInstrucaoAluno implements ValidadorAcessoAlunoInstrucao {
    private final AlunoRepository alunoRepository;

    public ValidadorAcessoInstrucaoAluno(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void validar(Long idAlunoRequisitado, String loginUsuarioAutenticado) {
        Optional<Aluno> alunoOpt = alunoRepository.findByUsuarioLogin(loginUsuarioAutenticado);

        if(alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            if(!aluno.getId().equals(idAlunoRequisitado)) {
                throw new ValidacaoException("Acesso negado: você pode visualizar apenas seus próprios agendamentos!");
            }
        }
    }
}