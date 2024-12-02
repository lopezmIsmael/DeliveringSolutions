package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.ServicioEntrega;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import com.isoii.deliveringsolutions.dominio.service.ServiceServicioEntrega;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;
import java.util.Optional;

// Controlador para gestionar los servicios de entrega
@Controller
@RequestMapping("/servicioEntrega")
public class GestorServicioEntrega {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceServicioEntrega serviceServicioEntrega;

    @Autowired
    public GestorServicioEntrega(ServiceServicioEntrega serviceServicioEntrega) {
        this.serviceServicioEntrega = serviceServicioEntrega;
    }

    // Método para mostrar todos los servicios de entrega
    @GetMapping("/findAll")
    @ResponseBody
    public List<ServicioEntrega> findAll() {
        return serviceServicioEntrega.findAll();
    }

    // Método para mostrar el formulario de registro de servicio de entrega
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterServicioEntrega"; 
    }

    // Método para buscar un servicio de entrega por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public ServicioEntrega findById(@PathVariable Integer id) {
        return serviceServicioEntrega.findById(id).orElse(null);
    }

    // Método para registrar un servicio de entrega
    @PostMapping("/registrarServicioEntrega")
    public ResponseEntity<ServicioEntrega> registrarServicioEntrega(@ModelAttribute ServicioEntrega servicioEntrega) {
        System.out.println("ServicioEntrega recibido: " + servicioEntrega.toString());
        if (servicioEntrega.getFechaRecepcion() == 0 || servicioEntrega.getFechaEntrega() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ServicioEntrega servicioEntregaRegistrado = serviceServicioEntrega.save(servicioEntrega);
        System.out.println("ServicioEntrega registrado: " + servicioEntregaRegistrado);
        return new ResponseEntity<>(servicioEntregaRegistrado, HttpStatus.CREATED);
    }

    // Método que devuelve una lista de todos los servicios de entrega
    @GetMapping("/mostrarServiciosEntrega")
    public String mostrarServiciosEntrega(Model model) {
        List<ServicioEntrega> serviciosEntrega = serviceServicioEntrega.findAll();
        if (serviciosEntrega != null && !serviciosEntrega.isEmpty()) {
            model.addAttribute("serviciosEntrega", serviciosEntrega);
            return "/administrador/ListaServiciosEntrega"; 
        } else {
            model.addAttribute("error", "No se encontraron servicios de entrega");
            return "error"; 
        }
    }

    // Método que muestra los detalles de un servicio de entrega
    @GetMapping("/mostrarServicioEntrega/{id}")
    public String mostrarServicioEntrega(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ServicioEntrega> optionalServicioEntrega = serviceServicioEntrega.findById(id);
        if (optionalServicioEntrega.isPresent()) {
            ServicioEntrega servicioEntrega = optionalServicioEntrega.get();
            model.addAttribute("servicioEntrega", servicioEntrega);
            return "/administrador/VerServicioEntrega"; 
        } else {
            model.addAttribute("error", "Servicio de entrega no encontrado");
            return "error"; 
        }
    }

}
