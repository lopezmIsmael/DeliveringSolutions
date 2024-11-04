package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad ItemPedido
@Repository
public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Integer> {
}