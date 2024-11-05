package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.ZonaCodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad ZonaCodigoPostal
@Repository
public interface ZonaCodigoPostalDAO extends JpaRepository<ZonaCodigoPostal, Long> {
}