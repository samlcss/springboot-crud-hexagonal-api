package br.com.fiap3es.springbootproject.application.core.validation.interfaces;

public interface ValidadorAcessoInstrutorInstrucao {
    void validar(Long idInstrutorRequisitado, String loginUsuarioAutenticado);
}