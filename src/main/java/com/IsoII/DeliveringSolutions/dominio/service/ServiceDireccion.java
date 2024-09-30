package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.persistencia.DireccionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceDireccion {
    @Autowired
    private DireccionDAO direccionDAO;

    public List<Direccion> findAll(){
        return direccionDAO.findAll();
    }

    public Optional<Direccion> findById(Long id){
        return direccionDAO.findById(id);
    }

    public Direccion save(Direccion direccion){
        return direccionDAO.save(direccion);
    }

    public void deleteById(Long id){
        direccionDAO.deleteById(id);
    }
}
