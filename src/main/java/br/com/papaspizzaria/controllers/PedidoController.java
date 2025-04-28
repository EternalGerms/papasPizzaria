package br.com.papaspizzaria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.papaspizzaria.dto.PedidoDTO;
import br.com.papaspizzaria.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/meus-pedidos")
    @PreAuthorize("isAuthenticated()")
    public List<PedidoDTO> listarMeusPedidos() {
        return pedidoService.listarPedidosDoClienteAutenticado();
    }

    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public List<PedidoDTO> listarPorCliente(@PathVariable Long idCliente) {
        return pedidoService.listarPedidosDoCliente(idCliente);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.criarPedido(pedidoDTO));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<PedidoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String novoStatus) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, novoStatus));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPedidoPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PedidoDTO> atualizar(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.atualizarPedido(id, pedidoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
} 