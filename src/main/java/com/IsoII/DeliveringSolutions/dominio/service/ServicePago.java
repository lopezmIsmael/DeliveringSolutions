package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import com.IsoII.DeliveringSolutions.persistencia.PagoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePago {

    @Autowired
    private PagoDAO pagoDAO;

    public List<Pago> findAll(){
        return pagoDAO.findAll();
    }

    public Optional<Pago> findById(Long id){
        return pagoDAO.findById(id);
    }

    public Pago save(Pago pago){
        return pagoDAO.save(pago);
    }

    public void deleteById(Long id){
        pagoDAO.deleteById(id);
    }
}