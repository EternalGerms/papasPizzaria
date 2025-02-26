package br.com.papaspizzaria.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.papaspizzaria.entities.Usuario;

public class UserDetailsImpl implements UserDetails {

	private Long id;
	private String name;
	private String login;
	private String email;
	private String senha;
	
	
	
	public UserDetailsImpl(Long id, String name, String login, String senha , String email,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Usuario usuario) {
		return new UserDetailsImpl(usuario.getId(), usuario.getNomeCompleto(), usuario.getLogin(), usuario.getSenha(), usuario.getEmail(), new ArrayList<>());
	}
	
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


}
