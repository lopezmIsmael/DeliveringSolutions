package com.IsoII.DeliveringSolutions.dominio.controladores;

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

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceCartaMenu;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceRestaurant;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;

@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurante {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private RestauranteDAO restauranteDAO;

    @Autowired
    private ServiceCartaMenu serviceCartaMenu;

    @Autowired
    private ServiceDireccion serviceDireccion;

    @Autowired
    private ServiceRestaurant serviceRestaurant;

    @GetMapping("/findAll")
    @ResponseBody
    public List<Restaurante> findAll() {
        return restauranteDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterRestaurante"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public Restaurante findById(@PathVariable String id) {
        return restauranteDAO.findById(id).orElse(null);
    }

    @GetMapping("/buscar")
    public String buscarRestaurante(@RequestParam String nombre, Model model) {
        Optional<Restaurante> optionalRestaurante = restauranteDAO.findById(nombre);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            return "redirect:/restaurantes/findById/" + restaurante.getNombre();
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "verRestaurantes"; // Nombre del archivo HTML sin la extensión
        }
    }

    @GetMapping("/gestion/{id}")
    public String gestionRestaurante(@PathVariable String id, Model model) {
        Optional<Restaurante> optionalRestaurante = restauranteDAO.findById(id);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            List<CartaMenu> menus = serviceCartaMenu.findByRestaurante(restaurante);
            model.addAttribute("restaurante", restaurante);
            model.addAttribute("menus", menus);

            Direccion direccion = serviceDireccion.findByUsuario(restaurante);

            if (direccion != null) {
                model.addAttribute("direccion", direccion);
            } else {
                model.addAttribute("direccion", new Direccion());
            }
            return "interfazGestionRestaurante"; // Nombre del archivo HTML sin la extensión
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error"; // Nombre del archivo HTML de la página de error
        }
    }

    @PostMapping("/registrarRestaurante")
    public String registrarRestaurante(@ModelAttribute Restaurante restaurante, Model model) {
        System.out.println("Restaurante recibido: " + restaurante.toString());
        if (restaurante.getPass() == null || restaurante.getPass().isEmpty()) {
            model.addAttribute("error", "Contraseña no puede estar vacía");
            return "error"; // Nombre del archivo HTML de la página de error
        }
        Restaurante restauranteRegistrado = restauranteDAO.save(restaurante);
        System.out.println("Restaurante registrado: " + restauranteRegistrado);
        return "redirect:/restaurantes/gestion/" + restauranteRegistrado.getIdUsuario();
    }

    // Método que devuelve una lista de todos los restaurantes
    @GetMapping("/mostrarRestaurantes")
    public String mostrarRestaurantes(Model model) {
        List<Restaurante> restaurantes = serviceRestaurant.findAll();
        if (restaurantes != null && !restaurantes.isEmpty()) {
            model.addAttribute("restaurantes", restaurantes);
            return "/administrador/ListaRestaurantes"; // Vista para listar restaurantes
        } else {
            model.addAttribute("error", "No se encontraron restaurantes");
            return "error"; // Vista de error si no se encuentran restaurantes
        }
    }

    // Método que muestra los detalles de un restaurante
    @GetMapping("/mostrarRestaurante/{id}")
    public String mostrarRestaurante(@PathVariable String id, Model model) {
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(id);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            model.addAttribute("restaurante", restaurante);
            return "/administrador/VerRestaurante"; // Vista para ver detalles de un restaurante
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error"; // Vista de error si no se encuentra el restaurante
        }
    }

}