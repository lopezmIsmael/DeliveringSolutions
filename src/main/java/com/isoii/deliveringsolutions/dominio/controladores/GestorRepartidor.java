package com.isoii.deliveringsolutions.dominio.controladores;

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

import com.isoii.deliveringsolutions.dominio.entidades.Zona;
import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceDireccion;
import com.isoii.deliveringsolutions.dominio.service.ServicePedido;
import com.isoii.deliveringsolutions.dominio.service.ServiceRepartidor;
import com.isoii.deliveringsolutions.dominio.service.ServiceServicioEntrega;
import com.isoii.deliveringsolutions.dominio.service.ServiceZona;
import com.isoii.deliveringsolutions.dominio.service.ServiceZonaCodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Repartidor;
import com.isoii.deliveringsolutions.dominio.entidades.ServicioEntrega;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/repartidores")
public class GestorRepartidor {
    private final ServicePedido servicePedido;
    private final ServiceDireccion serviceDireccion;
    private final ServiceRepartidor serviceRepartidor;
    private final ServiceServicioEntrega serviceServicioEntrega;
    private final ServiceZona serviceZona;
    private final ServiceZonaCodigoPostal serviceZonaCodigoPostal;

    private static final String USUARIO = "usuario";
    private static final String ERROR = "error";

    private static final Logger logger = LoggerFactory.getLogger(GestorRepartidor.class);

    @Autowired
    public GestorRepartidor(ServicePedido servicePedido, ServiceDireccion serviceDireccion, 
                            ServiceRepartidor serviceRepartidor, ServiceServicioEntrega serviceServicioEntrega, 
                            ServiceZona serviceZona, ServiceZonaCodigoPostal serviceZonaCodigoPostal) {
        this.servicePedido = servicePedido;
        this.serviceDireccion = serviceDireccion;
        this.serviceRepartidor = serviceRepartidor;
        this.serviceServicioEntrega = serviceServicioEntrega;
        this.serviceZona = serviceZona;
        this.serviceZonaCodigoPostal = serviceZonaCodigoPostal;
    }

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    // Método que devuelve una lista de todos los repartidores
    @GetMapping("/findAll")
    
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
    
    public Repartidor findById(@PathVariable String id) {
        return serviceRepartidor.findById(id).orElse(null);
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model, HttpSession session) {
        Repartidor repartidor = (Repartidor) session.getAttribute(USUARIO);
        if (repartidor == null) {
            model.addAttribute(ERROR, "Debe iniciar sesión primero.");
            return ERROR;
        }

        List<ZonaCodigoPostal> zonaCodigosPostales = serviceZonaCodigoPostal.findAll();
        List<CodigoPostal> codigosPostales = new java.util.ArrayList<>();

        for (ZonaCodigoPostal zcp : zonaCodigosPostales) {
            if (repartidor.getZona().getId() == zcp.getZona().getId()) {
                codigosPostales.add(zcp.getCodigoPostal());
            }
        }

        List<Pedido> pedidos = servicePedido.findAll();
        Map<Pedido, Direccion> pedidosPendientes = new LinkedHashMap<>();

        for (Pedido pedido : pedidos) {
            if ("Pagado".equals(pedido.getEstadoPedido())) {
                for (CodigoPostal cp : codigosPostales) {
                    Direccion direccion = serviceDireccion.findByUsuario(pedido.getCliente()).get(0);
                    if (direccion.getCodigoPostal().equals(cp)) {
                        pedidosPendientes.put(pedido, direccion);
                    }
                }
            }
        }

        model.addAttribute("pedidosPendientes", pedidosPendientes);
        return "GestorRepartidor";
    }

    @GetMapping("/gestionar/{id}")
    public String gestionarPedido(@PathVariable Integer id, Model model) {
        Pedido pedido = servicePedido.findById(id).orElse(null);

        if (pedido == null) {
            model.addAttribute(ERROR, "Pedido no encontrado.");
            return ERROR;
        }

        List<Direccion> direcciones = serviceDireccion.findByUsuario(pedido.getCliente());

        Direccion direccion = direcciones.isEmpty() ? null : direcciones.get(0); // Seleccionar la primera dirección si
                                                                                 // existe

        model.addAttribute("pedido", pedido);
        model.addAttribute("direccion", direccion);
        return "GestionPedido";
    }

    @GetMapping("/calcularTiempos/{id}")
    public String calcularTiempos(@PathVariable Integer id, Model model, HttpSession session) {
        Pedido pedido = servicePedido.findById(id).orElse(null);
        List<ServicioEntrega> serviciosEntrega = serviceServicioEntrega.findAll();
        Repartidor repartidor = (Repartidor) session.getAttribute(USUARIO);
        ServicioEntrega servicioEntrega = null;
        for (ServicioEntrega s : serviciosEntrega) {
            if (s.getPedido().getIdPedido() == id) {
                servicioEntrega = s;

                break;
            }
        }
        if (servicioEntrega == null) {
            model.addAttribute(ERROR, "No se ha encontrado el servicio de entrega.");
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
        logger.info("Repartidor recibido: {}", repartidor);
        if (repartidor.getPass() == null || repartidor.getPass().isEmpty() || repartidor.getDni().length() != 9
                || repartidor.getPass().length() < 6) {
            redirectAttributes.addFlashAttribute(ERROR,
                    "La contraseña no puede estar vacía, el DNI debe tener 9 caracteres y la contraseña debe tener al menos 6 caracteres.");
            return "redirect:/repartidores/register";
        }

        Repartidor repartidorRegistrado = serviceRepartidor.save(repartidor);
        logger.info("Repartidor registrado: {}", repartidorRegistrado);
        redirectAttributes.addFlashAttribute("success", "Repartidor registrado correctamente");
        return "redirect:/";
    }

    // Método para actualizar el estado de un pedido
    @PostMapping("/actualizarEstado/{id}")
    public String actualizarEstadoPedido(@PathVariable Integer id, @RequestParam("estado") String nuevoEstado,
            RedirectAttributes redirectAttributes, HttpSession session) {
        Pedido pedido = servicePedido.findById(id).orElse(null);
        if (pedido == null) {
            redirectAttributes.addFlashAttribute(ERROR, "El pedido no existe.");
            return "redirect:/repartidores/gestionar/" + id;
        }

        pedido.setEstadoPedido(nuevoEstado);
        servicePedido.save(pedido);
        ServicioEntrega servicioEntrega = new ServicioEntrega();

        if (pedido.getEstadoPedido().equals("Entregado")) {
            servicioEntrega.setFechaEntrega(System.currentTimeMillis());
            servicioEntrega.setFechaRecepcion(pedido.getFecha());
            servicioEntrega.setPedido(pedido);
            servicioEntrega.setRepartidor((Repartidor) session.getAttribute(USUARIO));

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
            model.addAttribute(ERROR, "No se encontraron repartidores");
            return ERROR;
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
            model.addAttribute(ERROR, "Repartidor no encontrado");
            return ERROR;
        }
    }

}
