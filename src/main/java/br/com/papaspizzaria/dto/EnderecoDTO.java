package br.com.papaspizzaria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private Integer id;
    private Integer idCliente;
    private String endereco;
    private String bairro;
    private String numero;
    private String complemento;
    private String observacao;
}