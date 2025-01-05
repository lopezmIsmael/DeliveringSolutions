package com.isoii.deliveringsolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.persistencia.PedidoDAO;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Pedido
@Service
public class ServicePedido {
    private final PedidoDAO pedidoDAO;

    @Autowired
    public ServicePedido(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }
    
    public List<Pedido> findAll(){
        return pedidoDAO.findAll();
    }
    
    public Optional<Pedido> findById(Integer id){
        return pedidoDAO.findById(id);
    }
    
    public Pedido save(Pedido pedido){
        return pedidoDAO.save(pedido);
    }

    public void deleteById(Integer id){
        pedidoDAO.deleteById(id);
    }
    
}
