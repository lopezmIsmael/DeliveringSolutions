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

    public List<EstadoPedido> getAllOrderStatus() {
        return estadoPedidoDAO.findAll();
    }

    public EstadoPedido getOrderStatusById(Long id) {
        return estadoPedidoDAO.findById(id).orElse(null);
    }

    public EstadoPedido saveOrderStatus(EstadoPedido estadoPedido) {
        return estadoPedidoDAO.save(estadoPedido);
    }

    public void deleteOrderStatus(Long id) {
        estadoPedidoDAO.deleteById(id);
    }
}
