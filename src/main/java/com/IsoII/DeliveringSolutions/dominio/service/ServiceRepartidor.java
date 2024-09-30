package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Repartidor;
import com.IsoII.DeliveringSolutions.persistencia.RepartidorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRepartidor{
    @Autowired
    private RepartidorDAO repartidorDAO;

    public List<Repartidor> findAll(){
        return repartidorDAO.findAll();
    }

    public Optional<Repartidor> findById(Long id){
        return repartidorDAO.findById(id);
    }

    public Repartidor save(Repartidor repartidor){
        return repartidorDAO.save(repartidor);
    }

    public void deleteById(Long id){
        repartidorDAO.deleteById(id);
    }
}
