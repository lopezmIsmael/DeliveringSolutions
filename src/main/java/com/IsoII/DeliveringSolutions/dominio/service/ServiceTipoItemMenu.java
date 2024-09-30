package com.IsoII.DeliveringSolutions.dominio.service;

import com.IsoII.DeliveringSolutions.dominio.entidades.TipoItemMenu;
import com.IsoII.DeliveringSolutions.persistencia.TipoItemMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceTipoItemMenu{
    @Autowired
    private TipoItemMenuDAO tipoItemMenuDAO;

    public List<TipoItemMenu> findAll(){
        return tipoItemMenuDAO.findAll();
    }

    public Optional<TipoItemMenu> findById(Long id){
        return tipoItemMenuDAO.findById(id);
    }

    public TipoItemMenu save(TipoItemMenu tipoItemMenu){
        return tipoItemMenuDAO.save(tipoItemMenu);
    }

    public void deleteById(Long id){
        tipoItemMenuDAO.deleteById(id);
    }
}