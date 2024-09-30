package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.persistencia.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrder {
    
    @Autowired
    private PedidoDAO pedidoDAO;

    public List<Pedido> findAll() {
        return pedidoDAO.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoDAO.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoDAO.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoDAO.deleteById(id);
    }
}