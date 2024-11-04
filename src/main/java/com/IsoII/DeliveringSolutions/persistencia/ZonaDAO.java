package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad Zona
@Repository
public interface ZonaDAO extends JpaRepository<Zona, Integer> {
}