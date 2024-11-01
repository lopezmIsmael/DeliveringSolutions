package com.IsoII.DeliveringSolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.persistencia.PedidoDAO;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePedido {
    @Autowired
    private static PedidoDAO pedidoDAO;
    
        public List<Pedido> findAll(){
            return pedidoDAO.findAll();
        }
    
        public Optional<Pedido> findById(Integer id){
            return pedidoDAO.findById(id);
        }
    
        public static Pedido save(Pedido pedido){
            return pedidoDAO.save(pedido);
    }

    public void deleteById(Integer id){
        pedidoDAO.deleteById(id);
    }
    
}
