package br.com.papaspizzaria.repositories;

import br.com.papaspizzaria.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByIdCliente(Integer idCliente);
    
    @Query("SELECT e FROM Endereco e WHERE e.id = :id AND e.idCliente = :idCliente")
    Endereco findByIdAndIdCliente(@Param("id") Integer id, @Param("idCliente") Integer idCliente);
    List<Endereco> findAll(); // Já fornecido pelo JpaRepository
}