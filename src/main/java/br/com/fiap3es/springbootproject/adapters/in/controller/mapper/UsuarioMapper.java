package br.com.fiap3es.springbootproject.adapters.in.controller.mapper;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario.DadosListagemUsuario;
import br.com.fiap3es.springbootproject.application.core.domain.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    private final PasswordEncoder encoder;

    public UsuarioMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public Usuario toEntity(DadosCadastroUsuario dados) {
        return new Usuario(
                null,
                true,
                dados.login(),
                encoder.encode(dados.senha()),
                dados.perfil()
        );
    }

    public DadosDetalhamentoUsuario toDetalhamentoDTO(Usuario usuario) {
        return new DadosDetalhamentoUsuario(
                usuario.getId(),
                usuario.getAtivo(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }

    public DadosListagemUsuario toListagemDTO(Usuario usuario) {
        return new DadosListagemUsuario(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }
}