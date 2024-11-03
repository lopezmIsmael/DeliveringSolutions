package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
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
    private ServicePedido servicePedido;

    @Autowired
    private ServiceDireccion serviceDireccion;

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

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        List<Pedido> pedidos = servicePedido.findAll();
        Map<Pedido, Direccion> pedidosPendientes = new LinkedHashMap<>(); // Preserve order

        for (Pedido pedido : pedidos) {
            if ("Pagado".equals(pedido.getEstadoPedido())) {
                Direccion direccion = serviceDireccion.findByUsuario(pedido.getCliente());
                pedidosPendientes.put(pedido, direccion);
            }
        }

        model.addAttribute("pedidosPendientes", pedidosPendientes);
        return "GestorRepartidor"; // Ensure this matches your template name
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
}
