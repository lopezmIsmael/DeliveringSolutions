package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.Repartidor;
import com.isoii.deliveringsolutions.persistencia.RepartidorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Repartidor
@Service
public class ServiceRepartidor {
    private final RepartidorDAO repartidorDAO;

    @Autowired
    public ServiceRepartidor(RepartidorDAO repartidorDAO) {
        this.repartidorDAO = repartidorDAO;
    }

    public List<Repartidor> findAll(){
        return repartidorDAO.findAll();
    }

    public Optional<Repartidor> findById(String id){
        return repartidorDAO.findById(id);
    }

    public Repartidor save(Repartidor repartidor){
        return repartidorDAO.save(repartidor);
    }

    public void deleteById(String id){
        repartidorDAO.deleteById(id);
    }
}
