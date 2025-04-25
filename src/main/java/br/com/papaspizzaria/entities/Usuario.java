package br.com.papaspizzaria.entities;



import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.papaspizzaria.dto.UsuarioDTO;
import java.util.Collections;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")

// Organiza a resposta do JSON... Sim... Frescura necessária...
@JsonPropertyOrder({ "id", "nome_completo", "cpfcnpj", "email", "telefone", "tipo", "situacao", "login", "senha" })

// Implementa todos os Gets e Sets sem todas aquelas linhas de código
@Getter
@Setter

// Cria um construtor padrão sem argumentos
@NoArgsConstructor

// Cria a função equals e hashcode para o ID da entidade
@EqualsAndHashCode(of = "id")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nomeCompleto;
	
	@Column(nullable = false, unique = true)
	private String cpfcnpj;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String telefone;
	
	@Column(nullable = false)
	private Integer tipo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoSituacaoUsuario situacao;
	
	@Column(nullable = false, unique = true)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    if (this.tipo == 2) { // Supondo que 2 seja o tipo para FUNCIONARIO/ADMIN
	        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
	    } else {
	        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"));
	    }
	}
	
	
	
	public Usuario(UsuarioDTO usuario) {
		BeanUtils.copyProperties(usuario, this);
	}
	

}
