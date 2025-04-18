package br.com.papaspizzaria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {
    private Integer id;
    private String descricao;
    private Double precovenda;
    private String unidade;
    private Double estoque;
    private Integer grupos;
    private String image;
}