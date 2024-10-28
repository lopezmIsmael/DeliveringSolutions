package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import com.IsoII.DeliveringSolutions.persistencia.ServicioEntregaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;

@Controller
@RequestMapping("/servicioEntrega")
public class GestorServicioEntrega {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
    
    @Autowired
    private ServicioEntregaDAO servicioEntregaDAO;

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
        if (servicioEntrega.getFechaRecepcion() == null || servicioEntrega.getFechaEntrega() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ServicioEntrega servicioEntregaRegistrado = servicioEntregaDAO.save(servicioEntrega);
        System.out.println("ServicioEntrega registrado: " + servicioEntregaRegistrado);
        return new ResponseEntity<>(servicioEntregaRegistrado, HttpStatus.CREATED);
    }
    
}
