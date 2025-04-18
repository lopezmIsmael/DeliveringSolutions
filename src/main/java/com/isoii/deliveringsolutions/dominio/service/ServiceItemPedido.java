package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.ItemPedido;
import com.isoii.deliveringsolutions.persistencia.ItemPedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad ItemPedido
@Service
public class ServiceItemPedido {

    private final ItemPedidoDAO itemPedidoDAO;

    @Autowired
    public ServiceItemPedido(ItemPedidoDAO itemPedidoDAO) {
        this.itemPedidoDAO = itemPedidoDAO;
    }

    public List<ItemPedido> findAll(){
        return itemPedidoDAO.findAll();
    }

    public Optional<ItemPedido> findById(Integer id){
        return itemPedidoDAO.findById(id);
    }

    public ItemPedido save(ItemPedido itemsPedidos){
        return itemPedidoDAO.save(itemsPedidos);
    }

    public void deleteById(Integer id){
        itemPedidoDAO.deleteById(id);
    }

}
