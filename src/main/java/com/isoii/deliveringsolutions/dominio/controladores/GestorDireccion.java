package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.dominio.service.ServiceCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceDireccion;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

// Controlador para gestionar las direcciones
@Controller
@RequestMapping("/direccion")
public class GestorDireccion {

    private static final String USUARIO = "usuario";
    private static final String ERROR = "error";

    private final ServiceDireccion serviceDireccion;
    private final ServiceCodigoPostal serviceCodigoPostal;

    @Autowired
    public GestorDireccion(ServiceDireccion serviceDireccion, ServiceCodigoPostal serviceCodigoPostal) {
        this.serviceDireccion = serviceDireccion;
        this.serviceCodigoPostal = serviceCodigoPostal;
    }

    // Método para mostrar el formulario de registro de dirección
    @GetMapping("/formularioRegistro")
    public String mostrarFormularioRegistro(Model model, HttpSession session) {
        model.addAttribute("direccion", new Direccion());
        List<CodigoPostal> codigosPostales = serviceCodigoPostal.findAll();
        model.addAttribute("codigosPostales", codigosPostales);
        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        model.addAttribute(USUARIO, usuario);

        return "RegistrarDireccion";
    }

    // Método para registrar una dirección
    @PostMapping("/registro")
    public String registrarDireccion(@ModelAttribute Direccion direccion,
            @RequestParam("codigoPostal") Integer codigoPostalId,
            @RequestParam("idUsuario") String idUsuario,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty() ||
                direccion.getNumero() == null || direccion.getNumero().trim().isEmpty() ||
                codigoPostalId == null || idUsuario == null) {
            redirectAttributes.addFlashAttribute(ERROR, "Rellenar los campos obligatorios");
            return "redirect:/direccion/formularioRegistro";
        }

        CodigoPostal codigoPostal = serviceCodigoPostal.findById(codigoPostalId).orElse(null);
        Optional<Usuario> usuarioOptional = serviceDireccion.findUsuarioById(idUsuario);

        if (codigoPostal == null || usuarioOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(ERROR, "Código Postal o Usuario no válido");
            return "redirect:/direccion/formularioRegistro";
        }

        direccion.setCodigoPostal(codigoPostal);
        direccion.setUsuario(usuarioOptional.get());

        serviceDireccion.save(direccion);

        redirectAttributes.addFlashAttribute("mensaje", "Dirección registrada con éxito");

        Object usuario = session.getAttribute(USUARIO);

        if (usuario instanceof Cliente) {
            return "redirect:/clientes/verRestaurantes";
        }
        if (usuario instanceof Restaurante restaurante) {
            return "redirect:/restaurantes/gestion/" + restaurante.getIdUsuario();
        }

        return "redirect:/";
    }

    // Método para encontrar una dirección por ID y mostrar su información
    @GetMapping("/findById/{id}")
    
    public Direccion findById(@PathVariable Long id) {
        return serviceDireccion.findById(id).orElse(null);
    }

    // Método para encontrar todas las direcciones y mostrarlas
    @GetMapping("/findAll")
    
    public List<Direccion> findAll() {
        return serviceDireccion.findAll();
    }

    // Método para mostrar una dirección por ID y presentar su información
    @GetMapping("/mostrarDireccion/{id}")
    public String mostrarDireccion(@PathVariable Long id, Model model) {
        Optional<Direccion> direccionOpt = serviceDireccion.findById(id);
        if (direccionOpt.isPresent()) {
            model.addAttribute("direccion", direccionOpt.get());
            return "/administrador/VerDireccion";
        } else {
            model.addAttribute(ERROR, "Dirección no encontrada");
            return ERROR;
        }
    }

    // Método para mostrar todas las direcciones con manejo de error si no existen
    @GetMapping("/mostrarDirecciones")
    public String mostrarDirecciones(Model model) {
        List<Direccion> direcciones = serviceDireccion.findAll();
        if (direcciones != null && !direcciones.isEmpty()) {
            model.addAttribute("direcciones", direcciones);
            return "/administrador/ListaDirecciones";
        } else {
            model.addAttribute(ERROR, "No se encontraron direcciones");
            return ERROR;
        }
    }

}
