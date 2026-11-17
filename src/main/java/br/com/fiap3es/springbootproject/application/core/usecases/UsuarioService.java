package br.com.fiap3es.springbootproject.application.core.usecases;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAlteracaoSenha;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAtualizacaoPerfil;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAtualizacaoUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario.DadosListagemUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.mapper.UsuarioMapper;
import br.com.fiap3es.springbootproject.application.core.domain.model.Usuario;
import br.com.fiap3es.springbootproject.application.ports.out.UsuarioRepository;
import br.com.fiap3es.springbootproject.exception.types.usuario.SenhaIncorretaException;
import br.com.fiap3es.springbootproject.exception.types.usuario.UsuarioInativoException;
import br.com.fiap3es.springbootproject.exception.types.usuario.UsuarioNaoEncontradoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder encoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.encoder = encoder;
    }

    @Transactional
    public DadosDetalhamentoUsuario cadastrar(DadosCadastroUsuario dados) {
        usuarioRepository.findByLogin(dados.login())
                .ifPresent(usuario -> {
                    throw new RuntimeException("Usuário já cadastrado!");
                });
        Usuario usuario = usuarioMapper.toEntity(dados);
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDetalhamentoDTO(saved);
    }

    public Page<DadosListagemUsuario> listar(Pageable paginacao) {
        return usuarioRepository.findAllAtivos(paginacao)
                .map(usuarioMapper::toListagemDTO);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizar(@Valid DadosAtualizacaoUsuario dados) {
        Usuario usuario = usuarioRepository.findById(dados.id())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dados.login()));
        usuario.atualizarInformacoes(
                dados.ativo(),
                dados.login(),
                dados.perfil()
        );
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDetalhamentoDTO(saved);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarPerfil(DadosAtualizacaoPerfil dados) {
        Usuario usuario = usuarioRepository.findById(dados.id())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("ID do usuário informado não existe!"));
        usuario.atualizarPerfil(dados.perfil());
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDetalhamentoDTO(saved);
    }

    @Transactional
    public void alterarSenha(DadosAlteracaoSenha dados) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!encoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new SenhaIncorretaException("Senha atual incorreta!");
        }
        usuario.alterarSenha(encoder.encode(dados.novaSenha()));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public DadosDetalhamentoUsuario excluir(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("ID do usuário informado não existe!"));
        if(!usuario.isAtivo()) {
            throw new UsuarioInativoException("Usuário já está inativo!");
        }
        usuario.excluir();
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDetalhamentoDTO(saved);
    }

    public DadosDetalhamentoUsuario detalhar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("ID do usuário informado não existe!"));
        return usuarioMapper.toDetalhamentoDTO(usuario);
    }
}