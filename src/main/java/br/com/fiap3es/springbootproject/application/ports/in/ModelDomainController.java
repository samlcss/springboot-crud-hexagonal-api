package br.com.fiap3es.springbootproject.application.ports.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ModelDomainController<C, R, U, D, DET, ID> {
    ResponseEntity<DET> cadastrar(C dados, UriComponentsBuilder uriBuilder);
    ResponseEntity<Page<R>> listar(Pageable pageable);
    ResponseEntity<DET> atualizar(U dados);
    ResponseEntity<DET> excluir(ID id);
    ResponseEntity<DET> detalhar(ID id);
}