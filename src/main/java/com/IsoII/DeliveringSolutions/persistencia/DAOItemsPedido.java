package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemsPedidos;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DAOItemsPedido extends JpaRepository<ItemsPedidos, Long> {
    
}
