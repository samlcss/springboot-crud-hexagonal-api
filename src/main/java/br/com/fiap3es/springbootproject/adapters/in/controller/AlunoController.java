package br.com.fiap3es.springbootproject.adapters.in.controller;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.aluno.DadosAtualizacaoAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3es.springbootproject.application.core.usecases.AlunoService;
import br.com.fiap3es.springbootproject.application.ports.in.ModelDomainController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
@SecurityRequirement(name = "bearer-key")
public class AlunoController implements ModelDomainController<
        DadosCadastroAluno,
        DadosListagemAluno,
        DadosAtualizacaoAluno,
        Void,
        DadosDetalhamentoAluno,
        Long> {
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoAluno> cadastrar(
            @RequestBody @Valid DadosCadastroAluno dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoAluno dto = alunoService.cadastrar(dados);
        URI uri = uriBuilder.path("/alunos/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<DadosListagemAluno>> listar(
            @ParameterObject @PageableDefault(size = 10 ,sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(alunoService.listar(paginacao));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoAluno> atualizar(
            @RequestBody @Valid DadosAtualizacaoAluno dados) {
        return ResponseEntity.ok(alunoService.atualizar(dados));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoAluno> excluir(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(alunoService.excluir(id));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoAluno> detalhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(alunoService.detalhar(id));
    }

    @GetMapping("/self")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<DadosDetalhamentoAluno> alunoAutoDetalhar(
            Authentication authentication) {
        return ResponseEntity.ok(alunoService.detalharPorAluno(authentication));
    }
}