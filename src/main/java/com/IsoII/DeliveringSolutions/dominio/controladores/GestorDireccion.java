package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceCodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/direccion")
public class GestorDireccion {

    @Autowired
    private ServiceDireccion serviceDireccion;

    @Autowired
    private ServiceCodigoPostal serviceCodigoPostal;

    // Método para mostrar el formulario de registro de dirección
    @GetMapping("/formularioRegistro")
    public String mostrarFormularioRegistro(Model model, HttpSession session) {
        model.addAttribute("direccion", new Direccion());
        List<CodigoPostal> codigosPostales = serviceCodigoPostal.findAll();
        model.addAttribute("codigosPostales", codigosPostales);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "RegistrarDireccion";
    }

    // Método para registrar una dirección
    @PostMapping("/registro")
    public String registrarDireccion(@ModelAttribute Direccion direccion,
            @RequestParam("codigoPostal") Integer codigoPostalId,
            @RequestParam("idUsuario") String idUsuario,
            RedirectAttributes redirectAttributes) {
        // Validación
        if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty() ||
                direccion.getNumero() == null || direccion.getNumero().trim().isEmpty() ||
                codigoPostalId == null || idUsuario == null) {
            redirectAttributes.addFlashAttribute("error", "Rellenar los campos obligatorios");
            return "redirect:/direccion/formularioRegistro";
        }

        // Recuperar el Código Postal y el Usuario de la base de datos
        CodigoPostal codigoPostal = serviceCodigoPostal.findById(codigoPostalId).orElse(null);
        Optional<Usuario> usuarioOptional = serviceDireccion.findUsuarioById(idUsuario); // Añade este método en el
                                                                                         // ServiceDireccion

        if (codigoPostal == null || usuarioOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Código Postal o Usuario no válido");
            return "redirect:/direccion/formularioRegistro";
        }

        // Asignar las entidades a la Dirección
        direccion.setCodigoPostal(codigoPostal);
        direccion.setUsuario(usuarioOptional.get());

        // Guardar la dirección en la base de datos
        Direccion direccionRegistrada = serviceDireccion.save(direccion);

        redirectAttributes.addFlashAttribute("mensaje", "Dirección registrada con éxito");
        return "redirect:/direccion/findById/" + direccionRegistrada.getId();
    }

    /*
     * @PostMapping("/registro")
     * public String registrarDireccion(@ModelAttribute Direccion direccion,
     * 
     * @RequestParam("codigoPostal") Integer codigoPostalId,
     * Model model) {
     * // Validar que la dirección tenga los datos requeridos
     * if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty() ||
     * direccion.getNumero() == null || direccion.getNumero().trim().isEmpty()) {
     * model.addAttribute("error", "La calle y el número son obligatorios");
     * model.addAttribute("codigosPostales", serviceCodigoPostal.findAll());
     * return "RegistrarDireccion";
     * }
     * 
     * // Verificar que el código postal no esté ya asociado a otra dirección
     * List<Direccion> todasDirecciones = serviceDireccion.findAll();
     * boolean codigoPostalEnUso = todasDirecciones.stream()
     * .anyMatch(d -> d.getCodigoPostal().getId() == codigoPostalId);
     * 
     * if (codigoPostalEnUso) {
     * model.addAttribute("error",
     * "Este código postal ya está asociado a otra dirección.");
     * model.addAttribute("codigosPostales", serviceCodigoPostal.findAll());
     * return "RegistrarDireccion";
     * }
     * 
     * // Buscar el código postal seleccionado y asignarlo a la dirección
     * Optional<CodigoPostal> codigoPostal =
     * serviceCodigoPostal.findById(codigoPostalId);
     * if (codigoPostal.isEmpty()) {
     * model.addAttribute("error", "Código postal no válido");
     * model.addAttribute("codigosPostales", serviceCodigoPostal.findAll());
     * return "RegistrarDireccion";
     * }
     * 
     * direccion.setCodigoPostal(codigoPostal.get());
     * 
     * // Guardar la dirección en la base de datos
     * Direccion direccionRegistrada = serviceDireccion.save(direccion);
     * 
     * // Redirigir a una página de éxito o a la vista de la dirección registrada
     * model.addAttribute("mensaje", "Dirección registrada con éxito");
     * return "redirect:/direccion/findById/" + direccionRegistrada.getId();
     * }
     */

    // Método para encontrar una dirección por ID y mostrar su información
    @GetMapping("/findById/{id}")
    public String verDireccion(@PathVariable Long id, Model model) {
        Optional<Direccion> direccion = serviceDireccion.findById(id);
        if (direccion.isPresent()) {
            model.addAttribute("direccion", direccion.get());
            return "verDireccion";
        } else {
            model.addAttribute("error", "Dirección no encontrada");
            return "error";
        }
    }

    @GetMapping("/findAll")
    public String listarDirecciones(Model model) {
        List<Direccion> direcciones = serviceDireccion.findAll();
        model.addAttribute("direcciones", direcciones);
        return "listaDirecciones"; // Asegúrate de tener un archivo Thymeleaf llamado listaDirecciones.html
    }

}
