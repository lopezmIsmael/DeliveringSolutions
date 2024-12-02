package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.persistencia.ItemMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad ItemMenu
@Service
public class ServiceItemMenu {
    @Autowired
    private ItemMenuDAO itemMenuDAO;

    public List<ItemMenu> findAll(){
        return itemMenuDAO.findAll();
    }

    public Optional<ItemMenu> findById(Integer id){
        return itemMenuDAO.findById(id);
    }

    public ItemMenu save(ItemMenu itemMenu){
        return itemMenuDAO.save(itemMenu);
    }

    public void deleteById(Integer id){
        itemMenuDAO.deleteById(id);
    }

    public List<ItemMenu> findByCartaMenu(CartaMenu cartaMenu){
        return itemMenuDAO.findByCartamenu(cartaMenu);
    }
}
