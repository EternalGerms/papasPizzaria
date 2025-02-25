package br.com.papaspizzaria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.papaspizzaria.dto.UsuarioDTO;
import br.com.papaspizzaria.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuario(id);
    }

    @PostMapping
    public void salvarUsuario(@RequestBody UsuarioDTO usuario) {
        usuarioService.salvarUsuario(usuario);
    }
    
    @PutMapping
    public UsuarioDTO alterarUsuario(@RequestBody UsuarioDTO usuario) {
    	return usuarioService.alterarUsuario(usuario);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
    	usuarioService.excluirUsuario(id);
    	return ResponseEntity.ok().build();
    }
}



