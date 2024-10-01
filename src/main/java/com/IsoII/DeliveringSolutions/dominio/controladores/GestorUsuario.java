package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.persistencia.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class GestorUsuario{
    @Autowired
    private UsuarioDAO UsuarioDAO;

    @GetMapping
    public List<Usuario> findAll(){
        return UsuarioDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        Usuario usuario = UsuarioDAO.findById(id).orElse(null);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
        return new ResponseEntity<>(UsuarioDAO.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        UsuarioDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}