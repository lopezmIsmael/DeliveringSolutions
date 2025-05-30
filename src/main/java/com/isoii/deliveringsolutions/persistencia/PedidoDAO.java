package com.isoii.deliveringsolutions.persistencia;

import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad Pedido
@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer> {  
}