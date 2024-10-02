package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.persistencia.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class GestorUsuario {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private UsuarioDAO usuarioDAO;

    // ************************************************** GETMAPPING
    // ********************************************** */

    // Método que devuelve una lista de todos los usuarios
    @GetMapping("/findAll")
    @ResponseBody
    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    // Método que muestra el formulario de registro de usuario
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegistroUsuario"; // Nombre del archivo HTML sin la extensión
    }

    // Método que busca un solo usuario por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Usuario findById(@PathVariable String id) {
        return usuarioDAO.findById(id).orElse(null);
    }

    // Metodo que muestra formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "Pruebas-Login"; // Nombre del archivo HTML sin la extensión
    }

    // ************************************************** POSTMAPPING
    // ********************************************** */
    // Método que registra un usuario
    @PostMapping("/registrarUsuario")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioRegistrado = usuarioDAO.save(usuario);
        return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
    }

    // Método que logea un usuario
    @PostMapping("/loginUsuario")
    public String loginUsuario(@RequestParam String username, @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = usuarioDAO.findById(username).orElse(null);
        if (usuarioLogueado != null && usuarioLogueado.getPass().equals(password)) {
            redirectAttributes.addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
            return "redirect:/usuarios/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/usuarios/login";
        }
    }
}
