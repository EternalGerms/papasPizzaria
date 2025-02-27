package br.com.papaspizzaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin // Permite requisição de diferentes origens - CORDS - útil talvez para o frontend posteriormente
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(value = "/login") // Endpoint para autenticação de usuário
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
		return ResponseEntity.ok(authService.login(authDto));
		
	}
	
	@PostMapping(value = "/registro") // Endpoint para registro de novo usuário
	public void register(@RequestBody UsuarioDTO usuario) {
		usuarioService.registrarNovoUsuario(usuario);
		
	}
	
	@GetMapping(value = "/validar-cadastro/{uuid}") // Endpoint para validar cadastro usando UUID
	public String verificarCadastro(@PathVariable("uuid") String uuid) {
		return usuarioService.verificarCadastro(uuid);
		// TODO melhorar tratamento de exceção
		
	}

}
