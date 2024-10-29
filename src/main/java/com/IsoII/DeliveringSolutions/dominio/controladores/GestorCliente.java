package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;

import java.util.Optional;
import java.util.Set;
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
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.entidades.Usuario;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.persistencia.ClienteDAO;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;

import jakarta.servlet.http.HttpSession;

import com.IsoII.DeliveringSolutions.persistencia.CartaMenuDAO;
import com.IsoII.DeliveringSolutions.persistencia.ItemMenuDAO;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceCartaMenu;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceDireccion;

@Controller
@RequestMapping("/clientes")
public class GestorCliente {

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private RestauranteDAO RestauranteDAO;

    @Autowired
    private ServiceCartaMenu serviceCartaMenu;

    @Autowired
    private ServiceDireccion serviceDireccion;

    @Autowired
    private ItemMenuDAO itemMenuDAO;

    // ************************************************** GETMAPPING
    // ********************************************** */
    // Método que devuelve una lista de todos los clientes
    @GetMapping("/findAll")
    @ResponseBody
    public List<Cliente> findAll() {
        return clienteDAO.findAll();
    }

    // Método que muestra el formulario de registro de cliente
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterClient"; // Nombre del archivo HTML sin la extensión
    }

    // Método que busca un solo cliente por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Cliente findById(@PathVariable String id) {
        return clienteDAO.findById(id).orElse(null);
    }

    @GetMapping("/verRestaurantes")
    public String verRestaurantes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            // Si el usuario no está autenticado, se muestra como "anónimo"
            Usuario anonimo = new Usuario();
            anonimo.setIdUsuario("anónimo");
            model.addAttribute("usuario", anonimo);
        } else if (usuario instanceof Cliente cliente) {
            // Si el usuario es un Cliente, lo añadimos como tal para acceder a favoritos
            model.addAttribute("usuario", cliente);
        } else {
            // Si el usuario no es de tipo Cliente, lo añadimos sin la lista de favoritos
            model.addAttribute("usuario", usuario);
        }

        List<Restaurante> restaurantes = RestauranteDAO.findAll();
        model.addAttribute("restaurantes", restaurantes);
        model.addAttribute("vistaFavoritos", false); // Indica que es la vista general
        return "verRestaurantes";
    }

    @GetMapping("/listar")
    public String listarRestaurantes(Model model) {
        // Obtener todos los restaurantes de la base de datos
        List<Restaurante> restaurantes = RestauranteDAO.findAll();

        // Agregar la lista de restaurantes al modelo para que Thymeleaf pueda acceder a
        // ella
        model.addAttribute("restaurantes", restaurantes);

        // Retornar la vista "verRestaurantes"
        return "verRestaurantes"; // Nombre del archivo Thymeleaf
    }

    @GetMapping("/filtrar")
    public String filtrarRestaurantes(@RequestParam(required = false) String nombre, Model model) {
        List<Restaurante> restaurantes;

        // Si se proporciona el nombre, filtramos por nombre, de lo contrario listamos
        // todos los restaurantes
        if (nombre != null && !nombre.isEmpty()) {
            restaurantes = RestauranteDAO.findAll().stream()
                    .filter(r -> r.getNombre() != null && r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        } else {
            restaurantes = RestauranteDAO.findAll();
        }

        // Añadimos la lista de restaurantes filtrados o completos al modelo
        model.addAttribute("restaurantes", restaurantes);
        return "verRestaurantes"; // Nombre de la vista que contiene la lista de restaurantes
    }

    @GetMapping("/verMenusRestaurante/{id}")
    public String verMenusRestaurante(@PathVariable String id, Model model) {
        // Buscar el restaurante por su idUsuario
        Optional<Restaurante> optionalRestaurante = RestauranteDAO.findById(id);

        // Si el restaurante existe, pasarlo al modelo
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();
            List<CartaMenu> menus = serviceCartaMenu.findByRestaurante(restaurante);

            if (menus.isEmpty()) {
                model.addAttribute("error", "No hay menús disponibles");
                return "error";
            }

            model.addAttribute("restaurante", restaurante);
            model.addAttribute("menus", menus);

            return "verMenusRestaurante"; // Nombre de la vista
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error"; // Vista de error si no se encuentra el restaurante
        }
    }

    @GetMapping("/editarDatos")
    public String editarDatos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        System.out.println("<<USUARIO>>:: " + usuario);

        model.addAttribute("usuario", usuario);

        Direccion direccionOptional = serviceDireccion.findByUsuario(usuario);

        System.out.println("<<DIRECCION>>: " + direccionOptional);
        if (direccionOptional == null) {
            direccionOptional = new Direccion();
        }
        model.addAttribute("direccion", direccionOptional);

        Cliente cliente = clienteDAO.findById(usuario.getIdUsuario()).orElse(null);
        model.addAttribute("cliente", usuario);

        System.out.println("<<DIRECCION>>: " + direccionOptional);

        return "editarDatosCliente"; // Vista para editar los datos del cliente
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate(); // Invalida la sesión actual
        redirectAttributes.addFlashAttribute("mensaje", "Has cerrado sesión.");
        return "redirect:/";
    }

    // ************************************************** POSTMAPPING
    // ********************************************** */
    // Método que registra un cliente
    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute Cliente cliente) {
        // Comprobar si 'pass' no es nulo o vacío
        System.out.println("Cliente recibido: " + cliente.toString());
        if (cliente.getPass() == null || cliente.getPass().isEmpty()) {
            return "redirect:/clientes/register"; // Devuelve un error si 'pass' está vacío
        }

        Cliente clienteRegistrado = clienteDAO.save(cliente);
        System.out.println("Cliente registrado: " + clienteRegistrado);
        return "redirect:/"; // Redirige al index si el cliente se registra correctamente
    }

    // Método que elimina un cliente por su id
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        clienteDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/favoritos")
    public String verFavoritos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario instanceof Cliente cliente) {
            model.addAttribute("usuario", cliente);
            model.addAttribute("restaurantes", cliente.getFavoritos());
            model.addAttribute("vistaFavoritos", true); // Indica que es la vista de favoritos
            return "verRestaurantes"; // Muestra solo los favoritos en la misma vista
        }

        return "redirect:/clientes/verRestaurantes";
    }

    @PostMapping("/marcarFavorito/{idRestaurante}")
    public String marcarComoFavorito(@PathVariable String idRestaurante, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario está autenticado y es un cliente
        if (usuario == null || !(usuario instanceof Cliente)) {
            redirectAttributes.addFlashAttribute("error",
                    "Debes estar autenticado como cliente para marcar favoritos.");
            return "redirect:/clientes/verRestaurantes";
        }

        Cliente cliente = (Cliente) usuario;
        Optional<Restaurante> restauranteOpt = RestauranteDAO.findById(idRestaurante);

        // Verificar si el restaurante existe
        if (restauranteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Restaurante no encontrado.");
            return "redirect:/clientes/verRestaurantes";
        }

        Restaurante restaurante = restauranteOpt.get();

        // Solo añadir si no está ya en favoritos
        if (cliente.getFavoritos().contains(restaurante)) {
            redirectAttributes.addFlashAttribute("info", "El restaurante ya está en tus favoritos.");
        } else {
            cliente.addFavorito(restaurante);
            clienteDAO.save(cliente); // Guardar el cambio en la base de datos
            redirectAttributes.addFlashAttribute("success", "Restaurante marcado como favorito.");
        }

        return "redirect:/clientes/verRestaurantes";
    }

}
