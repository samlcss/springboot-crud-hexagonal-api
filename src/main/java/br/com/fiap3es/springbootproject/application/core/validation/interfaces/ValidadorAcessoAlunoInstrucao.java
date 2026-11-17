package br.com.fiap3es.springbootproject.application.core.validation.interfaces;

public interface ValidadorAcessoAlunoInstrucao {
    void validar(Long idAlunoRequisitado, String loginUsuarioAutenticado);
}