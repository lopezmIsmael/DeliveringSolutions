package com.isoii.deliveringsolutions.dominio.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.entidades.Usuario;

import jakarta.servlet.http.HttpSession;

import com.isoii.deliveringsolutions.dominio.service.ServiceCartaMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceClient;
import com.isoii.deliveringsolutions.dominio.service.ServiceDireccion;
import com.isoii.deliveringsolutions.dominio.service.ServicePedido;
import com.isoii.deliveringsolutions.dominio.service.ServiceRestaurant;

@Controller
@RequestMapping("/clientes")
public class GestorCliente {

    private static final String USUARIO = "usuario";
    private static final String CLIENTE = "cliente";
    private static final String REDIRECT_ROOT = "redirect:/";
    private static final String RESTAURANTES = "restaurantes";
    private static final String VER_RESTAURANTES = "verRestaurantes";
    private static final String ERROR = "error";

    private final ServiceCartaMenu serviceCartaMenu;
    private final ServiceDireccion serviceDireccion;
    private final ServiceClient serviceClient;
    private final ServiceRestaurant serviceRestaurant;
    private final ServicePedido servicePedido;

    Logger logger = LoggerFactory.getLogger(GestorCliente.class);

    @Autowired
    public GestorCliente(ServiceCartaMenu serviceCartaMenu, ServiceDireccion serviceDireccion, 
                         ServiceClient serviceClient, ServiceRestaurant serviceRestaurant, 
                         ServicePedido servicePedido) {
        this.serviceCartaMenu = serviceCartaMenu;
        this.serviceDireccion = serviceDireccion;
        this.serviceClient = serviceClient;
        this.serviceRestaurant = serviceRestaurant;
        this.servicePedido = servicePedido;
    }

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @GetMapping("/findAll")
    
    public List<Cliente> findAll() {
        return serviceClient.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterClient";
    }

    @GetMapping("/findById/{id}")
    
    public Cliente findById(@PathVariable String id) {
        return serviceClient.findById(id).orElse(null);
    }

    @GetMapping("/verRestaurantes")
    public String verRestaurantes(@RequestParam(value = "favoritos", required = false) String favoritosParam,
            Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        model.addAttribute(USUARIO, usuario);

        boolean vistaFavoritos = "true".equalsIgnoreCase(favoritosParam);
        model.addAttribute("vistaFavoritos", vistaFavoritos);

        List<Restaurante> restaurantes;

        if (vistaFavoritos) {
            if (usuario != null) {
                Cliente cliente = serviceClient.findById(usuario.getIdUsuario()).orElse(null);
                if (cliente != null) {
                    restaurantes = new ArrayList<>(cliente.getFavoritos());
                    model.addAttribute(CLIENTE, cliente);
                } else {
                    restaurantes = new ArrayList<>();
                }
            } else {
                return REDIRECT_ROOT;
            }
        } else {
            restaurantes = serviceRestaurant.findAll();
        }

        model.addAttribute(RESTAURANTES, restaurantes);
        return VER_RESTAURANTES;
    }

    @GetMapping("/listar")
    public String listarRestaurantes(Model model) {
        List<Restaurante> restaurantes = serviceRestaurant.findAll();
        model.addAttribute(RESTAURANTES, restaurantes);
        return VER_RESTAURANTES;
    }

    @GetMapping("/filtrar")
    public String filtrarRestaurantes(@RequestParam(required = false) String nombre, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        model.addAttribute(USUARIO, usuario);
        model.addAttribute("vistaFavoritos", false);

        List<Restaurante> restaurantes = serviceRestaurant.findAll();
        if (nombre != null && !nombre.trim().isEmpty()) {
            restaurantes = restaurantes.stream()
                    .filter(r -> r.getNombre() != null && r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        }

        model.addAttribute(RESTAURANTES, restaurantes);
        return VER_RESTAURANTES;
    }

    @GetMapping("/verMenusRestaurante/{id}")
    public String verMenusRestaurante(@PathVariable String id, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        if (usuario == null) {
            return REDIRECT_ROOT; 
        }

        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(id);

        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            List<CartaMenu> menus = serviceCartaMenu.findByRestaurante(restaurante);

            if (menus.isEmpty()) {
                model.addAttribute(ERROR, "No hay menús disponibles");
                return ERROR;
            }

            model.addAttribute("restaurante", restaurante);
            model.addAttribute("menus", menus);

            return "verMenusRestaurante";
        } else {
            model.addAttribute(ERROR, "Restaurante no encontrado");
            return ERROR;
        }
    }

    @GetMapping("/editarDatos")
    public String editarDatos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(USUARIO);

        logger.info("<<USUARIO>>: {}", usuario);

        model.addAttribute(USUARIO, usuario);

        List<Direccion> direcciones = serviceDireccion.findByUsuario(usuario);

        if (direcciones == null || direcciones.isEmpty()) {
            direcciones = List.of(new Direccion());
        }
        model.addAttribute("direcciones", direcciones);

        model.addAttribute(CLIENTE, usuario);

        return "editarDatosCliente";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensaje", "Has cerrado sesión.");
        return REDIRECT_ROOT;
    }

    @GetMapping("/verPedidos")
    public String verPedidos(Model model, HttpSession session) {
        List<Pedido> pedidos = servicePedido.findAll();
        List<Pedido> pedidosCliente = new ArrayList<>();

        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        if (usuario == null) {
            return REDIRECT_ROOT;
        }

        for (Pedido pedido : pedidos) {
            if (pedido.getCliente() != null && 
                pedido.getCliente().getIdUsuario().equals(usuario.getIdUsuario())) {
                pedidosCliente.add(pedido);
            }
        }

        Cliente cliente = serviceClient.findById(usuario.getIdUsuario()).orElse(null);
        if (cliente == null) {
            return REDIRECT_ROOT;
        }

        model.addAttribute(CLIENTE, cliente);
        model.addAttribute("pedidos", pedidosCliente);
        return "verPedidosCliente";
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute Cliente cliente) {

        if (cliente.getPass() == null || cliente.getPass().isEmpty()) {
            return "redirect:/clientes/register";
        }

        Cliente clienteRegistrado = serviceClient.save(cliente);
        logger.info("Cliente registrado: {}", clienteRegistrado);
        return REDIRECT_ROOT;
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        serviceClient.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/toggleFavorito/{id}")
    public String toggleFavorito(@PathVariable String id, HttpSession session,
            @RequestParam(value = "favoritos", required = false) String favoritosParam) {
        Usuario usuario = (Usuario) session.getAttribute(USUARIO);

        if (usuario == null) {
            return REDIRECT_ROOT;
        }

        Cliente cliente = serviceClient.findById(usuario.getIdUsuario()).orElse(null);
        if (cliente == null) {
            return REDIRECT_ROOT;
        }

        Restaurante restaurante = serviceRestaurant.findById(id).orElse(null);
        if (restaurante != null) {
            if (cliente.getFavoritos().contains(restaurante)) {
                cliente.removeFavorito(restaurante);
            } else {
                cliente.addFavorito(restaurante);
            }
            serviceClient.save(cliente);
        }

        boolean vistaFavoritos = "true".equalsIgnoreCase(favoritosParam);
        String redirectUrl = vistaFavoritos ? "/clientes/verRestaurantes?favoritos=true" : "/clientes/verRestaurantes";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/mostrarClientes")
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = serviceClient.findAll();
        if (clientes != null && !clientes.isEmpty()) {
            model.addAttribute("clientes", clientes);
            return "/administrador/ListaClientes";
        } else {
            model.addAttribute(ERROR, "Clientes no encontrados");
            return ERROR;
        }
    }

    @GetMapping("/mostrarCliente/{id}")
    public String mostrarCliente(@PathVariable String id, Model model) {
        Optional<Cliente> optionalCliente = serviceClient.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            model.addAttribute(CLIENTE, cliente);
            return "/administrador/VerCliente";
        } else {
            model.addAttribute(ERROR, "Cliente no encontrado");
            return ERROR;
        }
    }
}
