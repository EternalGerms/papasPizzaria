package br.com.papaspizzaria.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_cliente")
    private Long idCliente;
    
    @Column(name = "id_endereco")
    private Integer idEndereco;
    
    @Column(nullable = false)
    private LocalDateTime dataHora;
    
    @Column(nullable = false)
    private String status;  // PENDENTE, EM_PREPARO, EM_ENTREGA, ENTREGUE, CANCELADO
    
    @Column(name = "valor_total")
    private Double valorTotal;
    
    private String observacoes;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
} 