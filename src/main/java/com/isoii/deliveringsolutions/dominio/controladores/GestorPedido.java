package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.service.ServiceItemPedido;
import com.isoii.deliveringsolutions.dominio.service.ServicePedido;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemPedido;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controlador para gestionar los pedidos
@RestController
@RequestMapping("/pedido")
public class GestorPedido {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private List<ItemMenu> carrito = new ArrayList<>();

    private final ServicePedido servicePedido;
    private final ServiceItemPedido serviceItemPedido;

    private static final Logger logger = LoggerFactory.getLogger(GestorPedido.class);

    @Autowired
    public GestorPedido(ServicePedido servicePedido, ServiceItemPedido serviceItemPedido) {
        this.servicePedido = servicePedido;
        this.serviceItemPedido = serviceItemPedido;
    }

    // Método para mostrar todos los pedidos
    @GetMapping("/findAll")
    public List<Pedido> findAll() {
        return servicePedido.findAll();
    }

    // Método para mostrar el formulario de registro de pedido
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterPedido";
    }

    // Método para buscar un pedido por su id
    @GetMapping("/findById/{id}")
    public Pedido findById(@PathVariable Integer id) {
        return servicePedido.findById(id).orElse(null);
    }

    // Método para registrar un pedido
    @PostMapping("/registrarPedido")
    public ResponseEntity<Pedido> registrarPedido(@ModelAttribute Pedido pedido) {
        logger.info("Pedido recibido: " + pedido.toString());
        if (pedido.getFecha() == 0 || pedido.getEstadoPedido() == null || pedido.getEstadoPedido().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pedido pedidoRegistrado = servicePedido.save(pedido);
        logger.info("Pedido registrado: " + pedidoRegistrado);
        return new ResponseEntity<>(pedidoRegistrado, HttpStatus.CREATED);
    }

    // Método para añaadir un item al carrito
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@ModelAttribute ItemMenu item) {
        carrito.add(item);
        // Aquí puedes agregar la lógica para añadir el item al carrito
        return new ResponseEntity<>("Item añadido al carrito", HttpStatus.OK);
    }

    // Método para mostrar el carrito
    @RequestMapping("/carrito")
    public String mostrarCarrito(Model model) {
        model.addAttribute("carrito", carrito);
        return "verMenusRestaurante"; 
    }

    // Método para listar todos los pedidos
    @GetMapping("/mostrarPedidos")
    public String mostrarPedidos(Model model) {
        List<Pedido> pedidos = servicePedido.findAll();
        if (pedidos != null && !pedidos.isEmpty()) {
            model.addAttribute("pedidos", pedidos);
            return "/administrador/ListaPedidos";
        } else {
            model.addAttribute("error", "No se encontraron pedidos");
            return "error";
        }
    }

    // Método para ver detalles de un pedido específico
    @GetMapping("/mostrarPedido/{id}")
    public String mostrarPedido(@PathVariable Integer id, Model model) {
        Optional<Pedido> optionalPedido = servicePedido.findById(id);
        List<ItemPedido> itemsPedidos = serviceItemPedido.findAll();
        List<ItemPedido> itemsPedido = new ArrayList<>();
        if (optionalPedido.isPresent()) {

            Pedido pedido = optionalPedido.get();
            for (ItemPedido itemPedido : itemsPedidos) {

                if (itemPedido.getPedido().getIdPedido() == pedido.getIdPedido()) {
                    itemsPedido.add(itemPedido);
                }

            }

            model.addAttribute("pedido", pedido);
            model.addAttribute("itemsPedido", itemsPedido);

            return "/administrador/VerPedido";
        } else {
            model.addAttribute("error", "Pedido no encontrado");
            return "error";
        }
    }

    // Método que devuelve una lista de todos los items en pedidos
    @GetMapping("/mostrarItemsPedido")
    public String mostrarItemsPedido(Model model) {
        List<ItemPedido> itemsPedidos = serviceItemPedido.findAll();
        if (itemsPedidos != null && !itemsPedidos.isEmpty()) {
            model.addAttribute("itemsPedidos", itemsPedidos);
            return "/administrador/ListaItemsPedido";
        } else {
            model.addAttribute("error", "Items de pedido no encontrados");
            return "error";
        }
    }

    // Método que muestra los detalles de un item en pedido específico
    @GetMapping("/mostrarItemPedido/{id}")
    public String mostrarItemPedido(@PathVariable int id, Model model) {
        Optional<ItemPedido> optionalItemPedido = serviceItemPedido.findById(id);
        if (optionalItemPedido.isPresent()) {
            model.addAttribute("itemPedido", optionalItemPedido.get());
            return "/administrador/VerItemPedido";
        } else {
            model.addAttribute("error", "Item de pedido no encontrado");
            return "error"; 
        }
    }

}
