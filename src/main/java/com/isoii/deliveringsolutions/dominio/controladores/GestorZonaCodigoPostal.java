package com.isoii.deliveringsolutions.dominio.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Zona;
import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceZona;
import com.isoii.deliveringsolutions.dominio.service.ServiceZonaCodigoPostal;

// Controlador para gestionar las zonas con códigos postales
@Controller
@RequestMapping("/zonaCodigoPostal")
public class GestorZonaCodigoPostal {
    private static final String ERROR_MESSAGE = "error";
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceZonaCodigoPostal serviceZonaCodigoPostal;
    private final ServiceZona serviceZona;
    private final ServiceCodigoPostal serviceCodigoPostal;

    @Autowired
    public GestorZonaCodigoPostal(ServiceZonaCodigoPostal serviceZonaCodigoPostal, ServiceZona serviceZona, ServiceCodigoPostal serviceCodigoPostal) {
        this.serviceZonaCodigoPostal = serviceZonaCodigoPostal;
        this.serviceZona = serviceZona;
        this.serviceCodigoPostal = serviceCodigoPostal;
    }

    // Método para listar todas las zonas con códigos postales
    @GetMapping("/findAll")
    @ResponseBody
    public List<ZonaCodigoPostal> findAll() {
        return serviceZonaCodigoPostal.findAll();
    }

    // Método para mostrar el formulario de registro de zona con código postal
    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        List<Zona> zonas = serviceZona.findAll();
        List<CodigoPostal> codigosPostales = serviceCodigoPostal.findAll();
        model.addAttribute("zonas", zonas);
        model.addAttribute("codigosPostales", codigosPostales);
        model.addAttribute("zonaCodigoPostal", new ZonaCodigoPostal()); 
        return "Pruebas-RegisterZonaCodigoPostal"; 
    }

    // Método para buscar una zona con código postal por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public ZonaCodigoPostal findById(@PathVariable Long id) {
        return serviceZonaCodigoPostal.findById(id).orElse(null);
    }

    // Método para registrar una zona con código postal
    @PostMapping("/registrarZonaCodigoPostal")
    public String registrarZonaCodigoPostal(@RequestParam("codigoPostal") List<Integer> codigoPostalIds,
            @RequestParam("zona") int zonaId,
            RedirectAttributes redirectAttributes) {

        Zona zona = serviceZona.findById(zonaId).orElse(null);

        if (zona == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Debe seleccionar una zona y un código postal válidos.");
            return "redirect:/zonaCodigoPostal/register";
        }

        for (int codigoPostalId : codigoPostalIds) {
            CodigoPostal codigoPostal = serviceCodigoPostal.findById(codigoPostalId).orElse(null);
            if (codigoPostal == null) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Debe seleccionar una zona y un código postal válidos.");
                return "redirect:/zonaCodigoPostal/register";
            }
            ZonaCodigoPostal zonaCodigoPostal = new ZonaCodigoPostal();
            zonaCodigoPostal.setCodigoPostal(codigoPostal);
            zonaCodigoPostal.setZona(zona);

            serviceZonaCodigoPostal.save(zonaCodigoPostal);
        }

        redirectAttributes.addFlashAttribute("success", "ZonaCodigoPostal registrada exitosamente.");
        return "redirect:/repartidores/register";
    }

    // Método para listar todas las zonas con códigos postales asociados
    @GetMapping("/mostrarZonasCodigoPostal")
    public String mostrarZonasCodigoPostal(Model model) {
        List<ZonaCodigoPostal> zonasCodigosPostales = serviceZonaCodigoPostal.findAll();
        if (zonasCodigosPostales != null && !zonasCodigosPostales.isEmpty()) {
            model.addAttribute("zonasCodigosPostales", zonasCodigosPostales);
            return "/administrador/ListaZonasCodigoPostal";
        } else {
            model.addAttribute(ERROR_MESSAGE, "No se encontraron zonas con códigos postales.");
            return "error"; 
        }
    }

    // Método para mostrar los detalles de una zona específica con su código postal
    @GetMapping("/mostrarZonaCodigoPostal/{id}")
    public String mostrarZonaCodigoPostal(@PathVariable Long id, Model model) {
        Optional<ZonaCodigoPostal> optionalZonaCodigoPostal = serviceZonaCodigoPostal.findById(id);
        if (optionalZonaCodigoPostal.isPresent()) {
            model.addAttribute("zonaCodigoPostal", optionalZonaCodigoPostal.get());
            return "/administrador/VerZonaCodigoPostal"; 
        } else {
            model.addAttribute(ERROR_MESSAGE, "Zona con código postal no encontrada.");
            return "error";
        }
    }

}
