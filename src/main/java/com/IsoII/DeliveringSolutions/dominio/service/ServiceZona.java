package com.IsoII.DeliveringSolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.persistencia.ZonaDAO;

@Service
public class ServiceZona {
    @Autowired
    private ZonaDAO zonaDAO;
    
    public List<Zona> findAll(){
        return zonaDAO.findAll();
    }

    public Optional<Zona> findById(String id){
        return zonaDAO.findById(id);
    }

    public Zona save(Zona zona){
        return zonaDAO.save(zona);
    }

    public void deleteById(String id){
        zonaDAO.deleteById(id);
    }
}
