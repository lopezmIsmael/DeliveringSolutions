package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.persistencia.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceUser{
    @Autowired
    private UsuarioDAO usuarioDAO;

    public List<Usuario> findAll(){
        return usuarioDAO.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioDAO.findById(id);
    }

    public Usuario save(Usuario usuario){
        return usuarioDAO.save(usuario);
    }

    public void deleteById(Long id){
        usuarioDAO.deleteById(id);
    }
}