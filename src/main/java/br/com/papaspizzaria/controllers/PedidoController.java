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

    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasRole('FUNCIONARIO') or (hasRole('CLIENTE') and #idCliente == principal.id)")
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
} 