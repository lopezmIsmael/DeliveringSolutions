package com.isoii.deliveringsolutions.persistencia;

import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad Usuario
@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, String> {    
}