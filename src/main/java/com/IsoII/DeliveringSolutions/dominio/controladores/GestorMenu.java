package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceCartaMenu;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceItemMenu;
import com.IsoII.DeliveringSolutions.persistencia.CartaMenuDAO;
import com.IsoII.DeliveringSolutions.persistencia.ItemMenuDAO;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cartas")
public class GestorMenu {

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private CartaMenuDAO cartaMenuDAO;

    @Autowired
    private ItemMenuDAO itemMenuDAO;

    @Autowired
    private RestauranteDAO restauranteDAO;

    @Autowired
    private ServiceCartaMenu serviceCartaMenu;

    @Autowired
    private ServiceItemMenu serviceItemMenu;

    // ************************************************** GETMAPPING
    // ********************************************** */

    // Método que devuelve una lista de todas las cartas
    @GetMapping("/findAll")
    @ResponseBody
    public List<CartaMenu> findAll() {
        return cartaMenuDAO.findAll();
    }

    @GetMapping("/mostrarMenu")
    public String mostrarMenu(Model model) {
        List<CartaMenu> cartas = serviceCartaMenu.findAll();
        model.addAttribute("cartas", cartas);
        return "/administrador/ListaMenus";
    }

    @GetMapping("/register/{id}")
    public String mostrarFormularioRegistro(@PathVariable String id, Model model) {
        Optional<Restaurante> optionalRestaurante = restauranteDAO.findById(id);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();

            // Asegurarse de que restaurante tiene idUsuario
            System.out.println("Restaurante encontrado: " + restaurante.getIdUsuario());

            model.addAttribute("restaurante", restaurante);

            // Crear una nueva instancia de CartaMenu y establecer el restaurante
            CartaMenu cartaMenu = new CartaMenu();
            cartaMenu.setRestaurante(restaurante);
            model.addAttribute("cartaMenu", cartaMenu);

            return "Pruebas-RegisterMenu";
        } else {
            model.addAttribute("error", "Restaurante no encontrado");
            return "error";
        }
    }

    // Método que busca una sola carta por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public CartaMenu findById(@PathVariable Integer id) {
        return cartaMenuDAO.findById(id).orElse(null);
    }

    // Método que devuelve una lista de todos los items del menú
    @GetMapping("/items/findAll")
    @ResponseBody
    public List<ItemMenu> findAllItems() {
        return itemMenuDAO.findAll();
    }

    // Método que muestra el formulario de registro de item del menú
    @GetMapping("/items/register")
    public String mostrarFormularioRegistroItem(Model model) {
        model.addAttribute("itemMenu", new ItemMenu());
        return "Pruebas-RegisterItemMenu"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable Integer id, Model model) {
        Optional<CartaMenu> optionalCartaMenu = cartaMenuDAO.findById(id);
        if (optionalCartaMenu.isPresent()) {
            CartaMenu cartaMenu = optionalCartaMenu.get();
            model.addAttribute("cartaMenu", cartaMenu);

            // Crear un nuevo ItemMenu y asignarle la cartaMenu
            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setCartamenu(cartaMenu); // Inicializar cartamenu
            model.addAttribute("itemMenu", itemMenu);

            // Añadir la lista de items al modelo
            List<ItemMenu> items = itemMenuDAO.findByCartamenu(cartaMenu);
            model.addAttribute("items", items);

            return "gestorItems";
        } else {
            model.addAttribute("error", "Carta no encontrada");
            return "error"; // Asegúrate de tener una plantilla 'error.html'
        }
    }

    // ************************************************** POSTMAPPING
    // ********************************************** */
    // Método que registra un item del menú
    @PostMapping("/items/registrarItem")
    public String registrarItem(@ModelAttribute ItemMenu itemMenu, Model model, RedirectAttributes redirectAttributes,
            BindingResult result) {

        if (itemMenu.getCartamenu() == null || itemMenu.getCartamenu().getIdCarta() == 0) {
            model.addAttribute("error", "Carta no válida");
            return "redirect:/cartas/modificar/" + itemMenu.getCartamenu().getIdCarta();
        }
        System.out.println("\nITEM RECIBIDO: " + itemMenu.getNombre() + "\n");
        System.out.println("\nITEM RECIBIDO: " + itemMenu.getTipo() + "\n");
        System.out.println("\nITEM RECIBIDO: " + itemMenu.getPrecio() + "\n");

        Integer cartaMenuID = itemMenu.getCartamenu().getIdCarta();
        Optional<CartaMenu> optionalCarta = cartaMenuDAO.findById(cartaMenuID);

        System.out.println("\nCARTA RECIBIDA: " + cartaMenuID + "\n");

        if (!optionalCarta.isPresent()) {
            model.addAttribute("error", "Carta no encontrada");
            return "redirect:/cartas/modificar/" + cartaMenuID;
        }

        CartaMenu cartaMenu = optionalCarta.get();
        itemMenu.setCartamenu(cartaMenu);

        if (itemMenu.getNombre() == null || itemMenu.getNombre().isEmpty()) {
            model.addAttribute("error", "Carta no válida, nombre no puede estar vacío");
            return "redirect:/cartas/modificar/" + cartaMenuID;
        }

        if (itemMenu.getTipo() == null || itemMenu.getTipo().isEmpty()) {
            model.addAttribute("error", "Carta no válida, tipo no puede estar vacío");
            return "redirect:/cartas/modificar/" + cartaMenuID;
        }

        // Validar el precio
        if (itemMenu.getPrecio() <= 0) {
            redirectAttributes.addFlashAttribute("error", "El precio debe ser mayor que 0");
            return "redirect:/cartas/modificar/" + cartaMenuID;
        }

        // Lógica para registrar el item
        itemMenuDAO.save(itemMenu);
        System.out.println("\nITEM REGISTRADO: " + itemMenu.toString());
        model.addAttribute("success", "Item registrado exitosamente.");
        model.addAttribute("itemMenu", itemMenu); // Para resetear el formulario
        return "redirect:/cartas/modificar/" + itemMenu.getCartamenu().getIdCarta();
    }

    // Método que registra una carta
    @PostMapping("/registrarCarta")
    public String registrarCarta(@ModelAttribute CartaMenu cartaMenu, RedirectAttributes redirectAttributes) {
        if (cartaMenu.getRestaurante() == null || cartaMenu.getRestaurante().getIdUsuario() == null) {
            System.out.println("Restaurante no válido: " + cartaMenu.getRestaurante());
            redirectAttributes.addFlashAttribute("error", "Restaurante no válido");
            return "redirect:/cartas/register";
        }

        if (cartaMenu.getNombre() == null || cartaMenu.getNombre().isEmpty()) {
            System.out.println("Nombre de la carta no puede estar vacío");
            redirectAttributes.addFlashAttribute("error", "El nombre de la carta no puede estar vacío");
            return "redirect:/cartas/register";
        }

        // Buscar el Restaurante en la base de datos
        String restauranteCif = cartaMenu.getRestaurante().getIdUsuario();
        Optional<Restaurante> optionalRestaurante = restauranteDAO.findById(restauranteCif);
        if (!optionalRestaurante.isPresent()) {
            System.out.println("Restaurante no encontrado" + restauranteCif);
            redirectAttributes.addFlashAttribute("error", "Restaurante no encontrado");
            return "redirect:/cartas/register";
        }

        Restaurante restaurante = optionalRestaurante.get();
        cartaMenu.setRestaurante(restaurante); // Asignar el Restaurante persistente a cartaMenu

        // Guardar cartaMenu
        System.out.println("Carta recibida: " + cartaMenu);
        cartaMenuDAO.save(cartaMenu);
        System.out.println("Carta registrada: " + cartaMenu);
        redirectAttributes.addFlashAttribute("success", "Carta registrada exitosamente.");
        return "redirect:/restaurantes/gestion/" + restauranteCif;
    }

    // ************************************************** DELETEMAPPING
    // ********************************************** */
    // Método que elimina una carta
    @DeleteMapping("/eliminarCarta/{id}")
    public ResponseEntity<CartaMenu> eliminarCarta(@PathVariable Integer id) {
        // Comprobar si la carta existe
        CartaMenu cartaMenu = cartaMenuDAO.findById(id).orElse(null);
        if (cartaMenu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve un error si la carta no existe
        }

        cartaMenuDAO.deleteById(id);
        return new ResponseEntity<>(cartaMenu, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarItem/{id}")
    @ResponseBody
    public ResponseEntity<Void> eliminarItem(@PathVariable Integer id) {
        System.out.println("\nBuscando item: " + id + "\n");
        Optional<ItemMenu> optionalItemMenu = itemMenuDAO.findById(id);
        System.out.println("\nItem encontrado: " + optionalItemMenu + "\n");
        if (optionalItemMenu.isPresent()) {
            itemMenuDAO.delete(optionalItemMenu.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método que devuelve una lista de todos los items del menú
    @GetMapping("/items/mostrarItems")
    public String mostrarItems(Model model) {
        List<ItemMenu> items = serviceItemMenu.findAll();
        if (items != null && !items.isEmpty()) {
            model.addAttribute("items", items);
            return "/administrador/ListaItemsMenu"; 
        } else {
            model.addAttribute("error", "No se encontraron items");
            return "error"; // Vista de error si no se encuentran items
        }
    }

    // Método que muestra los detalles de un item del menú
    @GetMapping("/items/mostrarItem/{id}")
    public String mostrarItem(@PathVariable Integer id, Model model) {
        Optional<ItemMenu> optionalItem = serviceItemMenu.findById(id);
        if (optionalItem.isPresent()) {
            ItemMenu itemMenu = optionalItem.get();
            model.addAttribute("itemMenu", itemMenu);
            return "/administrador/VerItemMenu"; // Vista para ver detalles de un item
        } else {
            model.addAttribute("error", "Item no encontrado");
            return "error"; // Vista de error si no se encuentra el item
        }
    }

}
