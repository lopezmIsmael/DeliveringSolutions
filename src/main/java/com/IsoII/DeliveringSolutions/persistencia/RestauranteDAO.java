package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RestauranteDAO extends JpaRepository<Restaurante, String> {
}
