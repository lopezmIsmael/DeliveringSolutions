package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
}