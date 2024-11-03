package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Cliente;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemPedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceRestaurant;
import com.IsoII.DeliveringSolutions.persistencia.PagoDAO;
import com.IsoII.DeliveringSolutions.persistencia.ItemMenuDAO;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceItemPedido;
import com.IsoII.DeliveringSolutions.persistencia.ItemPedidoDAO;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceItemMenu;
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

    @GetMapping("/findAll")
    @ResponseBody
    public List<Pago> findAll() {
        return pagoDAO.findAll();
    }

    // In GestorPago.java, update the mapping annotation
    // In GestorPago.java
    @PostMapping("/register")
    public String mostrarFormularioRegistro(@RequestParam("cartData") String cartData,
            @RequestParam("restauranteId") String restauranteId, Model model) {
        System.out.println("<<ESTOY EN REGISTER: GestorPago>>");
        System.out.println("<<RestauranteId>>: " + restauranteId);
        System.out.println("<<CartData>>: " + cartData);

        // Find restaurante by id
        Restaurante restaurante = new Restaurante();

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

        // Fetch items from database based on IDs
        List<ItemMenu> items = new ArrayList<>();
        for (Integer itemId : itemIds) {
            System.out.println("<<Item ID>>: " + itemId);
            // Assuming you have a service or repository to fetch items
            Optional<ItemMenu> optionalItem = serviceItemMenu.findById(itemId);
            if (optionalItem.isPresent()) {
                ItemPedido itemsPedidos = new ItemPedido();
                items.add(optionalItem.get());
                System.out.println("<<Item encontrado>>: " + optionalItem.toString());
                itemsPedidos.setItemMenu(optionalItem.get());
                itemsPedidos.setPedido(pedido);
                serviceItemPedido.save(itemsPedidos);
                System.out.println("<<ItemPedido registrado>>: " + itemsPedidos.toString());
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

        // Redirect with Flash Attributes
        redirectAttributes.addFlashAttribute("pedido", pedido);

        return "redirect:/pago/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String mostrarConfirmacion() {
        return "ConfirmacionPedido";
    }
}