package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.TipoItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TipoItemMenuDAO extends JpaRepository<TipoItemMenu, Long> {
}