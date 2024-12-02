package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.Zona;
import com.isoii.deliveringsolutions.persistencia.ZonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Zona
@Service
public class ServiceZona {
    @Autowired
    private ZonaDAO zonaDAO;
    
    public List<Zona> findAll(){
        return zonaDAO.findAll();
    }

    public Optional<Zona> findById(Integer id){
        return zonaDAO.findById(id);
    }

    public Zona save(Zona zona){
        return zonaDAO.save(zona);
    }

    public void deleteById(Integer id){
        zonaDAO.deleteById(id);
    }
}
