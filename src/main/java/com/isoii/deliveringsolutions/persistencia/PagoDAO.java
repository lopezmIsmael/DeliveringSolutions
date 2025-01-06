package com.isoii.deliveringsolutions.persistencia;

import com.isoii.deliveringsolutions.dominio.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad Pago
@Repository
public interface PagoDAO extends JpaRepository<Pago, Integer> {   
}