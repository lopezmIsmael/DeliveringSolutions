package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartaMenuDAO extends JpaRepository<CartaMenu, Long> {
}