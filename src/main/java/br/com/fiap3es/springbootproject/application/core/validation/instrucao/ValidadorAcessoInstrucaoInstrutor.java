package br.com.fiap3es.springbootproject.application.core.validation.instrucao;

import br.com.fiap3es.springbootproject.application.core.validation.interfaces.ValidadorAcessoInstrutorInstrucao;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrutor;
import br.com.fiap3es.springbootproject.application.ports.out.InstrutorRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidadorAcessoInstrucaoInstrutor implements ValidadorAcessoInstrutorInstrucao {
    private final InstrutorRepository instrutorRepository;

    public ValidadorAcessoInstrucaoInstrutor(InstrutorRepository instrutorRepository) {
        this.instrutorRepository = instrutorRepository;
    }

    @Override
    public void validar(Long idInstrutorRequisitado, String loginUsuarioAutenticado) {
        Optional<Instrutor> instrutorOpt = instrutorRepository.findByUsuarioLogin(loginUsuarioAutenticado);

        if(instrutorOpt.isPresent()) {
            Instrutor instrutor = instrutorOpt.get();
            if(!instrutor.getId().equals(idInstrutorRequisitado)) {
                throw new ValidacaoException("Acesso negado: você pode visualizar apenas seus próprios agendamentos!");
            }
        }
    }
}