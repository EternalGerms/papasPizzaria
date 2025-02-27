package br.com.papaspizzaria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.papaspizzaria.dto.AcessDTO;
import br.com.papaspizzaria.dto.AuthenticationDTO;
import br.com.papaspizzaria.entities.TipoSituacaoUsuario;
import br.com.papaspizzaria.entities.Usuario;
import br.com.papaspizzaria.repositories.UsuarioRepository;
import br.com.papaspizzaria.security.jwt.JwtUtils;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public AcessDTO login(AuthenticationDTO authDto) {
		
		try {
		// Cria mecanismo de credencial
		UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getSenha());
		
		// Prepara autenticação
		Authentication authentication = authenticationManager.authenticate(userAuth);
		
		// Busca usuário autenticado
		UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
		
		Usuario usuario = usuarioRepository.findByLogin(userAuthenticate.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
		if (usuario.getSituacao() != TipoSituacaoUsuario.ATIVO) {
			return new AcessDTO("Usuário não foi verificado. Por favor, verifique seu email para ativar sua conta.");
		}
		
		String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
		
		AcessDTO acessDto = new AcessDTO(token);
		
		return acessDto;
		
		} catch(BadCredentialsException e) {
			return new AcessDTO("Login ou senha inválidos");
		} catch(UsernameNotFoundException e) {
			return new AcessDTO("Usuário não encontrado");
		}
		
	}

}
