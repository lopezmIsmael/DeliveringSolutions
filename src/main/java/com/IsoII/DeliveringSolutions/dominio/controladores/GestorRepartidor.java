package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.Zona;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceRepartidor;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceServicioEntrega;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Repartidor;
import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import com.IsoII.DeliveringSolutions.persistencia.RepartidorDAO;
import com.IsoII.DeliveringSolutions.persistencia.ZonaDAO;

import jakarta.servlet.http.HttpSession;

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

    @Autowired
    private ServiceRepartidor serviceRepartidor;

    @Autowired
    private ServiceServicioEntrega serviceServicioEntrega;

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

    @GetMapping("/gestionar/{id}")
    public String gestionarPedido(@PathVariable Integer id, Model model) {
        Pedido pedido = servicePedido.findById(id).orElse(null);
        Direccion direccion = serviceDireccion.findByUsuario(pedido.getCliente());
        model.addAttribute("pedido", pedido);
        model.addAttribute("direccion", direccion);
        return "GestionPedido"; // Ensure this matches your template name
    }

    @GetMapping("/calcularTiempos/{id}")
    public String calcularTiempos(@PathVariable Integer id, Model model, HttpSession session) {
        Pedido pedido = servicePedido.findById(id).orElse(null);
        List<ServicioEntrega> serviciosEntrega = serviceServicioEntrega.findAll();
        Repartidor repartidor = (Repartidor) session.getAttribute("usuario");
        ServicioEntrega servicioEntrega = null;
        for (ServicioEntrega s : serviciosEntrega) {
            if (s.getPedido().getIdPedido() == id) {
                servicioEntrega = s;

                break;
            }
        }
        if (servicioEntrega == null) {
            model.addAttribute("error", "No se ha encontrado el servicio de entrega.");
            return "CalcularTiempos";
        }

        long tiempoRecepcion = servicioEntrega.getFechaRecepcion();
        long tiempoEntrega = servicioEntrega.getFechaEntrega();
        long tiempoTotal = tiempoEntrega - tiempoRecepcion;

        // Calcular eficiencia repartidor
        // Si tiempoTotal < 30 minutos, eficiencia = 5 estrellas
        // Si tiempoTotal < 1 hora, eficiencia = 4 estrellas
        // Si tiempoTotal < 1:15 horas, eficiencia = 3 estrellas
        // Si tiempoTotal < 1:30 horas, eficiencia = 2 estrellas
        // Si tiempoTotal > 1:30 horas, eficiencia = 1 estrella
        int eficiencia = 0;

        // Convierte tiempoTotal de milisegundos a horas, minutos y segundos
        long segundos = (tiempoTotal / 1000) % 60;
        long minutos = (tiempoTotal / (1000 * 60)) % 60;
        long horas = (tiempoTotal / (1000 * 60 * 60)) % 24;

        // Crea una cadena formateada
        String tiempoTotalFormatted = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        if (tiempoTotal < 30 * 60 * 1000) {
            eficiencia = 5;
        } else if (tiempoTotal < 60 * 60 * 1000) {
            eficiencia = 4;
        } else if (tiempoTotal < 75 * 60 * 1000) {
            eficiencia = 3;
        } else if (tiempoTotal < 90 * 60 * 1000) {
            eficiencia = 2;
        } else {
            eficiencia = 1;
        }

        repartidor.setEficiencia(eficiencia);
        serviceRepartidor.save(repartidor);

        model.addAttribute("tiempoTotal", tiempoTotal);
        model.addAttribute("tiempoTotalFormatted", tiempoTotalFormatted);
        model.addAttribute("pedido", pedido);
        model.addAttribute("repartidor", repartidor);
        model.addAttribute("servicioEntrega", servicioEntrega);
        return "CalcularTiempos"; // Ensure this matches your template name
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

    // Método para actualizar el estado de un pedido
    @PostMapping("/actualizarEstado/{id}")
    public String actualizarEstadoPedido(@PathVariable Integer id, @RequestParam("estado") String nuevoEstado,
            RedirectAttributes redirectAttributes, HttpSession session) {
        Pedido pedido = servicePedido.findById(id).orElse(null);
        if (pedido == null) {
            redirectAttributes.addFlashAttribute("error", "El pedido no existe.");
            return "redirect:/repartidores/gestionar/" + id;
        }

        // Actualizar el estado del pedido
        pedido.setEstadoPedido(nuevoEstado);
        servicePedido.save(pedido);
        ServicioEntrega servicioEntrega = new ServicioEntrega();

        if (pedido.getEstadoPedido().equals("Entregado")) {
            servicioEntrega.setFechaEntrega(System.currentTimeMillis());
            servicioEntrega.setFechaRecepcion(pedido.getFecha());
            servicioEntrega.setPedido(pedido);
            servicioEntrega.setRepartidor((Repartidor) session.getAttribute("usuario"));

            // Guardar el servicio de entrega
            serviceServicioEntrega.save(servicioEntrega);

            // Redirige a una nueva pagina que calcula los tiempos
            return "redirect:/repartidores/calcularTiempos/" + id;
        }

        redirectAttributes.addFlashAttribute("success", "Estado del pedido actualizado correctamente.");
        return "redirect:/repartidores/gestionar/" + id; // Redirige de nuevo a la página de gestión
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
