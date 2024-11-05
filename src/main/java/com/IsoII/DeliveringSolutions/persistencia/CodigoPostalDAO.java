package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad CodigoPostal
@Repository
public interface CodigoPostalDAO extends JpaRepository<CodigoPostal, Integer> {
}