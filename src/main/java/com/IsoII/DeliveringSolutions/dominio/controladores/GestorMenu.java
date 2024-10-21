package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.CartaMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.persistencia.CartaMenuDAO;
import com.IsoII.DeliveringSolutions.persistencia.ItemMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;

@Controller
@RequestMapping("/cartas")
public class GestorMenu {
    
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private CartaMenuDAO cartaMenuDAO;

    @Autowired
    private ItemMenuDAO itemMenuDAO;

    // ************************************************** GETMAPPING ********************************************** */

    // Método que devuelve una lista de todas las cartas
    @GetMapping("/findAll")
    @ResponseBody
    public List<CartaMenu> findAll() {
        return cartaMenuDAO.findAll();
    }

    // Método que muestra el formulario de registro de carta
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterMenu"; // Nombre del archivo HTML sin la extensión
    }

    // Método que busca una sola carta por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public CartaMenu findById(@PathVariable String id) {
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
    public String mostrarFormularioRegistroItem() {
        return "Pruebas-RegisterItemMenu"; // Nombre del archivo HTML sin la extensión
    }

    // ************************************************** POSTMAPPING ********************************************** */
    // Método que registra una carta
    @PostMapping("/registrarCarta")
    public String registrarCarta(@ModelAttribute CartaMenu cartaMenu, RedirectAttributes redirectAttributes) {
        // Comprobar si 'nombre' no es nulo o vacío
        System.out.println("Carta recibida: " + cartaMenu.toString());
        if (cartaMenu.getNombre() == null || cartaMenu.getNombre().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El nombre de la carta no puede estar vacío");
            return "redirect:/cartas/register";
        }

        CartaMenu cartaMenuRegistrada = cartaMenuDAO.save(cartaMenu);
        String cif = cartaMenuRegistrada.getRestaurante().getCif();
        //System.out.println("Carta registrada: " + cartaMenuRegistrada);
        redirectAttributes.addFlashAttribute("success", "Carta registrada exitosamente.");
        return "redirect:/restaurantes/gestion/" + cif;
    }

    // ************************************************** DELETEMAPPING ********************************************** */
    // Método que elimina una carta
    @DeleteMapping("/eliminarCarta/{id}")
    public ResponseEntity<CartaMenu> eliminarCarta(@PathVariable String id) {
        // Comprobar si la carta existe
        CartaMenu cartaMenu = cartaMenuDAO.findById(id).orElse(null);
        if (cartaMenu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve un error si la carta no existe
        }

        cartaMenuDAO.deleteById(id);
        return new ResponseEntity<>(cartaMenu, HttpStatus.OK);
    }
}
