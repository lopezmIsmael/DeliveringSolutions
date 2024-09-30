package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MetodoPagoDAO extends JpaRepository<MetodoPago, Long> {

}