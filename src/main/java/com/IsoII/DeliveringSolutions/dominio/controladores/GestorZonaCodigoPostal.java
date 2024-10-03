package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.dominio.entidades.ZonaCodigoPostal;
import com.IsoII.DeliveringSolutions.persistencia.ZonaCodigoPostalDAO;
import com.IsoII.DeliveringSolutions.persistencia.ZonaDAO;
import com.IsoII.DeliveringSolutions.persistencia.CodigoPostalDAO;

@Controller
@RequestMapping("/zonaCodigoPostal")
public class GestorZonaCodigoPostal {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ZonaCodigoPostalDAO zonaCodigoPostalDAO;
    @Autowired
    private ZonaDAO zonaDAO;
    @Autowired
    private CodigoPostalDAO codigoPostalDAO;

    @GetMapping("/findAll")
    @ResponseBody
    public List<ZonaCodigoPostal> findAll() {
        return zonaCodigoPostalDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        List<Zona> zonas = zonaDAO.findAll();
        List<CodigoPostal> codigosPostales = codigoPostalDAO.findAll();
        model.addAttribute("zonas", zonas);
        model.addAttribute("codigosPostales", codigosPostales);
        model.addAttribute("zonaCodigoPostal", new ZonaCodigoPostal()); // Agregar un objeto vacío al modelo
        return "Pruebas-RegisterZonaCodigoPostal"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public ZonaCodigoPostal findById(@PathVariable Long id) {
        return zonaCodigoPostalDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarZonaCodigoPostal")
    public String registrarZonaCodigoPostal(@RequestParam("codigoPostal") int codigoPostalId,
            @RequestParam("zona") int zonaId,
            RedirectAttributes redirectAttributes) {
        CodigoPostal codigoPostal = codigoPostalDAO.findById(codigoPostalId).orElse(null);
        Zona zona = zonaDAO.findById(zonaId).orElse(null);

        if (codigoPostal == null || zona == null) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una zona y un código postal válidos.");
            return "redirect:/zonaCodigoPostal/register";
        }

        ZonaCodigoPostal zonaCodigoPostal = new ZonaCodigoPostal();
        zonaCodigoPostal.setCodigoPostal(codigoPostal);
        zonaCodigoPostal.setZona(zona);

        zonaCodigoPostalDAO.save(zonaCodigoPostal);
        redirectAttributes.addFlashAttribute("success", "ZonaCodigoPostal registrada exitosamente.");
        return "redirect:/zonaCodigoPostal/register";
    }

}
