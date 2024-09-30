package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PagoDAO extends JpaRepository<Pago, Long> {
}