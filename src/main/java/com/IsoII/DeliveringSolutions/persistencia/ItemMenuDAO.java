package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ItemMenuDAO extends JpaRepository<ItemMenu, Long> {
} 