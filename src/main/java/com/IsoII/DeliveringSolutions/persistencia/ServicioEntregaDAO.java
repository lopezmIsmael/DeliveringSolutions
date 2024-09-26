package com.IsoII.DeliveringSolutions.persistencia;

import org.springframework.stereotype.Repository;
import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ServicioEntregaDAO extends JpaRepository<ServicioEntrega, Long> {    
}
