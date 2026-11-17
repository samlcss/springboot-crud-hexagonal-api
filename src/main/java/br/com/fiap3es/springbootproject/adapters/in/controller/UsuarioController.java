package br.com.fiap3es.springbootproject.adapters.in.controller;

import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAtualizacaoUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAlteracaoSenha;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosAtualizacaoPerfil;
import br.com.fiap3es.springbootproject.adapters.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3es.springbootproject.adapters.in.controller.response.usuario.DadosListagemUsuario;
import br.com.fiap3es.springbootproject.application.core.usecases.UsuarioService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController implements ModelDomainController<
        DadosCadastroUsuario,
        DadosListagemUsuario,
        DadosAtualizacaoUsuario,
        Void,
        DadosDetalhamentoUsuario,
        Long> {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoUsuario dto = usuarioService.cadastrar(dados);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @ParameterObject @PageableDefault(size = 10 ,sort = {"id"}) Pageable paginacao) {
        return ResponseEntity.ok(usuarioService.listar(paginacao));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(
            @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return ResponseEntity.ok(usuarioService.atualizar(dados));
    }

    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarPerfil(
            @RequestBody @Valid DadosAtualizacaoPerfil dados) {
        return ResponseEntity.ok(usuarioService.atualizarPerfil(dados));
    }

    @PatchMapping("/senha")
    public ResponseEntity<String> alterarSenha(
            @RequestBody DadosAlteracaoSenha dados) {
        usuarioService.alterarSenha(dados);
        return ResponseEntity.ok("Senha Alterada com Sucesso!");
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> excluir(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarioService.excluir(id));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.detalhar(id));
    }
}