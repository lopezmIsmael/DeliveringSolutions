package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.persistencia.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class GestorUsuario{
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/findAll")
    public List<Usuario> findAll(){
        return usuarioDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "RegistroUsuario"; // Nombre del archivo HTML sin la extensión
    }

    @PostMapping("/registrarUsuario")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario){
        Usuario usuarioRegistrado = usuarioDAO.save(usuario);
        return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        Usuario usuario = usuarioDAO.findById(id).orElse(null);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/usuarios/registrarUsuario")
    public ResponseEntity<Usuario> registrarUsuario(
            @RequestParam String idUsuario,
            @RequestParam String pass,
            @RequestParam String tipoUsuario) {
        
        Usuario usuario = new Usuario(idUsuario, pass, tipoUsuario);
        // Lógica para registrar el usuario
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        usuarioDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}