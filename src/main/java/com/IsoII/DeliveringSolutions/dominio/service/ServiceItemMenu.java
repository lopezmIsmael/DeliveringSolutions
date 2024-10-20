package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.persistencia.ItemMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceItemMenu {
    @Autowired
    private ItemMenuDAO itemMenuDAO;

    public List<ItemMenu> findAll(){
        return itemMenuDAO.findAll();
    }

    public Optional<ItemMenu> findById(String id){
        return itemMenuDAO.findById(id);
    }

    public ItemMenu save(ItemMenu itemMenu){
        return itemMenuDAO.save(itemMenu);
    }

    public void deleteById(String id){
        itemMenuDAO.deleteById(id);
    }
}
