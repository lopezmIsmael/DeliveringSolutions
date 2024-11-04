package com.IsoII.DeliveringSolutions.dominio.controladores;

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
import com.IsoII.DeliveringSolutions.dominio.service.ServiceZona;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Repartidor;
import com.IsoII.DeliveringSolutions.dominio.entidades.ServicioEntrega;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/repartidores")
public class GestorRepartidor {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ServicePedido servicePedido;

    @Autowired
    private ServiceDireccion serviceDireccion;

    @Autowired
    private ServiceRepartidor serviceRepartidor;

    @Autowired
    private ServiceServicioEntrega serviceServicioEntrega;

    @Autowired
    private ServiceZona serviceZona;

    // Método que devuelve una lista de todos los repartidores
    @GetMapping("/findAll")
    @ResponseBody
    public List<Repartidor> findAll() {
        return serviceRepartidor.findAll();
    }

    // Método que muestra el formulario de registro de repartidor
    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        List<Zona> zonas = serviceZona.findAll();
        model.addAttribute("zonas", zonas);
        return "Pruebas-RegisterRepartidor"; 
    }

    // Método que busca un solo repartidor por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Repartidor findById(@PathVariable String id) {
        return serviceRepartidor.findById(id).orElse(null);
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        List<Pedido> pedidos = servicePedido.findAll();
        Map<Pedido, Direccion> pedidosPendientes = new LinkedHashMap<>(); 

        for (Pedido pedido : pedidos) {
            if ("Pagado".equals(pedido.getEstadoPedido())) {
                Direccion direccion = serviceDireccion.findByUsuario(pedido.getCliente());
                pedidosPendientes.put(pedido, direccion);
            }
        }

        model.addAttribute("pedidosPendientes", pedidosPendientes);
        return "GestorRepartidor"; 
    }

    @GetMapping("/gestionar/{id}")
    public String gestionarPedido(@PathVariable Integer id, Model model) {
        Pedido pedido = servicePedido.findById(id).orElse(null);
        Direccion direccion = serviceDireccion.findByUsuario(pedido.getCliente());
        model.addAttribute("pedido", pedido);
        model.addAttribute("direccion", direccion);
        return "GestionPedido"; 
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
        int eficiencia = 0;

        long segundos = (tiempoTotal / 1000) % 60;
        long minutos = (tiempoTotal / (1000 * 60)) % 60;
        long horas = (tiempoTotal / (1000 * 60 * 60)) % 24;

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
        return "CalcularTiempos";
    }

    // Método que registra un repartidor
    @PostMapping("/registrarRepartidor")
    public String registrarRepartidor(@ModelAttribute Repartidor repartidor, RedirectAttributes redirectAttributes) {
        System.out.println("Repartidor recibido: " + repartidor.toString());
        if (repartidor.getPass() == null || repartidor.getPass().isEmpty() || repartidor.getDni().length() != 9
                || repartidor.getPass().length() < 6) {
            redirectAttributes.addFlashAttribute("error",
                    "La contraseña no puede estar vacía, el DNI debe tener 9 caracteres y la contraseña debe tener al menos 6 caracteres.");
            return "redirect:/repartidores/register";
        }

        Repartidor repartidorRegistrado = serviceRepartidor.save(repartidor);
        System.out.println("Repartidor registrado: " + repartidorRegistrado);
        redirectAttributes.addFlashAttribute("success", "Repartidor registrado correctamente");
        return "redirect:/"; 
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

        pedido.setEstadoPedido(nuevoEstado);
        servicePedido.save(pedido);
        ServicioEntrega servicioEntrega = new ServicioEntrega();

        if (pedido.getEstadoPedido().equals("Entregado")) {
            servicioEntrega.setFechaEntrega(System.currentTimeMillis());
            servicioEntrega.setFechaRecepcion(pedido.getFecha());
            servicioEntrega.setPedido(pedido);
            servicioEntrega.setRepartidor((Repartidor) session.getAttribute("usuario"));

            serviceServicioEntrega.save(servicioEntrega);

            return "redirect:/repartidores/calcularTiempos/" + id;
        }

        redirectAttributes.addFlashAttribute("success", "Estado del pedido actualizado correctamente.");
        return "redirect:/repartidores/gestionar/" + id; 
    }

    // Método que devuelve una lista de todos los repartidores
    @GetMapping("/mostrarRepartidores")
    public String mostrarRepartidores(Model model) {
        List<Repartidor> repartidores = serviceRepartidor.findAll();
        if (repartidores != null && !repartidores.isEmpty()) {
            model.addAttribute("repartidores", repartidores);
            return "/administrador/ListaRepartidores"; 
        } else {
            model.addAttribute("error", "No se encontraron repartidores");
            return "error"; 
        }
    }

    // Método que muestra los detalles de un repartidor
    @GetMapping("/mostrarRepartidor/{id}")
    public String mostrarRepartidor(@PathVariable String id, Model model) {
        Optional<Repartidor> optionalRepartidor = serviceRepartidor.findById(id);
        if (optionalRepartidor.isPresent()) {
            Repartidor repartidor = optionalRepartidor.get();
            model.addAttribute("repartidor", repartidor);
            return "/administrador/VerRepartidor"; 
        } else {
            model.addAttribute("error", "Repartidor no encontrado");
            return "error";
        }
    }

}
