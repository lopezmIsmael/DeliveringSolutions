package com.IsoII.DeliveringSolutions.dominio.controladores;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.persistencia.ZonaDAO;

@Controller
@RequestMapping("/zona")
public class GestorZona {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
    
    @Autowired
    private ZonaDAO zonaDAO;

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
}
