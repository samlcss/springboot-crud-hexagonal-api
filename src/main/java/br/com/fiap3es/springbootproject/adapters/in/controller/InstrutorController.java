package br.com.fiap3es.springbootproject.adapters.in.controller;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrutor.DadosAtualizacaoInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.instrutor.DadosCadastroInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.instrutor.DadosListagemInstrutor;
import br.com.fiap3es.springbootproject.application.core.usecases.InstrutorService;
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
@RequestMapping("/instrutores")
@SecurityRequirement(name = "bearer-key")
public class InstrutorController implements ModelDomainController<
        DadosCadastroInstrutor,
        DadosListagemInstrutor,
        DadosAtualizacaoInstrutor,
        Void,
        DadosDetalhamentoInstrutor,
        Long> {
    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoInstrutor> cadastrar(
            @ParameterObject @RequestBody @Valid DadosCadastroInstrutor dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoInstrutor dto = instrutorService.cadastrar(dados);
        URI uri = uriBuilder.path("/instrutores/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'ALUNO')")
    public ResponseEntity<Page<DadosListagemInstrutor>> listar(
            @PageableDefault(size = 10 ,sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(instrutorService.listar(paginacao));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoInstrutor> atualizar(
            @RequestBody @Valid DadosAtualizacaoInstrutor dados) {
        return ResponseEntity.ok(instrutorService.atualizar(dados));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoInstrutor> excluir(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(instrutorService.excluir(id));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DadosDetalhamentoInstrutor> detalhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(instrutorService.detalhar(id));
    }

    @GetMapping("/self")
    @PreAuthorize("hasRole('INSTRUTOR')")
    public ResponseEntity<DadosDetalhamentoInstrutor> instrutorAutoDetalhar(
            Authentication authentication) {
        return ResponseEntity.ok(instrutorService.detalharPorInstrutor(authentication));
    }
}