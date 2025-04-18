package com.isoii.deliveringsolutions.dominio.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.ui.Model;

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Zona;
import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceZona;
import com.isoii.deliveringsolutions.dominio.service.ServiceZonaCodigoPostal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controlador para gestionar las zonas
@Controller
@RequestMapping("/zona")
public class GestorZona {
    private static final String ERROR_VIEW = "error";
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceZona serviceZona;
    private final ServiceZonaCodigoPostal serviceZonaCodigoPostal;

    private static final Logger logger = LoggerFactory.getLogger(GestorZona.class);

    @Autowired
    public GestorZona(ServiceZona serviceZona, ServiceZonaCodigoPostal serviceZonaCodigoPostal) {
        this.serviceZona = serviceZona;
        this.serviceZonaCodigoPostal = serviceZonaCodigoPostal;
    }

    // Método para mostrar todas las zonas
    @GetMapping("/findAll")
    
    public List<Zona> findAll() {
        return serviceZona.findAll();
    }

    // Método para mostrar el formulario de registro de zona
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterZona"; 
    }

    // Método para buscar una zona por su id
    @GetMapping("/findById/{id}")
    public Zona findById(@PathVariable Integer id) {
        if (id == null) {
            throw new NullPointerException("El id no debe ser nulo");
        }
        return serviceZona.findById(id).orElse(null);
    }

    // Método para registrar una zona
    @PostMapping("/registrarZona")
    public String registrarZona(@ModelAttribute Zona zona) {
        logger.info("Zona recibida: {}", zona);
        if (zona.getNombre() == null || zona.getNombre().isEmpty()) {
            return ERROR_VIEW;
        }
        Zona zonaRegistrada = serviceZona.save(zona);
        logger.info("Zona registrada: {}", zonaRegistrada);
        return "redirect:/zonaCodigoPostal/register";

    }

    // Método para listar todas las zonas
    @GetMapping("/mostrarZonas")
    public String mostrarZonas(Model model) {
        List<Zona> zonas = serviceZona.findAll();
        if (zonas != null && !zonas.isEmpty()) {
            model.addAttribute("zonas", zonas);
            return "/administrador/ListaZonas"; 
        } else {
            model.addAttribute(ERROR_VIEW, "No se encontraron zonas");
            return ERROR_VIEW; 
        }
    }

    // Método para ver detalles de una zona
    @GetMapping("/mostrarZona/{id}")
    public String mostrarZona(@PathVariable Integer id, Model model) {
        if (id == null) {
            throw new NullPointerException("El id no debe ser nulo");
        }
        Optional<Zona> optionalZona = serviceZona.findById(id);
        List<ZonaCodigoPostal> zonasCodigosPostales = serviceZonaCodigoPostal.findAll();
        List<CodigoPostal> codigosPostales = new ArrayList<>();
        if (optionalZona.isPresent()) {
            Zona zona = optionalZona.get();

            for (ZonaCodigoPostal zonaCodigoPostal : zonasCodigosPostales) {
                if (zona.getId() == (zonaCodigoPostal.getZona().getId())) {
                    codigosPostales.add(zonaCodigoPostal.getCodigoPostal());
                }
            }

            model.addAttribute("codigosPostales", codigosPostales);
            model.addAttribute("zona", zona);
            return "/administrador/VerZona"; 
        } else {
            model.addAttribute(ERROR_VIEW, "Zona no encontrada");
            return ERROR_VIEW;
        }
    }

}
