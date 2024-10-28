package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.persistencia.DireccionDAO;
import com.IsoII.DeliveringSolutions.persistencia.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDireccion {
    @Autowired
    private DireccionDAO direccionDAO;

    @Autowired
    private UsuarioDAO usuarioDAO; // Añadido para manejar la entidad Usuario

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

    // Método para encontrar un usuario por su ID
    public Optional<Usuario> findUsuarioById(String id) {
        return usuarioDAO.findById(id);
    }
}
