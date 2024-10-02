package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;

@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurante {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private RestauranteDAO restauranteDAO;

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
