package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.dominio.entidades.ZonaCodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceZona;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceZonaCodigoPostal;
import com.IsoII.DeliveringSolutions.persistencia.ZonaDAO;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/zona")
public class GestorZona {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ZonaDAO zonaDAO;

    @Autowired
    private ServiceZona serviceZona;

    @Autowired
    private ServiceZonaCodigoPostal serviceZonaCodigoPostal;

    @GetMapping("/findAll")
    @ResponseBody
    public List<Zona> findAll() {
        return zonaDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterZona"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public Zona findById(@PathVariable Integer id) {
        return zonaDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarZona")
    public String registrarZona(@ModelAttribute Zona zona) {
        System.out.println("Zona recibida: " + zona.toString());
        if (zona.getNombre() == null || zona.getNombre().isEmpty()) {
            return "Error";
        }
        Zona zonaRegistrada = zonaDAO.save(zona);
        System.out.println("Zona registrada: " + zonaRegistrada);
        // redirige a web ZonaCodigoPostal/register
        return "redirect:/zonaCodigoPostal/register";

    }

    // Método para listar todas las zonas
    @GetMapping("/mostrarZonas")
    public String mostrarZonas(Model model) {
        List<Zona> zonas = serviceZona.findAll();
        if (zonas != null && !zonas.isEmpty()) {
            model.addAttribute("zonas", zonas);
            return "/administrador/ListaZonas"; // Vista para listar zonas
        } else {
            model.addAttribute("error", "No se encontraron zonas");
            return "error"; // Vista de error si no hay zonas
        }
    }

    // Método para ver detalles de una zona
    @GetMapping("/mostrarZona/{id}")
    public String mostrarZona(@PathVariable Integer id, Model model) {
        Optional<Zona> optionalZona = serviceZona.findById(id);
        List<ZonaCodigoPostal> zonasCodigosPostales = serviceZonaCodigoPostal.findAll();
        List<CodigoPostal> codigosPostales = new ArrayList();
        if (optionalZona.isPresent()) {
            Zona zona = optionalZona.get();

            for (ZonaCodigoPostal zonaCodigoPostal : zonasCodigosPostales) {
                if (zona.getId() == (zonaCodigoPostal.getZona().getId())) {
                    codigosPostales.add(zonaCodigoPostal.getCodigoPostal());
                }
            }

            model.addAttribute("codigosPostales", codigosPostales);
            model.addAttribute("zona", zona);
            return "/administrador/VerZona"; // Vista para detalles de zona
        } else {
            model.addAttribute("error", "Zona no encontrada");
            return "error"; // Vista de error si la zona no existe
        }
    }

}
