package com.IsoII.DeliveringSolutions.persistencia;

import com.IsoII.DeliveringSolutions.dominio.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaz que extiende de JpaRepository para poder realizar operaciones CRUD sobre la entidad Cliente
@Repository
public interface ClienteDAO extends JpaRepository<Cliente, String> {
}