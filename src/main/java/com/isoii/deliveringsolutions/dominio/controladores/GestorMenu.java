package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.service.ServiceCartaMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceItemMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceRestaurant;
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

// Controlador para gestionar las cartas de menú
@Controller
@RequestMapping("/cartas")
public class GestorMenu {

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ServiceItemMenu serviceItemMenu;

    @Autowired
    private ServiceCartaMenu serviceCartaMenu;

    @Autowired
    private ServiceRestaurant serviceRestaurant;

    // Método que devuelve una lista de todas las cartas
    @GetMapping("/findAll")
    @ResponseBody
    public List<CartaMenu> findAll() {
        return serviceCartaMenu.findAll();
    }

    // Método que muestra el formulario de registro de carta
    @GetMapping("/register/{id}")
    public String mostrarFormularioRegistro(@PathVariable String id, Model model) {
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(id);
        if (optionalRestaurante.isPresent()) {
            Restaurante restaurante = optionalRestaurante.get();

            System.out.println("Restaurante encontrado: " + restaurante.getIdUsuario());

            model.addAttribute("restaurante", restaurante);

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
        return serviceCartaMenu.findById(id).orElse(null);
    }

    // Método que devuelve una lista de todos los items del menú
    @GetMapping("/items/findAll")
    @ResponseBody
    public List<ItemMenu> findAllItems() {
        return serviceItemMenu.findAll();
    }

    // Método que muestra el formulario de registro de item del menú
    @GetMapping("/items/register")
    public String mostrarFormularioRegistroItem(Model model) {
        model.addAttribute("itemMenu", new ItemMenu());
        return "Pruebas-RegisterItemMenu";
    }

    // Método que muestra el formulario de modificación de una carta
    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable Integer id, Model model) {
        Optional<CartaMenu> optionalCartaMenu = serviceCartaMenu.findById(id);
        if (optionalCartaMenu.isPresent()) {
            CartaMenu cartaMenu = optionalCartaMenu.get();
            model.addAttribute("cartaMenu", cartaMenu);

            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setCartamenu(cartaMenu);
            model.addAttribute("itemMenu", itemMenu);

            List<ItemMenu> items = serviceItemMenu.findByCartaMenu(cartaMenu);
            model.addAttribute("items", items);

            return "gestorItems";
        } else {
            model.addAttribute("error", "Carta no encontrada");
            return "error";
        }
    }

    // Método que visualiza el formulario de registro de un item
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
        Optional<CartaMenu> optionalCarta = serviceCartaMenu.findById(cartaMenuID);

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

        if (itemMenu.getPrecio() <= 0) {
            redirectAttributes.addFlashAttribute("error", "El precio debe ser mayor que 0");
            return "redirect:/cartas/modificar/" + cartaMenuID;
        }

        serviceItemMenu.save(itemMenu);
        System.out.println("\nITEM REGISTRADO: " + itemMenu.toString());
        model.addAttribute("success", "Item registrado exitosamente.");
        model.addAttribute("itemMenu", itemMenu);
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

        String restauranteCif = cartaMenu.getRestaurante().getIdUsuario();
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(restauranteCif);
        if (!optionalRestaurante.isPresent()) {
            System.out.println("Restaurante no encontrado" + restauranteCif);
            redirectAttributes.addFlashAttribute("error", "Restaurante no encontrado");
            return "redirect:/cartas/register";
        }

        Restaurante restaurante = optionalRestaurante.get();
        cartaMenu.setRestaurante(restaurante);

        System.out.println("Carta recibida: " + cartaMenu);
        serviceCartaMenu.save(cartaMenu);
        System.out.println("Carta registrada: " + cartaMenu);
        redirectAttributes.addFlashAttribute("success", "Carta registrada exitosamente.");
        return "redirect:/restaurantes/gestion/" + restauranteCif;
    }

    // Método que elimina una carta
    @DeleteMapping("/eliminarCarta/{id}")
    public ResponseEntity<CartaMenu> eliminarCarta(@PathVariable Integer id) {
        CartaMenu cartaMenu = serviceCartaMenu.findById(id).orElse(null);
        if (cartaMenu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        serviceCartaMenu.deleteById(id);
        return new ResponseEntity<>(cartaMenu, HttpStatus.OK);
    }

    // Método que elimina un item
    @DeleteMapping("/eliminarItem/{id}")
    @ResponseBody
    public ResponseEntity<Void> eliminarItem(@PathVariable Integer id) {
        System.out.println("\nBuscando item: " + id + "\n");
        Optional<ItemMenu> optionalItemMenu = serviceItemMenu.findById(id);
        System.out.println("\nItem encontrado: " + optionalItemMenu + "\n");
        if (optionalItemMenu.isPresent()) {
            serviceItemMenu.deleteById(optionalItemMenu.get().getIdItemMenu());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método que devuelve una lista de todos los items del menú
    @GetMapping("/mostrarItems")
    public String mostrarItems(Model model) {
        List<ItemMenu> items = serviceItemMenu.findAll();
        if (items != null && !items.isEmpty()) {
            model.addAttribute("items", items);
            return "/administrador/ListaItemsMenu";
        } else {
            model.addAttribute("error", "No se encontraron items");
            return "error";
        }
    }

    // Método que muestra los detalles de un item del menú
    @GetMapping("/mostrarItem/{id}")
    public String mostrarItem(@PathVariable Integer id, Model model) {
        Optional<ItemMenu> optionalItem = serviceItemMenu.findById(id);
        if (optionalItem.isPresent()) {
            ItemMenu itemMenu = optionalItem.get();
            model.addAttribute("itemMenu", itemMenu);
            return "/administrador/VerItemMenu";
        } else {
            model.addAttribute("error", "Item no encontrado");
            return "error";
        }
    }

    // Método que devuelve la lista de todos los menús
    @GetMapping("/mostrarMenus")
    public String mostrarMenus(Model model) {
        List<CartaMenu> cartas = serviceCartaMenu.findAll();
        if (cartas != null && !cartas.isEmpty()) {
            model.addAttribute("cartas", cartas);
            return "/administrador/ListaMenus";
        } else {
            model.addAttribute("error", "No se encontraron menús");
            return "error";
        }
    }

    // Método que muestra los detalles de un menú específico por su ID
    @GetMapping("/mostrarMenu/{id}")
    public String mostrarMenu(@PathVariable Integer id, Model model) {
        Optional<CartaMenu> optionalCartaMenu = serviceCartaMenu.findById(id);
        if (optionalCartaMenu.isPresent()) {
            model.addAttribute("cartaMenu", optionalCartaMenu.get());
            return "/administrador/VerMenu";
        } else {
            model.addAttribute("error", "Menú no encontrado");
            return "error";
        }
    }

}
