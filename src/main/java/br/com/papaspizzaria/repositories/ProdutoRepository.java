package br.com.papaspizzaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.papaspizzaria.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}