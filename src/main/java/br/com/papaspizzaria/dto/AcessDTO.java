package br.com.papaspizzaria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcessDTO {
	
	private String token;

	public AcessDTO(String token) {
		super();
		this.token = token;
	}
	
	// TODO implementar retorno do usuário e permissões (authorities)
	
	

}
