package com.isoii.deliveringsolutions.dominio.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.dominio.service.ServiceUser;

import jakarta.servlet.http.HttpSession;

// Controlador para gestionar los usuarios
@Controller
@RequestMapping("/usuarios")
public class GestorUsuario {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ServiceUser serviceUsuario;

    // Método que devuelve una lista de todos los clientes
    @GetMapping("/findAll")
    @ResponseBody
    public List<Usuario> findAll() {
        return serviceUsuario.findAll();
    }

    // Método que muestra el formulario de registro de cliente
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "rol";
    }

    // Método que busca un solo cliente por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Usuario findById(@PathVariable String id) {
        return serviceUsuario.findById(id).orElse(null);
    }

    // Metodo que muestra formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "index"; 
    }

    // Metodo que muestra formulario de aboutUs
    @GetMapping("/aboutUs")
    public String mostrarAboutUs() {
        return "aboutUs"; 
    }


    // Método que registra un cliente
    @PostMapping("/registrarUsuario")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("Usuario recibido: " + usuario.toString());
        if (usuario.getPass() == null || usuario.getPass().isEmpty()) {
            return "redirect:/usuarios/registrarUsuario"; 
        }

        Usuario usuarioRegistrado = serviceUsuario.save(usuario);
        System.out.println("Usuario registrado: " + usuarioRegistrado);
        return "redirect:/";
    }

    // Método que elimina un cliente por su id
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        serviceUsuario.deleteById(id);
        return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
    }

    // Método para loguear un usuario
    @PostMapping("/loginUsuario")
    public String loginUsuario(@RequestParam String username, @RequestParam String password,
            RedirectAttributes redirectAttributes, HttpSession session) {
        Usuario usuarioLogueado = serviceUsuario.findById(username).orElse(null);
        if (usuarioLogueado != null && usuarioLogueado.getPass().equals(password)) {
            session.setAttribute("usuario", usuarioLogueado);
            redirectAttributes.addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
            if(usuarioLogueado.gettipoUsuario().equals("CLIENTE"))
                return "redirect:/clientes/verRestaurantes";
            else if(usuarioLogueado.gettipoUsuario().equals("REPARTIDOR"))
                return "redirect:/repartidores/login";
            else if(usuarioLogueado.gettipoUsuario().equals("RESTAURANTE"))
                return "redirect:/restaurantes/gestion/" + username;
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/";
        }
        return "redirect:/";
    }
}