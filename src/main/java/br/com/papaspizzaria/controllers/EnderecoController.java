package br.com.papaspizzaria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.papaspizzaria.dto.EnderecoDTO;
import br.com.papaspizzaria.services.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasRole('FUNCIONARIO') or (hasRole('CLIENTE') and #idCliente == principal.id)")
    public List<EnderecoDTO> listarPorCliente(@PathVariable Integer idCliente) {
        return enderecoService.listarEnderecosDoUsuario(idCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.buscarEndereco(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EnderecoDTO> criar(@RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.criarEndereco(enderecoDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EnderecoDTO> atualizar(@PathVariable Integer id, @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.atualizarEndereco(id, enderecoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    @PreAuthorize("hasRole('FUNCIONARIO')") // Apenas funcionários
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
    	System.out.println("Endpoint /enderecos acessado"); // Log de depuração
        return ResponseEntity.ok(enderecoService.listarTodosEnderecos());
    }
    
}