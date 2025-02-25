package br.com.papaspizzaria.dto;

import org.springframework.beans.BeanUtils;

import br.com.papaspizzaria.entities.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

	private Long id;
	
	private String nomeCompleto;
	private String cpfcnpj;
	private String email;
	private String telefone;
	private Integer tipo;
	private Boolean ativado = false;
	private String login;
	private String senha;
	
	public UsuarioDTO (Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
	}


}
