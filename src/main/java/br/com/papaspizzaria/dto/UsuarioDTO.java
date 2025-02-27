package br.com.papaspizzaria.dto;

import org.springframework.beans.BeanUtils;

import br.com.papaspizzaria.entities.TipoSituacaoUsuario;
import br.com.papaspizzaria.entities.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Gera automaticamente os métodos getters e setters para os campos, e o construtor padrão sem argumentos
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
	private TipoSituacaoUsuario situacao;
	private String login;
	private String senha;
	
	public UsuarioDTO (Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
	}


}
