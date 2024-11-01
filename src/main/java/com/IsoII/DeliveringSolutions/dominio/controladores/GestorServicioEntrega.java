package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceServicioEntrega;
import com.IsoII.DeliveringSolutions.persistencia.ServicioEntregaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/servicioEntrega")
public class GestorServicioEntrega {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ServicioEntregaDAO servicioEntregaDAO;

    @Autowired
    private ServiceServicioEntrega serviceServicioEntrega;

    @GetMapping("/findAll")
    @ResponseBody
    public List<ServicioEntrega> findAll() {
        return servicioEntregaDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterServicioEntrega"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public ServicioEntrega findById(@PathVariable Integer id) {
        return servicioEntregaDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarServicioEntrega")
    public ResponseEntity<ServicioEntrega> registrarServicioEntrega(@ModelAttribute ServicioEntrega servicioEntrega) {
        System.out.println("ServicioEntrega recibido: " + servicioEntrega.toString());
        if (servicioEntrega.getFechaRecepcion() == 0 || servicioEntrega.getFechaEntrega() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ServicioEntrega servicioEntregaRegistrado = servicioEntregaDAO.save(servicioEntrega);
        System.out.println("ServicioEntrega registrado: " + servicioEntregaRegistrado);
        return new ResponseEntity<>(servicioEntregaRegistrado, HttpStatus.CREATED);
    }

    // Método que devuelve una lista de todos los servicios de entrega
    @GetMapping("/mostrarServiciosEntrega")
    public String mostrarServiciosEntrega(Model model) {
        List<ServicioEntrega> serviciosEntrega = serviceServicioEntrega.findAll();
        if (serviciosEntrega != null && !serviciosEntrega.isEmpty()) {
            model.addAttribute("serviciosEntrega", serviciosEntrega);
            return "/administrador/ListaServiciosEntrega"; // Vista para listar servicios de entrega
        } else {
            model.addAttribute("error", "No se encontraron servicios de entrega");
            return "error"; // Vista de error si no se encuentran servicios de entrega
        }
    }

    // Método que muestra los detalles de un servicio de entrega
    @GetMapping("/mostrarServicioEntrega/{id}")
    public String mostrarServicioEntrega(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ServicioEntrega> optionalServicioEntrega = serviceServicioEntrega.findById(id);
        if (optionalServicioEntrega.isPresent()) {
            ServicioEntrega servicioEntrega = optionalServicioEntrega.get();
            model.addAttribute("servicioEntrega", servicioEntrega);
            return "/administrador/VerServicioEntrega"; // Vista para ver detalles de un servicio de entrega
        } else {
            model.addAttribute("error", "Servicio de entrega no encontrado");
            return "error"; // Vista de error si no se encuentra el servicio de entrega
        }
    }

}
