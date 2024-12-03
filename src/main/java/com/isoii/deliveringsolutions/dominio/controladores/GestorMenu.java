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

    private static final String CARTA_MENU = "cartaMenu";
    private static final String ITEM_MENU = "itemMenu";
    private static final String ERROR = "error";
    private static final String RESTAURANTE_NO_ENCONTRADO = "Restaurante no encontrado";
    private static final String REDIRECT_MODIFICAR_CARTA = "redirect:/cartas/modificar/";
    private static final String ITEM_RECIBIDO = "ITEM RECIBIDO: ";
    private static final String REDIRECT_REGISTER_CARTA = "redirect:/cartas/register";

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    private final ServiceItemMenu serviceItemMenu;
    private final ServiceCartaMenu serviceCartaMenu;
    private final ServiceRestaurant serviceRestaurant;

    @Autowired
    public GestorMenu(ServiceItemMenu serviceItemMenu, ServiceCartaMenu serviceCartaMenu, ServiceRestaurant serviceRestaurant) {
        this.serviceItemMenu = serviceItemMenu;
        this.serviceCartaMenu = serviceCartaMenu;
        this.serviceRestaurant = serviceRestaurant;
    }

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

            model.addAttribute("restaurante", restaurante);

            CartaMenu cartaMenu = new CartaMenu();
            cartaMenu.setRestaurante(restaurante);
            model.addAttribute(CARTA_MENU, cartaMenu);

            return "Pruebas-RegisterMenu";
        } else {
            model.addAttribute(ERROR, RESTAURANTE_NO_ENCONTRADO);
            return ERROR;
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
        model.addAttribute(ITEM_MENU, new ItemMenu());
        return "Pruebas-RegisterItemMenu";
    }

    // Método que muestra el formulario de modificación de una carta
    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable Integer id, Model model) {
        Optional<CartaMenu> optionalCartaMenu = serviceCartaMenu.findById(id);
        if (optionalCartaMenu.isPresent()) {
            CartaMenu cartaMenu = optionalCartaMenu.get();
            model.addAttribute(CARTA_MENU, cartaMenu);

            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setCartamenu(cartaMenu);
            model.addAttribute(ITEM_MENU, itemMenu);

            List<ItemMenu> items = serviceItemMenu.findByCartaMenu(cartaMenu);
            model.addAttribute("items", items);

            return "gestorItems";
        } else {
            model.addAttribute(ERROR, "Carta no encontrada");
            return ERROR;
        }
    }

    // Método que visualiza el formulario de registro de un item
    @PostMapping("/items/registrarItem")
    public String registrarItem(@ModelAttribute ItemMenu itemMenu, Model model, RedirectAttributes redirectAttributes,
            BindingResult result) {

        if (itemMenu.getCartamenu() == null || itemMenu.getCartamenu().getIdCarta() == 0) {
            model.addAttribute(ERROR, "Carta no válida");
            return REDIRECT_MODIFICAR_CARTA + itemMenu.getCartamenu().getIdCarta();
        }

        Integer cartaMenuID = itemMenu.getCartamenu().getIdCarta();
        Optional<CartaMenu> optionalCarta = serviceCartaMenu.findById(cartaMenuID);

        if (!optionalCarta.isPresent()) {
            model.addAttribute(ERROR, "Carta no encontrada");
            return REDIRECT_MODIFICAR_CARTA + cartaMenuID;
        }

        CartaMenu cartaMenu = optionalCarta.get();
        itemMenu.setCartamenu(cartaMenu);

        if (itemMenu.getNombre() == null || itemMenu.getNombre().isEmpty()) {
            model.addAttribute(ERROR, "Carta no válida, nombre no puede estar vacío");
            return REDIRECT_MODIFICAR_CARTA + cartaMenuID;
        }

        if (itemMenu.getTipo() == null || itemMenu.getTipo().isEmpty()) {
            model.addAttribute(ERROR, "Carta no válida, tipo no puede estar vacío");
            return REDIRECT_MODIFICAR_CARTA + cartaMenuID;
        }

        if (itemMenu.getPrecio() <= 0) {
            redirectAttributes.addFlashAttribute(ERROR, "El precio debe ser mayor que 0");
            return REDIRECT_MODIFICAR_CARTA + cartaMenuID;
        }

        serviceItemMenu.save(itemMenu);
        model.addAttribute("success", "Item registrado exitosamente.");
        model.addAttribute(ITEM_MENU, itemMenu);
        return REDIRECT_MODIFICAR_CARTA + itemMenu.getCartamenu().getIdCarta();
    }

    // Método que registra una carta
    @PostMapping("/registrarCarta")
    public String registrarCarta(@ModelAttribute CartaMenu cartaMenu, RedirectAttributes redirectAttributes) {
        if (cartaMenu.getRestaurante() == null || cartaMenu.getRestaurante().getIdUsuario() == null) {
            redirectAttributes.addFlashAttribute(ERROR, "Restaurante no válido");
            return REDIRECT_REGISTER_CARTA;
        }

        if (cartaMenu.getNombre() == null || cartaMenu.getNombre().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El nombre de la carta no puede estar vacío");
            return "redirect:/cartas/register";
        }

        String restauranteCif = cartaMenu.getRestaurante().getIdUsuario();
        Optional<Restaurante> optionalRestaurante = serviceRestaurant.findById(restauranteCif);
        if (!optionalRestaurante.isPresent()) {
            redirectAttributes.addFlashAttribute(ERROR, RESTAURANTE_NO_ENCONTRADO);
            return REDIRECT_REGISTER_CARTA;
        }

        Restaurante restaurante = optionalRestaurante.get();
        cartaMenu.setRestaurante(restaurante);

        serviceCartaMenu.save(cartaMenu);
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
        Optional<ItemMenu> optionalItemMenu = serviceItemMenu.findById(id);
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
            model.addAttribute(ERROR, "No se encontraron items");
            return ERROR;
        }
    }

    // Método que muestra los detalles de un item del menú
    @GetMapping("/mostrarItem/{id}")
    public String mostrarItem(@PathVariable Integer id, Model model) {
        Optional<ItemMenu> optionalItem = serviceItemMenu.findById(id);
        if (optionalItem.isPresent()) {
            ItemMenu itemMenu = optionalItem.get();
            model.addAttribute(ITEM_MENU, itemMenu);
            return "/administrador/VerItemMenu";
        } else {
            model.addAttribute(ERROR, "Item no encontrado");
            return ERROR;
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
            model.addAttribute(ERROR, "No se encontraron menús");
            return ERROR;
        }
    }

    // Método que muestra los detalles de un menú específico por su ID
    @GetMapping("/mostrarMenu/{id}")
    public String mostrarMenu(@PathVariable Integer id, Model model) {
        Optional<CartaMenu> optionalCartaMenu = serviceCartaMenu.findById(id);
        if (optionalCartaMenu.isPresent()) {
            model.addAttribute(CARTA_MENU, optionalCartaMenu.get());
            return "/administrador/VerMenu";
        } else {
            model.addAttribute(ERROR, "Menú no encontrado");
            return ERROR;
        }
    }

}
