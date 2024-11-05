package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Restaurante
@Service
public class ServiceRestaurant {
    @Autowired
    private RestauranteDAO restauranteDAO;

    public List<Restaurante> findAll(){
        return restauranteDAO.findAll();
    }

    public Optional<Restaurante> findById(String id){
        return restauranteDAO.findById(id);
    }

    public Restaurante save(Restaurante restaurante){
        return restauranteDAO.save(restaurante);
    }

    public void deleteById(String id){
        restauranteDAO.deleteById(id);
    }
}
