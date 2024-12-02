package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.persistencia.DireccionDAO;
import com.isoii.deliveringsolutions.persistencia.UsuarioDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Direccion
@Service
public class ServiceDireccion {
    @Autowired
    private DireccionDAO direccionDAO;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsuarioDAO usuarioDAO;

    public List<Direccion> findAll() {
        return direccionDAO.findAll();
    }

    public Optional<Direccion> findById(Long id) {
        return direccionDAO.findById(id);
    }

    public Direccion save(Direccion direccion) {
        return direccionDAO.save(direccion);
    }

    public void deleteById(Long id) {
        direccionDAO.deleteById(id);
    }

    public Optional<Usuario> findUsuarioById(String id) {
        return usuarioDAO.findById(id);
    }

    public List<Direccion> findByUsuario(Usuario usuario) {
        return direccionDAO.findByUsuario(usuario);
    }
}

