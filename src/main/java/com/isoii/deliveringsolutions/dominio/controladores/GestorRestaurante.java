package com.isoii.deliveringsolutions.dominio.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.service.ServiceCartaMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceDireccion;
import com.isoii.deliveringsolutions.dominio.service.ServiceRestaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controlador para gestionar los restaurantes
@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurante {
    private static final Logger logger = LoggerFactory.getLogger(GestorRestaurante.class);
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceCartaMenu serviceCartaMenu;
    private final ServiceDireccion serviceDireccion;
    private final ServiceRestaurant serviceRestaurant;

    @Autowired
    public GestorRestaurante(ServiceCartaMenu serviceCartaMenu, ServiceDireccion serviceDireccion, ServiceRestaurant serviceRestaurant) {
        this.serviceCartaMenu = serviceCartaMenu;
        this.serviceDireccion = serviceDireccion;
        this.serviceRestaurant = serviceRestaurant;
    }

    // Método para mostrar todos los restaurantes
    @GetMapping("/findAll")
    @ResponseBody
    public List<Restaurante> findAll() {
        return serviceRestaurant.findAll();
    }

    // Método para mostrar el formulario de registro de restaurante
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterRestaurante"; // Nombre del archivo HTML sin la extensión
    }

    // Método para buscar un restaurante por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Restaurante findById(@PathVariable String id) {
        return serviceRestaurant.findById(id).orElse(null);
    }

    // Método para buscar un restaurante por su nombre
    @GetMapping("/buscar")
    public String buscarRestaurante(@RequestParam String nombre, Model model) {
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(nombre);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            return "redirect:/restaurantes/findById/" + restaurante.getNombre();
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "verRestaurantes";
        }
    }

    // Método para gestionar un restaurante
    @GetMapping("/gestion/{id}")
    public String gestionRestaurante(@PathVariable String id, Model model) {
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(id);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            List<CartaMenu> menus = serviceCartaMenu.findByRestaurante(restaurante);
            model.addAttribute("restaurante", restaurante);
            model.addAttribute("menus", menus);

            List<Direccion> direcciones = serviceDireccion.findByUsuario(restaurante);
            model.addAttribute("direcciones", direcciones);
            
            if (direcciones != null) {
                model.addAttribute("direccion", direcciones);
            } else {
                model.addAttribute("direccion", new Direccion());
            }
            return "interfazGestionRestaurante";
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error"; 
        }
    }

    // Método para registrar un restaurante
    @PostMapping("/registrarRestaurante")
    public String registrarRestaurante(@ModelAttribute Restaurante restaurante, Model model) {
        logger.info("Restaurante recibido: " + restaurante.toString());
        if (restaurante.getPass() == null || restaurante.getPass().isEmpty()) {
            model.addAttribute("error", "Contraseña no puede estar vacía");
            return "error";
        }
        Restaurante restauranteRegistrado = serviceRestaurant.save(restaurante);
        logger.info("Restaurante registrado: " + restauranteRegistrado);
        return "redirect:/restaurantes/gestion/" + restauranteRegistrado.getIdUsuario();
    }

    // Método para mostrar todos los restaurantes
    @GetMapping("/mostrarRestaurantes")
    public String mostrarRestaurantes(Model model) {
        List<Restaurante> restaurantes = serviceRestaurant.findAll();
        if (restaurantes != null && !restaurantes.isEmpty()) {
            model.addAttribute("restaurantes", restaurantes);
            return "/administrador/ListaRestaurantes";
        } else {
            model.addAttribute("error", "No se encontraron restaurantes");
            return "error"; 
        }
    }

    // Método que muestra los detalles de un restaurante
    @GetMapping("/mostrarRestaurante/{id}")
    public String mostrarRestaurante(@PathVariable String id, Model model) {
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(id);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            model.addAttribute("restaurante", restaurante);
            return "/administrador/VerRestaurante"; 
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error";
        }
    }

}