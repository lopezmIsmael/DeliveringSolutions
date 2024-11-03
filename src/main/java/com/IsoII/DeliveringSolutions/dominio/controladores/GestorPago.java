package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Cliente;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemPedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceRestaurant;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceUser;
import com.IsoII.DeliveringSolutions.persistencia.PagoDAO;
import com.IsoII.DeliveringSolutions.persistencia.ItemMenuDAO;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceItemPedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePago;
import com.IsoII.DeliveringSolutions.persistencia.DAOItemsPedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceItemMenu;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/pago")
public class GestorPago {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private PagoDAO pagoDAO;

    @Autowired
    private ServiceRestaurant serviceRestaurant;

    @Autowired
    private ServicePedido servicePedido;

    @Autowired
    private ServiceItemMenu serviceItemMenu;

    @Autowired
    private ServiceItemPedido serviceItemPedido;

    @Autowired
    private ServiceDireccion serviceDireccion;

    @Autowired
    private ServiceUser serviceUsuario;

    @Autowired
    private ServicePago servicePago;

    @GetMapping("/findAll")
    @ResponseBody
    public List<Pago> findAll() {
        return pagoDAO.findAll();
    }

    // In GestorPago.java, update the mapping annotation
    // In GestorPago.java
    @PostMapping("/register")
    public String mostrarFormularioRegistro(@RequestParam("cartData") String cartData,
            @RequestParam("restauranteId") String restauranteId, Model model, HttpSession session) {
        System.out.println("<<ESTOY EN REGISTER: GestorPago>>");
        System.out.println("<<RestauranteId>>: " + restauranteId);
        System.out.println("<<CartData>>: " + cartData);

        // Find direccion by usuario
        Restaurante restaurante = new Restaurante();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (serviceDireccion.findByUsuario(usuario) == null) {
            return "redirect:/direccion/formularioRegistro";
        }

        // Find restaurante by id
        restaurante = serviceRestaurant.findById(restauranteId).orElse(null);

        System.out.println("<<Restaurante>>: " + restaurante.getNombre());
        // Create ObjectMapper and configure it
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<ItemMenu> carrito = new ArrayList<>();

        try {
            carrito = objectMapper.readValue(cartData, new TypeReference<List<ItemMenu>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("<<Carrito size>>: " + carrito.size());
        for (ItemMenu item : carrito) {
            System.out.println("<<Item>>: " + item.getNombre() + ", Precio: " + item.getPrecio());
        }

        double totalPrice = 0;
        for (ItemMenu item : carrito) {
            totalPrice += item.getPrecio();
        }
        System.out.println("<<Total Price>>: " + totalPrice);

        model.addAttribute("restaurante", restaurante);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", totalPrice);

        return "RegistrarPedidos";
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public Pago findById(@PathVariable Integer id) {
        return pagoDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarPedido")
    public String registrarPedido(
            @RequestParam("metodoPago") String metodoPago,
            @RequestParam("restauranteId") String restauranteId,
            HttpSession session,
            @RequestParam("itemIds") List<Integer> itemIds,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Logging
        System.out.println("<<ESTOY EN REGISTRAR PEDIDO: GestorPago>>");
        System.out.println("<<Metodo de pago>>: " + metodoPago);
        System.out.println("<<RestauranteId>>: " + restauranteId);

        // Retrieve cliente and restaurante
        Cliente cliente = (Cliente) session.getAttribute("usuario");
        Restaurante restaurante = serviceRestaurant.findById(restauranteId).orElse(null);

        // Create and save Pedido
        Pedido pedido = new Pedido();
        pedido.setFecha(System.currentTimeMillis());
        pedido.setEstadoPedido("Pendiente");
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);

        // Registrar pedido
        servicePedido.save(pedido);
        System.out.println("<<Pedido registrado>>: " + pedido.toString());
        Double total = 0.0;
        // Fetch items from database based on IDs
        List<ItemMenu> items = new ArrayList<>();
        for (Integer itemId : itemIds) {
            System.out.println("<<Item ID>>: " + itemId);
            // Assuming you have a service or repository to fetch items
            Optional<ItemMenu> optionalItem = serviceItemMenu.findById(itemId);
            if (optionalItem.isPresent()) {
                ItemPedido itemPedido = new ItemPedido();
                items.add(optionalItem.get());
                total += optionalItem.get().getPrecio();
                System.out.println("<<Item encontrado>>: " + optionalItem.toString());
                itemPedido.setItemMenu(optionalItem.get());
                itemPedido.setPedido(pedido);
                serviceItemPedido.save(itemPedido);
                System.out.println("<<ItemPedido registrado>>: " + itemPedido.toString());
            }
        }

        // Registrar pago
        Pago pago = new Pago();
        pago.setMetodoPago(metodoPago);
        pago.setPedido(pedido);
        pagoDAO.save(pago);

        System.out.println("<<Pago registrado>>: " + pago.toString());

        // Actualizar estado pedido a pagado
        pedido.setEstadoPedido("Pagado");
        servicePedido.save(pedido);

        // Obtener direccion recogida
        Usuario usuarioRestaurante = serviceUsuario.findById(restaurante.getIdUsuario()).orElse(null);
        Direccion direccionRecogida = serviceDireccion.findByUsuario(usuarioRestaurante);

        // Obtener direccion entrega
        Usuario usuarioCliente = serviceUsuario.findById(cliente.getIdUsuario()).orElse(null);
        Direccion direccionEntrega = serviceDireccion.findByUsuario(usuarioCliente);

        // Add attributes to redirectAttributes instead of model
        redirectAttributes.addFlashAttribute("pedido", pedido);
        redirectAttributes.addFlashAttribute("items", items);
        redirectAttributes.addFlashAttribute("pago", pago);
        redirectAttributes.addFlashAttribute("restaurante", restaurante);
        redirectAttributes.addFlashAttribute("cliente", cliente);
        redirectAttributes.addFlashAttribute("total", total);
        redirectAttributes.addFlashAttribute("direccionRecogida", direccionRecogida);
        redirectAttributes.addFlashAttribute("direccionEntrega", direccionEntrega);

        System.out.println("<<MODELO:>>" + pedido.toString() + items.toString() + pago.toString()
                + restaurante.toString() + cliente.toString() + total + direccionRecogida.toString()
                + direccionEntrega.toString());
        return "redirect:/pago/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(Model model) {
        return "ConfirmacionPedido";
    }

    // Método para listar todos los pagos
    @GetMapping("/mostrarPagos")
    public String mostrarPagos(Model model) {
        List<Pago> pagos = servicePago.findAll();
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
        Optional<Pago> optionalPago = servicePago.findById(id);
        if (optionalPago.isPresent()) {
            model.addAttribute("pago", optionalPago.get());
            return "/administrador/VerPago";
        } else {
            model.addAttribute("error", "Pago no encontrado");
            return "error";
        }
    }

}