package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad ServicioEntrega
@Repository
public interface ServicioEntregaDAO extends JpaRepository<ServicioEntrega, Integer> {
}