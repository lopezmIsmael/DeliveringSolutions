package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Integer> {
    
}
