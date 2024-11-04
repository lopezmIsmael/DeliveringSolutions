package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.persistencia.DireccionDAO;
import com.IsoII.DeliveringSolutions.persistencia.UsuarioDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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

    public Direccion findByUsuario(Usuario usuario) {
        System.out.println("<<USUARIO>>: " + usuario);
        String idUsuario = usuario.getIdUsuario();
        TypedQuery<Direccion> query = entityManager.createQuery(
                "SELECT d FROM Direccion d WHERE d.usuario.idUsuario = :idUsuario", Direccion.class);
        query.setParameter("idUsuario", idUsuario);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
