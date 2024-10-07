package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.*;
import com.IsoII.DeliveringSolutions.persistencia.*;
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

    // Método que devuelve una lista de todos los clientes
    @GetMapping("/findAll")
    @ResponseBody
    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    // Método que muestra el formulario de registro de cliente
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "rol"; // Nombre del archivo HTML sin la extensión
    }

    // Método que busca un solo cliente por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Usuario findById(@PathVariable String id) {
        return usuarioDAO.findById(id).orElse(null);
    }

    // Metodo que muestra formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "index"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/aboutUs")
    public String mostrarAboutUs() {
        return "aboutUs"; // Nombre del archivo HTML sin la extensión
    }

    // ************************************************** POSTMAPPING
    // ********************************************** */
    // Método que registra un cliente
    @PostMapping("/registrarUsuario")
    public ResponseEntity<Usuario> registrarCliente(@ModelAttribute Usuario usuario) {
        // Comprobar si 'pass' no es nulo o vacío
        System.out.println("Cliente recibido: " + usuario.toString());
        if (usuario.getPass() == null || usuario.getPass().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Devuelve un error si 'pass' está vacío
        }

        Usuario usuarioRegistrado = usuarioDAO.save(usuario);
        System.out.println("Usuario registrado: " + usuarioRegistrado);
        return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
    }

    // Método que elimina un cliente por su id
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        usuarioDAO.deleteById(id);
        return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
    }

    @PostMapping("/loginUsuario")
    public String loginUsuario(@RequestParam String username, @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = usuarioDAO.findById(username).orElse(null);
        if (usuarioLogueado != null && usuarioLogueado.getPass().equals(password)) {
            redirectAttributes.addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/";
        }
    }
}