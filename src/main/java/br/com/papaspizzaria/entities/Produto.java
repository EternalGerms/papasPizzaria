package br.com.papaspizzaria.entities;

import br.com.papaspizzaria.dto.ProdutoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false)
    private Double precovenda;
    
    private String unidade;
    private Double estoque;
    private Integer grupos;
    private String image;
    
    public Produto(ProdutoDTO produtoDTO) {
        this.descricao = produtoDTO.getDescricao();
        this.precovenda = produtoDTO.getPrecovenda();
        this.unidade = produtoDTO.getUnidade();
        this.estoque = produtoDTO.getEstoque();
        this.grupos = produtoDTO.getGrupos();
        this.image = produtoDTO.getImage();
    }
}