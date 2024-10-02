package com.IsoII.DeliveringSolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.persistencia.CodigoPostalDAO;

@Service
public class ServiceCodigoPostal {
    @Autowired
    private CodigoPostalDAO codigoPostalDAO;
    
    public List<CodigoPostal> findAll(){
        return codigoPostalDAO.findAll();
    }

    public Optional<CodigoPostal> findById(String id){
        return codigoPostalDAO.findById(id);
    }

    public CodigoPostal save(CodigoPostal codigoPostal){
        return codigoPostalDAO.save(codigoPostal);
    }

    public void deleteById(String id){
        codigoPostalDAO.deleteById(id);
    }   
}
