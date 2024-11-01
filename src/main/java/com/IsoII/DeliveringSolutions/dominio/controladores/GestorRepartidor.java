package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.security.Provider.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceRepartidor;
import com.IsoII.DeliveringSolutions.dominio.entidades.Repartidor;
import com.IsoII.DeliveringSolutions.persistencia.RepartidorDAO;
import com.IsoII.DeliveringSolutions.persistencia.ZonaDAO;

@Controller
@RequestMapping("/repartidores")
public class GestorRepartidor {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private RepartidorDAO repartidorDAO;
    @Autowired
    private ZonaDAO zonaDAO;

    @Autowired
    private ServiceRepartidor serviceRepartidor;

    // ************************************************** GETMAPPING
    // ********************************************** */

    // Método que devuelve una lista de todos los repartidores
    @GetMapping("/findAll")
    @ResponseBody
    public List<Repartidor> findAll() {
        return repartidorDAO.findAll();
    }

    // Método que muestra el formulario de registro de repartidor
    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        List<Zona> zonas = zonaDAO.findAll();
        model.addAttribute("zonas", zonas);
        return "Pruebas-RegisterRepartidor"; // Nombre del archivo HTML sin la extensión
    }

    // Método que busca un solo repartidor por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Repartidor findById(@PathVariable String id) {
        return repartidorDAO.findById(id).orElse(null);
    }

    // ************************************************** POSTMAPPING
    // ********************************************** */
    // Método que registra un repartidor
    @PostMapping("/registrarRepartidor")
    public String registrarRepartidor(@ModelAttribute Repartidor repartidor, RedirectAttributes redirectAttributes) {
        // Comprobar si 'pass' no es nulo o vacío
        System.out.println("Repartidor recibido: " + repartidor.toString());
        if (repartidor.getPass() == null || repartidor.getPass().isEmpty() || repartidor.getDni().length() != 9
                || repartidor.getPass().length() < 6) {
            redirectAttributes.addFlashAttribute("error",
                    "La contraseña no puede estar vacía, el DNI debe tener 9 caracteres y la contraseña debe tener al menos 6 caracteres.");
            return "redirect:/repartidores/register"; // Devuelve un error si 'pass' está vacío
        }

        Repartidor repartidorRegistrado = repartidorDAO.save(repartidor);
        System.out.println("Repartidor registrado: " + repartidorRegistrado);
        redirectAttributes.addFlashAttribute("success", "Repartidor registrado correctamente");
        return "redirect:/"; // Redirige al index si el repartidor se registra correctamente
    }

    // Método que devuelve una lista de todos los repartidores
    @GetMapping("/mostrarRepartidores")
    public String mostrarRepartidores(Model model) {
        List<Repartidor> repartidores = serviceRepartidor.findAll();
        if (repartidores != null && !repartidores.isEmpty()) {
            model.addAttribute("repartidores", repartidores);
            return "/administrador/ListaRepartidores"; // Vista para listar repartidores
        } else {
            model.addAttribute("error", "No se encontraron repartidores");
            return "error"; // Vista de error si no se encuentran repartidores
        }
    }

    // Método que muestra los detalles de un repartidor
    @GetMapping("/mostrarRepartidor/{id}")
    public String mostrarRepartidor(@PathVariable String id, Model model) {
        Optional<Repartidor> optionalRepartidor = serviceRepartidor.findById(id);
        if (optionalRepartidor.isPresent()) {
            Repartidor repartidor = optionalRepartidor.get();
            model.addAttribute("repartidor", repartidor);
            return "/administrador/VerRepartidor"; // Vista para ver detalles de un repartidor
        } else {
            model.addAttribute("error", "Repartidor no encontrado");
            return "error"; // Vista de error si no se encuentra el repartidor
        }
    }
}
