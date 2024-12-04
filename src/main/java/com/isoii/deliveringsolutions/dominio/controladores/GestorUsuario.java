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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("/usuarios")
public class GestorUsuario {
    private static final String REDIRECT_PREFIX = "redirect:/";
    private static final Logger logger = LoggerFactory.getLogger(GestorUsuario.class);

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceUser serviceUsuario;

    @Autowired
    public GestorUsuario(ServiceUser serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @GetMapping("/findAll")
    
    public List<Usuario> findAll() {
        return serviceUsuario.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "rol";
    }

    @GetMapping("/findById/{id}")
    
    public Usuario findById(@PathVariable String id) {
        return serviceUsuario.findById(id).orElse(null);
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "index"; 
    }

    @GetMapping("/aboutUs")
    public String mostrarAboutUs() {
        return "aboutUs"; 
    }

    @PostMapping("/registrarUsuario")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        logger.info("Usuario recibido: {}", usuario);
        if (usuario.getPass() == null || usuario.getPass().isEmpty()) {
            return REDIRECT_PREFIX + "usuarios/registrarUsuario"; 
        }

        Usuario usuarioRegistrado = serviceUsuario.save(usuario);
        logger.info("Usuario registrado: {}", usuarioRegistrado);
        return REDIRECT_PREFIX;
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        serviceUsuario.deleteById(id);
        return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
    }

    @PostMapping("/loginUsuario")
    public String loginUsuario(@RequestParam String username, @RequestParam String password,
            RedirectAttributes redirectAttributes, HttpSession session) {
        Usuario usuarioLogueado = serviceUsuario.findById(username).orElse(null);
        if (usuarioLogueado != null && usuarioLogueado.getPass().equals(password)) {
            session.setAttribute("usuario", usuarioLogueado);
            redirectAttributes.addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
            if(usuarioLogueado.gettipoUsuario().equals("CLIENTE"))
                return REDIRECT_PREFIX + "clientes/verRestaurantes";
            else if(usuarioLogueado.gettipoUsuario().equals("REPARTIDOR"))
                return REDIRECT_PREFIX + "repartidores/login";
            else if(usuarioLogueado.gettipoUsuario().equals("RESTAURANTE"))
                return REDIRECT_PREFIX + "restaurantes/gestion/" + username;
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return REDIRECT_PREFIX;
        }
        return REDIRECT_PREFIX;
    }
}
