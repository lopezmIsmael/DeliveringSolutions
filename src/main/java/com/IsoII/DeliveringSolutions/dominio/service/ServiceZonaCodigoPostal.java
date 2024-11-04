package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.ZonaCodigoPostal;
import com.IsoII.DeliveringSolutions.persistencia.ZonaCodigoPostalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad ZonaCodigoPostal
@Service
public class ServiceZonaCodigoPostal {

    @Autowired
    private ZonaCodigoPostalDAO zonaCodigoPostalDAO;

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