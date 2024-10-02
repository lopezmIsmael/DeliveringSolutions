package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Cliente;
import com.IsoII.DeliveringSolutions.persistencia.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceClient {
    @Autowired
    private ClienteDAO clienteDAO;

    public List<Cliente> findAll(){
        return clienteDAO.findAll();
    }

    public Optional<Cliente> findById(String id){
        return clienteDAO.findById(id);
    }

    public Cliente save(Cliente cliente){
        return clienteDAO.save(cliente);
    }

    public void deleteById(String id){
        clienteDAO.deleteById(id);
    }
}
