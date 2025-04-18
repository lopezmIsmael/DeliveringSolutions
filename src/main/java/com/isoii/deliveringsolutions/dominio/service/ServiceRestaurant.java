package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.persistencia.RestauranteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Restaurante
@Service
public class ServiceRestaurant {
    private final RestauranteDAO restauranteDAO;

    @Autowired
    public ServiceRestaurant(RestauranteDAO restauranteDAO) {
        this.restauranteDAO = restauranteDAO;
    }

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
