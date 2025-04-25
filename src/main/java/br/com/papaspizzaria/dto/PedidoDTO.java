package br.com.papaspizzaria.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
    private Long id;
    private Long idCliente;
    private Integer idEndereco;
    private LocalDateTime dataHora;
    private String status;
    private Double valorTotal;
    private String observacoes;
    private List<ItemPedidoDTO> itens;
} 