package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CodigoPostalDAO extends JpaRepository<CodigoPostal, Long> {
}