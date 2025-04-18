package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import com.isoii.deliveringsolutions.persistencia.ZonaCodigoPostalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad ZonaCodigoPostal
@Service
public class ServiceZonaCodigoPostal {

    private final ZonaCodigoPostalDAO zonaCodigoPostalDAO;

    @Autowired
    public ServiceZonaCodigoPostal(ZonaCodigoPostalDAO zonaCodigoPostalDAO) {
        this.zonaCodigoPostalDAO = zonaCodigoPostalDAO;
    }

    public List<ZonaCodigoPostal> findAll(){
        return zonaCodigoPostalDAO.findAll();
    }

    public Optional<ZonaCodigoPostal> findById(Long id){
        return zonaCodigoPostalDAO.findById(id);
    }

    public ZonaCodigoPostal save(ZonaCodigoPostal zonaCodigoPostal){
        return zonaCodigoPostalDAO.save(zonaCodigoPostal);
    }

    public void deleteById(Long id){
        zonaCodigoPostalDAO.deleteById(id);
    }
}