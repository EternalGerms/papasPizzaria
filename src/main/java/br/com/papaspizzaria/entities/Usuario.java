package br.com.papaspizzaria.entities;



import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@JsonPropertyOrder({ "id", "nome_completo", "cpfcnpj", "email", "telefone", "tipo", "ativado", "login", "senha" })
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome_completo")
	private String nomeCompleto;
	
	private String cpfcnpj;
	private String email;
	private String telefone;
	private Integer tipo;
	private Boolean ativado;
	private String login;
	private String senha;
	
	
	
	public Usuario() {
	}

	public Usuario(Long id, String nomeCompleto, String cpfcnpj, String email, String telefone, Integer tipo,
			Boolean ativado, String login, String senha) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.cpfcnpj = cpfcnpj;
		this.email = email;
		this.telefone = telefone;
		this.tipo = tipo;
		this.ativado = ativado;
		this.login = login;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome_completo() {
		return nomeCompleto;
	}

	public void setNome_completo(String nome_completo) {
		this.nomeCompleto = nome_completo;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtivado() {
		return ativado;
	}

	public void setAtivado(Boolean ativado) {
		this.ativado = ativado;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
	
	
	
	
	

}
