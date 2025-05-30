package com.isoii.deliveringsolutions.persistencia;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad CartaMenu
@Repository
public interface CartaMenuDAO extends JpaRepository<CartaMenu, Integer> {
}