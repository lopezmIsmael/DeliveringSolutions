package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RepartidorDAO extends JpaRepository<Repartidor, String> {
}
