package br.com.papaspizzaria.entities;

import br.com.papaspizzaria.dto.EnderecoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enderecosadd")
@Getter
@Setter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "idcliente")
    private Integer idCliente;
    
    private String endereco;
    private String bairro;
    private String numero;
    private String complemento;
    private String observac; // Note que no banco está como 'observac'
    
    public Endereco() {}
    
    public Endereco(EnderecoDTO dto) {
        this.id = dto.getId();
        this.idCliente = dto.getIdCliente();
        this.endereco = dto.getEndereco();
        this.bairro = dto.getBairro();
        this.numero = dto.getNumero();
        this.complemento = dto.getComplemento();
        this.observac= dto.getObservac();
    }
}