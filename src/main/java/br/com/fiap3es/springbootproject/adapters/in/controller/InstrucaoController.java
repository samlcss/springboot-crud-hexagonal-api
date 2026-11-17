package br.com.fiap3es.springbootproject.adapters.in.controller;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrucao.DadosCancelamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosDetalhamentoCancelamento;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosDetalhamentoInstrucao;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrucao.DadosListagemInstrucao;
import br.com.fiap3es.springbootproject.application.core.usecases.AgendaDeInstrucoes;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrucoes")
@SecurityRequirement(name = "bearer-key")
public class InstrucaoController {
    private final AgendaDeInstrucoes agenda;

    public InstrucaoController(AgendaDeInstrucoes agenda) {
        this.agenda = agenda;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'ALUNO')")
    public ResponseEntity<DadosDetalhamentoInstrucao> agendarInstrucao(
            @RequestBody @Valid DadosAgendamentoInstrucao dados, Authentication authentication) {
        return ResponseEntity.ok(agenda.agendar(dados, authentication));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<DadosListagemInstrucao>> listarInstrucoes(
            @ParameterObject @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        return ResponseEntity.ok(agenda.listar(paginacao));
    }

    @GetMapping("/aluno/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'ALUNO')")
    public ResponseEntity<Page<DadosListagemInstrucao>> listarInstrucoesPorAluno(
            @PathVariable Long id,
            @ParameterObject @PageableDefault(size = 10) Pageable paginacao,
            Authentication authentication) {
        return ResponseEntity.ok(agenda.listarPorAluno(id, paginacao, authentication));
    }

    @GetMapping("/instrutor/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'ALUNO')")
    public ResponseEntity<Page<DadosListagemInstrucao>> listarInstrucoesPorInstrutor(
            @PathVariable Long id,
            @ParameterObject @PageableDefault(size = 10) Pageable paginacao,
            Authentication authentication) {
        return ResponseEntity.ok(agenda.listarPorInstrutor(id, paginacao, authentication));
    }

    @PostMapping("/cancelamentos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'ALUNO')")
    public ResponseEntity<DadosDetalhamentoCancelamento> cancelarInstrucao(
            @RequestBody @Valid DadosCancelamentoInstrucao dados) {
        return ResponseEntity.ok(agenda.cancelar(dados));
    }
}