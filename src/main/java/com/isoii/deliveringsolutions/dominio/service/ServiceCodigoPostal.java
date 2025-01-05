package com.isoii.deliveringsolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.persistencia.CodigoPostalDAO;

// Clase que implementa los servicios de la entidad CodigoPostal
@Service
public class ServiceCodigoPostal {
    private final CodigoPostalDAO codigoPostalDAO;

    @Autowired
    public ServiceCodigoPostal(CodigoPostalDAO codigoPostalDAO) {
        this.codigoPostalDAO = codigoPostalDAO;
    }
    
    public List<CodigoPostal> findAll(){
        return codigoPostalDAO.findAll();
    }

    public Optional<CodigoPostal> findById(Integer id){
        return codigoPostalDAO.findById(id);
    }

    public CodigoPostal save(CodigoPostal codigoPostal){
        return codigoPostalDAO.save(codigoPostal);
    }

    public void deleteById(Integer id){
        codigoPostalDAO.deleteById(id);
    }   
}
