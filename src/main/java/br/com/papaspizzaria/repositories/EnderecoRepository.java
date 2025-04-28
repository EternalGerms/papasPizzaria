package br.com.papaspizzaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.papaspizzaria.entities.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByIdCliente(Integer idCliente);
    
    @Query("SELECT e FROM Endereco e WHERE e.id = :id AND e.idCliente = :idCliente")
    Endereco findByIdAndIdCliente(@Param("id") Integer id, @Param("idCliente") Integer idCliente);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Endereco e WHERE e.id = :id AND e.idCliente = :idCliente")
    boolean existsByIdAndIdCliente(@Param("id") Integer id, @Param("idCliente") Integer idCliente);
}