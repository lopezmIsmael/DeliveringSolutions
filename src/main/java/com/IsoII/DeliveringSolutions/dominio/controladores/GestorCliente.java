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
            // Opcional: En lugar de cerrar la sesión, mostrar un mensaje
            return "redirect:/login"; // Cambia a tu vista de login si el usuario no está en sesión
        }
        model.addAttribute("usuario", usuario); // Usuario de la sesión

        List<Restaurante> restaurantes = RestauranteDAO.findAll();
        model.addAttribute("restaurantes", restaurantes);
        model.addAttribute("vistaFavoritos", false); // Indica que se ven todos los restaurantes
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
    public String filtrarRestaurantes(@RequestParam(required = false) String nombre, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("vistaFavoritos", false); // Filtrar en "todos" por defecto

        List<Restaurante> restaurantes = RestauranteDAO.findAll();
        if (nombre != null && !nombre.trim().isEmpty()) {
            restaurantes = restaurantes.stream()
                    .filter(r -> r.getNombre() != null && r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        }

        model.addAttribute("restaurantes", restaurantes);
        return "verRestaurantes";
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

    @GetMapping("/verFavoritos")
    public String verFavoritos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:http://localhost:8080/"; // Redirige a la URL especificada si el usuario no está en sesión
        }
        model.addAttribute("usuario", usuario); // Usuario de la sesión

        Cliente cliente = clienteDAO.findById(usuario.getIdUsuario()).orElse(null);
        if (cliente == null) {
            return "redirect:http://localhost:8080/"; // Redirige a la URL especificada si el cliente no está en sesión
        }

        Set<Restaurante> favoritos = cliente.getFavoritos();
        model.addAttribute("restaurantes", favoritos);
        model.addAttribute("vistaFavoritos", true); // Indica que se ven los favoritos
        return "verRestaurantes";
    }

    @PostMapping("/marcarFavorito/{nombre}")
    public String marcarFavorito(@PathVariable String nombre, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:http://localhost:8080/"; // Redirige a la URL especificada si el usuario no está en sesión
        }

        Cliente cliente = clienteDAO.findById(usuario.getIdUsuario()).orElse(null);
        if (cliente == null) {
            return "redirect:http://localhost:8080/"; // Redirige a la URL especificada si el cliente no está en sesión
        }

        // Lógica para marcar el restaurante como favorito
        Restaurante restaurante = // lógica para obtener el restaurante por nombre
        RestauranteDAO.findById(nombre).orElse(null);
        clienteDAO.save(cliente);

        return "redirect:/clientes/verFavoritos"; // Redirige a la vista de favoritos
    }

}
