package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad ItemMenu
@Repository
public interface ItemMenuDAO extends JpaRepository<ItemMenu, Integer> {
    List<ItemMenu> findByCartamenu(CartaMenu cartaMenu);
}