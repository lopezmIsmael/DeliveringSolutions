package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemPedido;
import com.isoii.deliveringsolutions.dominio.entidades.Pago;
import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.dominio.service.ServiceGroup;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controlador para gestionar los pagos
@RestController
@RequestMapping("/pago")
public class GestorPago {
    private final ServiceGroup serviceGroup;
    private static final Logger logger = LoggerFactory.getLogger(GestorPago.class);
    private static final String USUARIO = "usuario";

    @Autowired
    public GestorPago(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    // Método para listar todos los pagos
    @GetMapping("/findAll")
    public List<Pago> findAll() {
        return serviceGroup.getServicePago().findAll();
    }

    // Método para registrar un pago
    @PostMapping("/register")
    public String mostrarFormularioRegistro(@RequestParam("cartData") String cartData,
            @RequestParam("restauranteId") String restauranteId, Model model, HttpSession session) {
        logger.info("<<RestauranteId>>: " + restauranteId);

        Usuario usuario = (Usuario) session.getAttribute(USUARIO);

        if (usuario == null) {
            return "redirect:/usuarios/login";
        }

        if (serviceGroup.getServiceDireccion().findByUsuario(usuario) == null) {
            return "redirect:/direccion/formularioRegistro";
        }

        Restaurante restaurante = serviceGroup.getServiceRestaurant().findById(restauranteId).orElse(null);
        if (restaurante == null) {
            return "redirect:/error"; // or any appropriate error handling
        }

        logger.info("<<Restaurante>>: " + restaurante.getNombre());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<ItemMenu> carrito = new ArrayList<>();

        try {
            carrito = objectMapper.readValue(cartData, new TypeReference<List<ItemMenu>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("<<Carrito size>>: " + carrito.size());
        for (ItemMenu item : carrito) {
            logger.info("<<Item>>: " + item.getNombre() + ", Precio: " + item.getPrecio());
        }

        double totalPrice = 0;
        for (ItemMenu item : carrito) {
            totalPrice += item.getPrecio();
        }
        logger.info("<<Total Price>>: " + totalPrice);

        List<Direccion> direcciones = serviceGroup.getServiceDireccion().findByUsuario(usuario);
        List<CodigoPostal> codigosPostales = serviceGroup.getServiceCodigoPostal().findAll();

        model.addAttribute("direcciones", direcciones);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", totalPrice);
        model.addAttribute("usuario", usuario);
        model.addAttribute("codigosPostales", codigosPostales);

        return "RegistrarPedidos";
    }

    // Metodo para buscar un pago por ID
    @GetMapping("/findById/{id}")
    public Pago findById(@PathVariable Integer id) {
        return serviceGroup.getServicePago().findById(id).orElse(null);
    }

    // Metodo para registrar un pedido
    @PostMapping("/registrarPedido")
    public String registrarPedido(
            @RequestParam("metodoPago") String metodoPago,
            @RequestParam("restauranteId") String restauranteId,
            @RequestParam("direccion") Long direccion,
            HttpSession session,
            @RequestParam("itemIds") List<Integer> itemIds,
            Model model,
            RedirectAttributes redirectAttributes) {

        logger.info("<<ESTOY EN REGISTRAR PEDIDO: GestorPago>>");
        logger.info("<<Metodo de pago>>: " + metodoPago);
        logger.info("<<RestauranteId>>: " + restauranteId);
        logger.info("<<DireccionId>>: " + direccion);

        Cliente cliente = (Cliente) session.getAttribute(USUARIO);
        Restaurante restaurante = serviceGroup.getServiceRestaurant().findById(restauranteId).orElse(null);

        Pedido pedido = new Pedido();
        pedido.setFecha(System.currentTimeMillis());
        pedido.setEstadoPedido("Pendiente");
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);

        serviceGroup.getServicePedido().save(pedido);
        logger.info("<<Pedido registrado>>: " + pedido.toString());
        Double total = 0.0;
        List<ItemMenu> items = new ArrayList<>();
        for (Integer itemId : itemIds) {
            logger.info("<<Item ID>>: " + itemId);
            Optional<ItemMenu> optionalItem = serviceGroup.getServiceItemMenu().findById(itemId);
            if (optionalItem.isPresent()) {
                ItemPedido itemPedido = new ItemPedido();
                items.add(optionalItem.get());
                total += optionalItem.get().getPrecio();
                logger.info("<<Item encontrado>>: " + optionalItem.toString());
                itemPedido.setItemMenu(optionalItem.get());
                itemPedido.setPedido(pedido);
                serviceGroup.getServiceItemPedido().save(itemPedido);
                logger.info("<<ItemPedido registrado>>: " + itemPedido.toString());
            }
        }

        Pago pago = new Pago();
        pago.setMetodoPago(metodoPago);
        pago.setPedido(pedido);
        serviceGroup.getServicePago().save(pago);

        logger.info("<<Pago registrado>>: " + pago.toString());
        if (restaurante != null) {
            pedido.setEstadoPedido("Pagado");
            serviceGroup.getServicePedido().save(pedido);

            Usuario usuarioRestaurante = serviceGroup.getServiceUsuario().findById(restaurante.getIdUsuario()).orElse(null);
            List<Direccion> direccionesRecogida = serviceGroup.getServiceDireccion().findByUsuario(usuarioRestaurante);
            Direccion direccionRecogida = !direccionesRecogida.isEmpty() ? direccionesRecogida.get(0) : null;

            if (direccionRecogida != null) {
                logger.info("<<Direccion de recogida>>: " + direccionRecogida.toString());
            } else {
                logger.info("<<Direccion de recogida no encontrada>>");
            }

            Direccion direccionEntrega = serviceGroup.getServiceDireccion().findById(direccion).orElse(null);

            redirectAttributes.addFlashAttribute("pedido", pedido);
            redirectAttributes.addFlashAttribute("items", items);
            redirectAttributes.addFlashAttribute("pago", pago);
            redirectAttributes.addFlashAttribute("restaurante", restaurante);
            redirectAttributes.addFlashAttribute("cliente", cliente);
            redirectAttributes.addFlashAttribute("total", total);
            redirectAttributes.addFlashAttribute("direccionRecogida", direccionRecogida);
            redirectAttributes.addFlashAttribute("direccionEntrega", direccionEntrega);
        }

        return "redirect:/pago/confirmacion";
    }

    // Método para mostrar el formulario de registro de un pago
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(Model model) {
        return "ConfirmacionPedido";
    }

    // Método para listar todos los pagos
    @GetMapping("/mostrarPagos")
    public String mostrarPagos(Model model) {
        List<Pago> pagos = serviceGroup.getServicePago().findAll();
        if (!pagos.isEmpty()) {
            model.addAttribute("pagos", pagos);
            return "/administrador/ListaPagos";
        } else {
            model.addAttribute("error", "No se encontraron pagos");
            return "error";
        }
    }

    // Método para mostrar los detalles de un pago específico
    @GetMapping("/mostrarPago/{id}")
    public String mostrarPago(@PathVariable Integer id, Model model) {
        Optional<Pago> optionalPago = serviceGroup.getServicePago().findById(id);
        if (optionalPago.isPresent()) {
            model.addAttribute("pago", optionalPago.get());
            return "/administrador/VerPago";
        } else {
            model.addAttribute("error", "Pago no encontrado");
            return "error";
        }
    }

}