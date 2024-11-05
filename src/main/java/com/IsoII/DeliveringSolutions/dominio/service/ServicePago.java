package com.IsoII.DeliveringSolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import com.IsoII.DeliveringSolutions.persistencia.PagoDAO;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Pago
@Service
public class ServicePago {

    @Autowired
    private PagoDAO pagoDAO;

    public List<Pago> findAll(){
        return pagoDAO.findAll();
    }

    public Optional<Pago> findById(Integer id){
        return pagoDAO.findById(id);
    }

    public Pago save(Pago pago){
        return pagoDAO.save(pago);
    }

    public void deleteById(Integer id){
        pagoDAO.deleteById(id);
    }
    
}
