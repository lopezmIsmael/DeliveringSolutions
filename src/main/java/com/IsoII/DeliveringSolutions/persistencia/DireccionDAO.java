package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DireccionDAO extends JpaRepository<Direccion, Long> {

}