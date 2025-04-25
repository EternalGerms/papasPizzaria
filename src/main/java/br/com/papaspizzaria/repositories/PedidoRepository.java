package br.com.papaspizzaria.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.papaspizzaria.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByIdCliente(Long idCliente);
} 