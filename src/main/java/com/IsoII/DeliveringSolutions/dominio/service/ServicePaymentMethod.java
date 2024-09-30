package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.MetodoPago;
import com.IsoII.DeliveringSolutions.persistencia.MetodoPagoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePaymentMethod {
    
    @Autowired
    private MetodoPagoDAO metodoPagoDAO;

    public List<MetodoPago> findAll() {
        return metodoPagoDAO.findAll();
    }

    public Optional<MetodoPago> findById(Long id) {
        return metodoPagoDAO.findById(id);
    }

    public MetodoPago save(MetodoPago metodoPago) {
        return metodoPagoDAO.save(metodoPago);
    }

    public void deleteById(Long id) {
        metodoPagoDAO.deleteById(id);
    }
}