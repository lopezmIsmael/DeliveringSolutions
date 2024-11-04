package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.dominio.entidades.ZonaCodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceZonaCodigoPostal;
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

    @Autowired
    private ServiceZonaCodigoPostal serviceZonaCodigoPostal;

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
    public String registrarZonaCodigoPostal(@RequestParam("codigoPostal") List<Integer> codigoPostalIds,
            @RequestParam("zona") int zonaId,
            RedirectAttributes redirectAttributes) {

        Zona zona = zonaDAO.findById(zonaId).orElse(null);

        if (zona == null) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una zona y un código postal válidos.");
            return "redirect:/zonaCodigoPostal/register";
        }

        for (int codigoPostalId : codigoPostalIds) {
            CodigoPostal codigoPostal = codigoPostalDAO.findById(codigoPostalId).orElse(null);
            if (codigoPostal == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar una zona y un código postal válidos.");
                return "redirect:/zonaCodigoPostal/register";
            }
            ZonaCodigoPostal zonaCodigoPostal = new ZonaCodigoPostal();
            zonaCodigoPostal.setCodigoPostal(codigoPostal);
            zonaCodigoPostal.setZona(zona);

            zonaCodigoPostalDAO.save(zonaCodigoPostal);
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
            return "/administrador/ListaZonasCodigoPostal"; // Vista para mostrar la lista de zonas con códigos postales
        } else {
            model.addAttribute("error", "No se encontraron zonas con códigos postales.");
            return "error"; // Vista de error si no se encuentran zonas con códigos postales
        }
    }

    // Método para mostrar los detalles de una zona específica con su código postal
    @GetMapping("/mostrarZonaCodigoPostal/{id}")
    public String mostrarZonaCodigoPostal(@PathVariable Long id, Model model) {
        Optional<ZonaCodigoPostal> optionalZonaCodigoPostal = serviceZonaCodigoPostal.findById(id);
        if (optionalZonaCodigoPostal.isPresent()) {
            model.addAttribute("zonaCodigoPostal", optionalZonaCodigoPostal.get());
            return "/administrador/VerZonaCodigoPostal"; // Vista para mostrar los detalles de la zona específica
        } else {
            model.addAttribute("error", "Zona con código postal no encontrada.");
            return "error"; // Vista de error si no se encuentra la zona específica
        }
    }

}
