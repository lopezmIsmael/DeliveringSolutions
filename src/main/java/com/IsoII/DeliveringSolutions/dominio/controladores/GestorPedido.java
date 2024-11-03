package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceItemPedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemPedido;
import com.IsoII.DeliveringSolutions.persistencia.PedidoDAO;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pedido")
public class GestorPedido {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private List<ItemMenu> carrito = new ArrayList<>();

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ServicePedido servicePedido;

    @Autowired
    private ServiceItemPedido serviceItemPedido;

    @GetMapping("/findAll")
    @ResponseBody
    public List<Pedido> findAll() {
        return pedidoDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterPedido"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public Pedido findById(@PathVariable Integer id) {
        return pedidoDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarPedido")
    public ResponseEntity<Pedido> registrarPedido(@ModelAttribute Pedido pedido) {
        System.out.println("Pedido recibido: " + pedido.toString());
        if (pedido.getFecha() == 0 || pedido.getEstadoPedido() == null || pedido.getEstadoPedido().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pedido pedidoRegistrado = pedidoDAO.save(pedido);
        System.out.println("Pedido registrado: " + pedidoRegistrado);
        return new ResponseEntity<>(pedidoRegistrado, HttpStatus.CREATED);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@ModelAttribute ItemMenu item) {
        carrito.add(item);
        // Aquí puedes agregar la lógica para añadir el item al carrito
        System.out.println("Item añadido al carrito: " + item.getNombre() + " - " + item.getPrecio());
        return new ResponseEntity<>("Item añadido al carrito", HttpStatus.OK);
    }

    @RequestMapping("/carrito")
    public String mostrarCarrito(Model model) {
        model.addAttribute("carrito", carrito);
        // Aquí puedes agregar la lógica para mostrar el carrito
        return "verMenusRestaurante"; // Nombre del archivo HTML sin la extensión
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
        List<ItemPedido> itemsPedido = new ArrayList();
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

}
