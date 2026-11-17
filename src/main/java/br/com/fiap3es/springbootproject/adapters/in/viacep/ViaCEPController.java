package br.com.fiap3es.springbootproject.adapters.in.viacep;

import br.com.fiap3es.springbootproject.adapters.in.viacep.dto.DadosEnderecoViaCEPResponse;
import br.com.fiap3es.springbootproject.application.core.services.ViaCEPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class ViaCEPController {
    private final ViaCEPService viaCEPService;

    public ViaCEPController(ViaCEPService viaCEPService) {
        this.viaCEPService = viaCEPService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<DadosEnderecoViaCEPResponse> buscarPorCEP(@PathVariable String cep) {
        return ResponseEntity.ok(viaCEPService.buscar(cep));
    }
}