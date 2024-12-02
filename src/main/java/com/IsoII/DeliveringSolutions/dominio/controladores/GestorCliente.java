package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.Cliente;
import com.IsoII.DeliveringSolutions.dominio.entidades.Direccion;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pedido;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;

import jakarta.servlet.http.HttpSession;

import com.IsoII.DeliveringSolutions.dominio.service.ServiceCartaMenu;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceClient;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;
import com.IsoII.DeliveringSolutions.dominio.service.ServicePedido;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceRestaurant;

// Controlador de la entidad Cliente
@Controller
@RequestMapping("/clientes")
public class GestorCliente {

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceCartaMenu serviceCartaMenu;
    private final ServiceDireccion serviceDireccion;
    private final ServiceClient serviceClient;
    private final ServiceRestaurant serviceRestaurant;
    private final ServicePedido servicePedido;

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

    // Método que muestra la página principal de la aplicación
    @GetMapping("/findAll")
    @ResponseBody
    public List<Cliente> findAll() {
        return serviceClient.findAll();
    }

    // Método que muestra el formulario de registro de cliente
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterClient";
    }

    // Método que busca un solo cliente por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Cliente findById(@PathVariable String id) {
        return serviceClient.findById(id).orElse(null);
    }

    // Método que busca un solo cliente por su nombre
    @GetMapping("/verRestaurantes")
    public String verRestaurantes(@RequestParam(value = "favoritos", required = false) String favoritosParam,
            Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        boolean vistaFavoritos = "true".equalsIgnoreCase(favoritosParam);
        model.addAttribute("vistaFavoritos", vistaFavoritos);

        List<Restaurante> restaurantes;

        if (vistaFavoritos) {
            if (usuario != null) {
                Cliente cliente = serviceClient.findById(usuario.getIdUsuario()).orElse(null);
                if (cliente != null) {
                    restaurantes = new ArrayList<>(cliente.getFavoritos());
                    model.addAttribute("cliente", cliente);
                } else {
                    restaurantes = new ArrayList<>();
                }
            } else {
                return "redirect:/";
            }
        } else {
            restaurantes = serviceRestaurant.findAll();
        }

        model.addAttribute("restaurantes", restaurantes);
        return "verRestaurantes";
    }

    // Método que lista todos los restaurantes
    @GetMapping("/listar")
    public String listarRestaurantes(Model model) {
        List<Restaurante> restaurantes = serviceRestaurant.findAll();
        model.addAttribute("restaurantes", restaurantes);
        return "verRestaurantes";
    }

    // Método que filtra los restaurantes por nombre
    @GetMapping("/filtrar")
    public String filtrarRestaurantes(@RequestParam(required = false) String nombre, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("vistaFavoritos", false);

        List<Restaurante> restaurantes = serviceRestaurant.findAll();
        if (nombre != null && !nombre.trim().isEmpty()) {
            restaurantes = restaurantes.stream()
                    .filter(r -> r.getNombre() != null && r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        }

        model.addAttribute("restaurantes", restaurantes);
        return "verRestaurantes";
    }

    // Método que muestra los detalles de un restaurante
    @GetMapping("/verMenusRestaurante/{id}")
    public String verMenusRestaurante(@PathVariable String id, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/"; 
        }

        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(id);

        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            List<CartaMenu> menus = serviceCartaMenu.findByRestaurante(restaurante);

            if (menus.isEmpty()) {
                model.addAttribute("error", "No hay menús disponibles");
                return "error";
            }

            model.addAttribute("restaurante", restaurante);
            model.addAttribute("menus", menus);

            return "verMenusRestaurante";
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error";
        }
    }

    // Método que edita los datos de un cliente pudiendo registrar un cliente dos
    // direcciones
    @GetMapping("/editarDatos")
    public String editarDatos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        System.out.println("<<USUARIO>>:: " + usuario);

        model.addAttribute("usuario", usuario);

        List<Direccion> direcciones = serviceDireccion.findByUsuario(usuario);

        System.out.println("<<DIRECCIONES>>: " + direcciones);
        if (direcciones == null || direcciones.isEmpty()) {
            direcciones = List.of(new Direccion());
        }
        model.addAttribute("direcciones", direcciones);

        model.addAttribute("cliente", usuario);

        return "editarDatosCliente";
    }

    // Método que cierra la sesión de un cliente
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensaje", "Has cerrado sesión.");
        return "redirect:/";
    }

    // Metodo que muestra los pedidos de ese cliente
    @GetMapping("/verPedidos")
    public String verPedidos(Model model, HttpSession session) {
        List<Pedido> pedidos = servicePedido.findAll();
        List<Pedido> pedidosCliente = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            // Si el pedido pertenece al cliente, mostrar
            if (pedido.getCliente().getIdUsuario().equals(((Usuario) session.getAttribute("usuario")).getIdUsuario())) {
                pedidosCliente.add(pedido);
            }
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Cliente cliente = serviceClient.findById(usuario.getIdUsuario()).orElse(null);
        if (cliente == null) {
            return "redirect:/";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("pedidos", pedidosCliente);
        return "verPedidosCliente";
    }

    // Método que registra un cliente
    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute Cliente cliente) {

        System.out.println("Cliente recibido: " + cliente.toString());
        if (cliente.getPass() == null || cliente.getPass().isEmpty()) {
            return "redirect:/clientes/register";
        }

        Cliente clienteRegistrado = serviceClient.save(cliente);
        System.out.println("Cliente registrado: " + clienteRegistrado);
        return "redirect:/";
    }

    // Método que elimina un cliente por su id
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        serviceClient.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Método que implementa la funcionalidad de añadir un restaurante a favoritos
    @PostMapping("/toggleFavorito/{id}")
    public String toggleFavorito(@PathVariable String id, HttpSession session,
            @RequestParam(value = "favoritos", required = false) String favoritosParam) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        System.out.println("<<USUARIO>> toogleFavorito Postmapping: " + usuario);

        if (usuario == null) {
            return "redirect:/";
        }

        Cliente cliente = serviceClient.findById(usuario.getIdUsuario()).orElse(null);
        if (cliente == null) {
            return "redirect:/";
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

    // Método que muestra los clientes registrados
    @GetMapping("/mostrarClientes")
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = serviceClient.findAll();
        if (clientes != null && !clientes.isEmpty()) {
            model.addAttribute("clientes", clientes);
            return "/administrador/ListaClientes";
        } else {
            model.addAttribute("error", "Clientes no encontrados");
            return "error";
        }
    }

    // Método que muestra los detalles de un cliente
    @GetMapping("/mostrarCliente/{id}")
    public String mostrarCliente(@PathVariable String id, Model model) {
        Optional<Cliente> optionalCliente = serviceClient.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            model.addAttribute("cliente", cliente);
            return "/administrador/VerCliente";
        } else {
            model.addAttribute("error", "Cliente no encontrado");
            return "error";
        }
    }
}