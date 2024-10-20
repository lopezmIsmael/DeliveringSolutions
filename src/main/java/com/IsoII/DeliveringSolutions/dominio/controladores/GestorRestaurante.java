package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceCartaMenu;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;

@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurante {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private RestauranteDAO restauranteDAO;


    @Autowired
    private ServiceCartaMenu serviceCartaMenu;


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
            return "interfazGestionRestaurante"; // Nombre del archivo HTML sin la extensión
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error"; // Nombre del archivo HTML de la página de error
        }
    }

    @PostMapping("/registrarRestaurante")
    public ResponseEntity<Restaurante> registrarRestaurante(@ModelAttribute Restaurante restaurante) {
        System.out.println("Restaurante recibido: " + restaurante.toString());
        if (restaurante.getPass() == null || restaurante.getPass().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Restaurante restauranteRegistrado = restauranteDAO.save(restaurante);
        System.out.println("Restaurante registrado: " + restauranteRegistrado);
        return new ResponseEntity<>(restauranteRegistrado, HttpStatus.CREATED);
    }

}
