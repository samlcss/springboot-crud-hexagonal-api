package br.com.fiap3es.springbootproject.application.core.usecases;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosCancelamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosDetalhamentoCancelamento;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosDetalhamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosListagemInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.mapper.InstrucaoMapper;
import br.com.fiap3es.springbootproject.application.core.services.EmailNotificacaoService;
import br.com.fiap3es.springbootproject.application.core.validation.interfaces.*;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrucao;
import br.com.fiap3es.springbootproject.application.ports.out.InstrucaoRepository;
import br.com.fiap3es.springbootproject.exception.types.instrucao.InstrucaoNaoEncontradaException;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import br.com.fiap3es.springbootproject.application.core.domain.model.Aluno;
import br.com.fiap3es.springbootproject.application.ports.out.AlunoRepository;
import br.com.fiap3es.springbootproject.application.core.domain.model.Instrutor;
import br.com.fiap3es.springbootproject.application.ports.out.InstrutorRepository;
import br.com.fiap3es.springbootproject.exception.types.aluno.AlunoNaoEncontradoException;
import br.com.fiap3es.springbootproject.exception.types.instrutor.InstrutorNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeInstrucoes {
    private final InstrucaoRepository instrucaoRepository;
    private final InstrucaoMapper instrucaoMapper;
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;
    private final ValidadorAutenticacao validadorAutenticacao;
    private final List<ValidadorPermissoesAgendamento> validadoresPermissoesAgendamento;
    private final List<ValidadorAgendamentoInstrucao> validadoresAgendamento;
    private final List<ValidadorCancelamentoInstrucao> validadoresCancelamento;
    private final List<ValidadorAcessoAlunoInstrucao> validadoresAcessoAlunoInstrucao;
    private final List<ValidadorAcessoInstrutorInstrucao> validadoresAcessoInstrutorInstrucao;
    private final EmailNotificacaoService emailNotificacaoService;

    public AgendaDeInstrucoes(
            InstrucaoRepository instrucaoRepository,
            InstrucaoMapper instrucaoMapper,
            InstrutorRepository instrutorRepository,
            AlunoRepository alunoRepository,
            ValidadorAutenticacao validadorAutenticacao,
            List<ValidadorPermissoesAgendamento> validadoresPermissoesAgendamento,
            List<ValidadorAgendamentoInstrucao> validadoresAgendamento,
            List<ValidadorCancelamentoInstrucao> validadoresCancelamento,
            List<ValidadorAcessoAlunoInstrucao> validadoresAcessoAlunoInstrucao,
            List<ValidadorAcessoInstrutorInstrucao> validadoresAcessoInstrutorInstrucao,
            EmailNotificacaoService emailNotificacaoService) {
        this.instrucaoRepository = instrucaoRepository;
        this.instrucaoMapper = instrucaoMapper;
        this.instrutorRepository = instrutorRepository;
        this.alunoRepository = alunoRepository;
        this.validadorAutenticacao = validadorAutenticacao;
        this.validadoresPermissoesAgendamento = validadoresPermissoesAgendamento;
        this.validadoresAgendamento = validadoresAgendamento;
        this.validadoresCancelamento = validadoresCancelamento;
        this.validadoresAcessoAlunoInstrucao = validadoresAcessoAlunoInstrucao;
        this.validadoresAcessoInstrutorInstrucao = validadoresAcessoInstrutorInstrucao;
        this.emailNotificacaoService = emailNotificacaoService;
    }

    @Transactional
    public DadosDetalhamentoInstrucao agendar(
            DadosAgendamentoInstrucao dados,
            Authentication authentication) {
        validadorAutenticacao.validar(authentication);
        validadoresPermissoesAgendamento
                .forEach(v -> v.validar(dados, authentication));
        Aluno aluno = alunoRepository.findById(dados.idAluno())
                    .orElseThrow(() -> new AlunoNaoEncontradoException("ID do aluno informado não existe!"));
        Instrutor instrutor = escolherInstrutor(dados);
        if(instrutor == null) {
            throw new ValidacaoException("Não existe instrutor disponível na data / hora!");
        }
        validadoresAgendamento
                .forEach(v -> v.validar(dados));
        Instrucao instrucao = instrucaoMapper.toEntity(
                aluno,
                instrutor,
                dados.data()
        );
        Instrucao saved = instrucaoRepository.save(instrucao);
        emailNotificacaoService.enviarNotificacaoInstrucao(saved, "registrada");
        return instrucaoMapper.toDetalhamentoDTO(saved);
    }

    public Page<DadosListagemInstrucao> listar(Pageable paginacao) {
        return instrucaoRepository.findAllByCanceladaFalse(paginacao)
                .map(instrucaoMapper::toListagemDTO);
    }

    public Page<DadosListagemInstrucao> listarPorAluno(
            Long id,
            Pageable paginacao,
            Authentication authentication) {
        String usuarioLogado = authentication.getName();
        validadoresAcessoAlunoInstrucao
                .forEach(v -> v.validar(id, usuarioLogado));
        return instrucaoRepository.findAllByAlunoIdAndCanceladaFalse(id, paginacao)
                .map(instrucaoMapper::toListagemDTO);
    }

    public Page<DadosListagemInstrucao> listarPorInstrutor(
            Long id,
            Pageable paginacao,
            Authentication authentication) {
        String usuarioLogado = authentication.getName();
        validadoresAcessoInstrutorInstrucao
                .forEach(v -> v.validar(id, usuarioLogado));
        return instrucaoRepository.findAllByInstrutorIdAndCanceladaFalse(id, paginacao)
                .map(instrucaoMapper::toListagemDTO);
    }

    @Transactional
    public DadosDetalhamentoCancelamento cancelar(DadosCancelamentoInstrucao dados) {
        Instrucao instrucao = instrucaoRepository.findById(dados.idInstrucao())
                .orElseThrow(() -> new InstrucaoNaoEncontradaException("ID da instrução informado não existe!"));
        validadoresCancelamento.forEach(v -> v.validar(dados));
        instrucao.cancelar(dados.motivoCancelamento());
        Instrucao saved = instrucaoRepository.save(instrucao);
        emailNotificacaoService.enviarNotificacaoInstrucao(saved, "cancelada");
        return instrucaoMapper.toDetalhamentoCancelamentoDTO(saved);
    }

    private Instrutor escolherInstrutor(DadosAgendamentoInstrucao dados) {
        if(dados.idInstrutor() != null) {
            return instrutorRepository.findById(dados.idInstrutor())
                    .orElseThrow(() -> new InstrutorNaoEncontradoException("ID do instrutor informado não existe!"));
        } else {
            if(dados.especialidade() == null) {
                throw new ValidacaoException("Especialidade é obrigatória, quando instrutor não for escolhido!");
            }
            return instrutorRepository.escolherInstrutorAleatorioLivreNaData(
                    dados.especialidade(),
                    dados.data()
            );
        }
    }
}