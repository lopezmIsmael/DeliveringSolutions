package com.isoii.deliveringsolutions.dominio.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceCodigoPostal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

// Controlador para gestionar los códigos postales
@RestController
@RequestMapping("/codigoPostal")
public class GestorCodigoPostal {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceCodigoPostal serviceCodigoPostal;

    @Autowired
    public GestorCodigoPostal(ServiceCodigoPostal serviceCodigoPostal) {
        this.serviceCodigoPostal = serviceCodigoPostal;
    }

    // Método para obtener todos los códigos postales
    @GetMapping("/findAll")
    public List<CodigoPostal> findAll() {
        return serviceCodigoPostal.findAll();
    }

    // Método para mostrar el formulario de registro de códigos postales
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterCodigoPostal";
    }

    // Método para obtener un código postal por su id
    @GetMapping("/findById/{id}")
    public CodigoPostal findById(@PathVariable Integer id) {
        return serviceCodigoPostal.findById(id).orElse(null);
    }

    // Método para registrar un código postal
    @PostMapping("/registrarCodigoPostal")
    public ResponseEntity<CodigoPostal> registrarCodigoPostal(@ModelAttribute CodigoPostal codigoPostal) {
        System.out.println("CodigoPostal recibido: " + codigoPostal.toString());
        if (codigoPostal.getCodigo() == null || codigoPostal.getCodigo().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CodigoPostal codigoPostalRegistrado = serviceCodigoPostal.save(codigoPostal);
        System.out.println("CodigoPostal registrado: " + codigoPostalRegistrado);
        return new ResponseEntity<>(codigoPostalRegistrado, HttpStatus.CREATED);
    }

    // Método para mostrar el formulario de actualización de códigos postales
    @GetMapping("/mostrarCodigos")
    public String mostrarCodigosPostales(Model model) {
        List<CodigoPostal> codigosPostales = serviceCodigoPostal.findAll();
        if (codigosPostales != null && !codigosPostales.isEmpty()) {
            model.addAttribute("codigosPostales", codigosPostales);
            return "/administrador/ListaCodigoPostales";
        } else {
            model.addAttribute("error", "No se encontraron códigos postales");
            return "error";
        }
    }

    // Método para actualizar un código postal
    @GetMapping("/mostrarCodigo/{id}")
    public String mostrarCodigoPostal(@PathVariable Integer id, Model model) {
        Optional<CodigoPostal> optionalCodigoPostal = serviceCodigoPostal.findById(id);
        if (optionalCodigoPostal.isPresent()) {
            model.addAttribute("codigoPostal", optionalCodigoPostal.get());
            return "/administrador/VerCodigoPostal";
        } else {
            model.addAttribute("error", "Código postal no encontrado");
            return "error";
        }
    }
}