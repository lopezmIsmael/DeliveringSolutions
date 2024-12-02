package com.isoii.deliveringsolutions.dominio.service;

import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.persistencia.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Clase que implementa los servicios de la entidad Usuario
@Service
public class ServiceUser {
    @Autowired
    private UsuarioDAO usuarioDAO;

    public List<Usuario> findAll(){
        return usuarioDAO.findAll();
    }

    public Optional<Usuario> findById(String id){
        return usuarioDAO.findById(id);
    }

    public Usuario save(Usuario usuario){
        return usuarioDAO.save(usuario);
    }

    public void deleteById(String id){
        usuarioDAO.deleteById(id);
    }
}