package br.com.papaspizzaria.dto;

import lombok.Getter;
import lombok.Setter;

//Gera automaticamente os métodos getters e setters para os campos
@Getter
@Setter
public class AuthenticationDTO {
	
	private String login;
	private String senha;
	
	

}
