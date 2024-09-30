package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.persistencia.CodigoPostalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePostalCode {
    
    @Autowired
    private CodigoPostalDAO codigoPostalDAO;

    public List<CodigoPostal> findAll(){
        return codigoPostalDAO.findAll();
    }

    public Optional<CodigoPostal> findById(Long id){
        return codigoPostalDAO.findById(id);
    }

    public CodigoPostal save(CodigoPostal codigoPostal){
        return codigoPostalDAO.save(codigoPostal);
    }

    public void deleteById(Long id){
        codigoPostalDAO.deleteById(id);
    }

}
