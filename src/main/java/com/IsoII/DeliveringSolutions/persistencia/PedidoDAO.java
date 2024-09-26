package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Long> {

}