package br.com.fiap3es.springbootproject.application.core.usecases;

import br.com.fiap3es.springbootproject.adapters.in.controller.mapper.EnderecoMapper;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.aluno.DadosAtualizacaoAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.mapper.AlunoMapper;
import br.com.fiap3es.springbootproject.application.core.validation.aluno.ValidadorAcessoAluno;
import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import br.com.fiap3es.springbootproject.application.ports.out.AlunoRepository;
import br.com.fiap3es.springbootproject.application.core.domain.enums.Role;
import br.com.fiap3es.springbootproject.application.core.domain.model.Usuario;
import br.com.fiap3es.springbootproject.application.ports.out.UsuarioRepository;
import br.com.fiap3es.springbootproject.exception.types.aluno.AlunoInativoException;
import br.com.fiap3es.springbootproject.exception.types.aluno.AlunoNaoEncontradoException;
import br.com.fiap3es.springbootproject.exception.types.usuario.UsuarioAlunoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;
    private final PasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository;
    private final ValidadorAcessoAluno validador;
    private final EnderecoMapper enderecoMapper;

    public AlunoService(
            AlunoRepository alunoRepository,
            AlunoMapper alunoMapper,
            PasswordEncoder encoder,
            UsuarioRepository usuarioRepository,
            ValidadorAcessoAluno validador, EnderecoMapper enderecoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.encoder = encoder;
        this.usuarioRepository = usuarioRepository;
        this.validador = validador;
        this.enderecoMapper = enderecoMapper;
    }

    @Transactional
    public DadosDetalhamentoAluno cadastrar(DadosCadastroAluno dados) {
        Usuario usuario = new Usuario(
                null,
                true,
                dados.email(),
                encoder.encode(dados.cpf()),
                Role.ALUNO);
        Aluno aluno = alunoMapper.toEntity(dados);
        aluno.atribuirUsuario(usuario);
        Aluno saved = alunoRepository.save(aluno);
        return alunoMapper.toDetalhamentoDTO(saved);
    }

    public Page<DadosListagemAluno> listar(Pageable paginacao) {
        return alunoRepository.findAllAtivos(paginacao)
                .map(alunoMapper::toListagemDTO);
    }

    @Transactional
    public DadosDetalhamentoAluno atualizar(DadosAtualizacaoAluno dados) {
        Aluno aluno = alunoRepository.findById(dados.id())
                .orElseThrow(() -> new AlunoNaoEncontradoException("ID do aluno informado não existe!"));
        validador.validarAtualizacao(aluno);
        aluno.atualizarInformacoes(
                dados.nome(),
                dados.telefone(),
                enderecoMapper.toEntity(dados.endereco())
        );
        Aluno saved = alunoRepository.save(aluno);
        return alunoMapper.toDetalhamentoDTO(saved);
    }

    @Transactional
    public DadosDetalhamentoAluno excluir(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("ID do aluno informado não existe!"));
        validador.validarExclusao(aluno);
        if(!aluno.isAtivo()) {
            throw new AlunoInativoException("Aluno já está inativo!");
        }
        aluno.excluir();
        Aluno saved = alunoRepository.save(aluno);
        return alunoMapper.toDetalhamentoDTO(saved);
    }

    public DadosDetalhamentoAluno detalhar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("ID do aluno informado não existe!"));
        validador.validarProprioAcesso(aluno);
        return alunoMapper.toDetalhamentoDTO(aluno);
    }

    public DadosDetalhamentoAluno detalharPorAluno(Authentication authentication) {
        String login = authentication.getName();
        Aluno aluno = alunoRepository.findByUsuarioLogin(login)
                .orElseThrow(() -> new UsuarioAlunoNaoEncontradoException("Aluno não encontrado para usuário autenticado!"));
        return alunoMapper.toDetalhamentoDTO(aluno);
    }
}