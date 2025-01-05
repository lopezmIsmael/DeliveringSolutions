package com.isoii.deliveringsolutions.persistencia;

import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad ZonaCodigoPostal
@Repository
public interface ZonaCodigoPostalDAO extends JpaRepository<ZonaCodigoPostal, Long> {
}