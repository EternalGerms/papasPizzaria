package br.com.papaspizzaria.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import br.com.papaspizzaria.services.UserDetailsImpl;
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
    
    @GetMapping(value = "/validar-cadastro/{uuid}")
    public String verificarCadastro(@PathVariable("uuid") String uuid) {
        return usuarioService.verificarCadastro(uuid);
    }
    
    @GetMapping(value = "/verificar-autenticacao")
    public ResponseEntity<?> verificarAutenticacao(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Map<String, Object> response = new HashMap<>();
            response.put("autenticado", true);
            response.put("username", userDetails.getUsername());
            response.put("roles", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("autenticado", false));
    }
}