package com.IsoII.DeliveringSolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IsoII.DeliveringSolutions.persistencia.ServicioEntregaDAO;
import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad ServicioEntrega
@Service
public class ServiceServicioEntrega {
    
    @Autowired
    private ServicioEntregaDAO servicioEntregaDAO;

    public List<ServicioEntrega> findAll(){
        return servicioEntregaDAO.findAll();
    }

    public Optional<ServicioEntrega> findById(Integer id){
        return servicioEntregaDAO.findById(id);
    }

    public ServicioEntrega save(ServicioEntrega servicioEntrega){
        return servicioEntregaDAO.save(servicioEntrega);
    }

    public void deleteById(Integer id){
        servicioEntregaDAO.deleteById(id);
    }
}
