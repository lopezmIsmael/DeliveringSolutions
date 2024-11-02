package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ItemMenuDAO extends JpaRepository<ItemMenu, Integer> {
    List<ItemMenu> findByCartamenu(CartaMenu cartaMenu);
}

