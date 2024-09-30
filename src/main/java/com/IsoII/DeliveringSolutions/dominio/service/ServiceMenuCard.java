package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.persistencia.CartaMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceMenuCard {

    @Autowired
    private CartaMenuDAO cartaMenuDAO;

    public List<CartaMenu> findAll(){
        return cartaMenuDAO.findAll();
    }

    public Optional<CartaMenu> findById(Long id){
        return cartaMenuDAO.findById(id);
    }

    public CartaMenu save(CartaMenu cartaMenu){
        return cartaMenuDAO.save(cartaMenu);
    }

    public void deleteById(Long id){
        cartaMenuDAO.deleteById(id);
    }

}