package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.EstadoPedido;
import com.IsoII.DeliveringSolutions.persistencia.EstadoPedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrderStatus {
    
    @Autowired
    private EstadoPedidoDAO estadoPedidoDAO;

    public List<EstadoPedido> findAll() {
        return estadoPedidoDAO.findAll();
    }

    public Optional<EstadoPedido> findById(Long id) {
        return estadoPedidoDAO.findById(id);
    }

    public EstadoPedido save(EstadoPedido estadoPedido) {
        return estadoPedidoDAO.save(estadoPedido);
    }

    public void deleteById(Long id) {
        estadoPedidoDAO.deleteById(id);
    }
}