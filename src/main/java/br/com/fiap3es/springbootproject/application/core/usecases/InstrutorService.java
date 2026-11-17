package br.com.fiap3es.springbootproject.application.core.usecases;

import br.com.fiap3es.springbootproject.adapters.in.controller.mapper.EnderecoMapper;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrutor.DadosAtualizacaoInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrutor.DadosCadastroInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor.DadosListagemInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.mapper.InstrutorMapper;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrutor;
import br.com.fiap3es.springbootproject.application.ports.out.InstrutorRepository;
import br.com.fiap3es.springbootproject.application.core.domain.enums.Role;
import br.com.fiap3es.springbootproject.application.core.domain.model.Usuario;
import br.com.fiap3es.springbootproject.application.ports.out.UsuarioRepository;
import br.com.fiap3es.springbootproject.exception.types.instrutor.InstrutorInativoException;
import br.com.fiap3es.springbootproject.exception.types.instrutor.InstrutorNaoEncontradoException;
import br.com.fiap3es.springbootproject.exception.types.usuario.UsuarioInstrutorNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstrutorService {
    private final InstrutorRepository instrutorRepository;
    private final InstrutorMapper instrutorMapper;
    private final PasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoMapper enderecoMapper;

    public InstrutorService(
            InstrutorRepository instrutorRepository,
            InstrutorMapper instrutorMapper,
            PasswordEncoder encoder,
            UsuarioRepository usuarioRepository,
            EnderecoMapper enderecoMapper) {
        this.instrutorRepository = instrutorRepository;
        this.instrutorMapper = instrutorMapper;
        this.encoder = encoder;
        this.usuarioRepository = usuarioRepository;
        this.enderecoMapper = enderecoMapper;
    }

    @Transactional
    public DadosDetalhamentoInstrutor cadastrar(DadosCadastroInstrutor dados) {
        Usuario usuario = new Usuario(
                null,
                true,
                dados.email(),
                encoder.encode(dados.cnh()),
                Role.INSTRUTOR);
        Instrutor instrutor = instrutorMapper.toEntity(dados);
        instrutor.atribuirUsuario(usuario);
        Instrutor saved = instrutorRepository.save(instrutor);
        return instrutorMapper.toDetalhamentoDTO(saved);
    }

    public Page<DadosListagemInstrutor> listar(Pageable paginacao) {
        return instrutorRepository.findAllAtivos(paginacao).map(instrutorMapper::toListagemDTO);
    }

    @Transactional
    public DadosDetalhamentoInstrutor atualizar(DadosAtualizacaoInstrutor dados) {
        Instrutor instrutor = instrutorRepository.findById(dados.id())
                .orElseThrow(() -> new InstrutorNaoEncontradoException("ID do instrutor informado não existe!"));
        instrutor.atualizarInformacoes(
                dados.nome(),
                dados.telefone(),
                enderecoMapper.toEntity(dados.endereco())
        );
        Instrutor saved = instrutorRepository.save(instrutor);
        return instrutorMapper.toDetalhamentoDTO(saved);
    }

    @Transactional
    public DadosDetalhamentoInstrutor excluir(Long id) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new InstrutorNaoEncontradoException("ID do instrutor informado não existe!"));
        if(!instrutor.isAtivo()) {
            throw new InstrutorInativoException("Instrutor já está inativo!");
        }
        instrutor.excluir();
        Instrutor saved = instrutorRepository.save(instrutor);
        return instrutorMapper.toDetalhamentoDTO(saved);
    }

    public DadosDetalhamentoInstrutor detalhar(Long id) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new InstrutorNaoEncontradoException("ID do instrutor informado não existe!"));
        return instrutorMapper.toDetalhamentoDTO(instrutor);
    }

    public DadosDetalhamentoInstrutor detalharPorInstrutor(Authentication authentication) {
        String login = authentication.getName();
        Instrutor instrutor = instrutorRepository.findByUsuarioLogin(login)
                .orElseThrow(() -> new UsuarioInstrutorNaoEncontradoException("Instrutor não encontrado para usuário autenticado!"));
        return instrutorMapper.toDetalhamentoDTO(instrutor);
    }
}