package br.com.papaspizzaria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO {
    private Long pedidoId;
    private Integer produtoId;
    private Integer quantidade;
} 