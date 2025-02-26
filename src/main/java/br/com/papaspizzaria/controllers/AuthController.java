package br.com.papaspizzaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.papaspizzaria.dto.AuthenticationDTO;
import br.com.papaspizzaria.dto.UsuarioDTO;
import br.com.papaspizzaria.services.AuthService;
import br.com.papaspizzaria.services.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
		return ResponseEntity.ok(authService.login(authDto));
		
	}
	
	@PostMapping(value = "/registro")
	public void register(@RequestBody UsuarioDTO usuario) {
		usuarioService.registrarNovoUsuario(usuario);
		
	}

}
