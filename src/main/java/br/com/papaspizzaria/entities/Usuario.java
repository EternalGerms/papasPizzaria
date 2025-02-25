package br.com.papaspizzaria.entities;



import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.papaspizzaria.dto.UsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@JsonPropertyOrder({ "id", "nome_completo", "cpfcnpj", "email", "telefone", "tipo", "ativado", "login", "senha" })

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
	
	@Column(nullable = false)
	private Boolean ativado;
	
	@Column(nullable = false, unique = true)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	public Usuario(UsuarioDTO usuario) {
		BeanUtils.copyProperties(usuario, this);
	}
	

}
