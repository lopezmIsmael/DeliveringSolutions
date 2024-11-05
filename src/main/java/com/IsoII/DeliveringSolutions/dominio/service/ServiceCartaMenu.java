package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.persistencia.CartaMenuDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad CartaMenu
@Service
public class ServiceCartaMenu {
    @Autowired
    private CartaMenuDAO cartaMenuDAO;

    @PersistenceContext
    private EntityManager entityManager;

    public List<CartaMenu> findAll(){
        return cartaMenuDAO.findAll();
    }

    public Optional<CartaMenu> findById(Integer id){
        return cartaMenuDAO.findById(id);
    }

    public List<CartaMenu> findByRestaurante(Restaurante restaurante){
        String cif = restaurante.getCif();
        TypedQuery<CartaMenu> query = entityManager.createQuery(
            "SELECT cm FROM CartaMenu cm WHERE cm.restaurante.cif = :cif", CartaMenu.class);
        query.setParameter("cif", cif);
        return query.getResultList();
    }

    public CartaMenu save(CartaMenu cartaMenu){
        return cartaMenuDAO.save(cartaMenu);
    }

    public void deleteById(Integer id){
        cartaMenuDAO.deleteById(id);
    }
}
