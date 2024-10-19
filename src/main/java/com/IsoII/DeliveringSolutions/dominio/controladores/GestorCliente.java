package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.Cliente;
import com.IsoII.DeliveringSolutions.dominio.entidades.Restaurante;
import com.IsoII.DeliveringSolutions.persistencia.ClienteDAO;
import com.IsoII.DeliveringSolutions.persistencia.RestauranteDAO;

@Controller
@RequestMapping("/clientes")
public class GestorCliente {

    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private RestauranteDAO RestauranteDAO;

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
    public String verRestaurantes(Model model) {
        List<Restaurante> restaurantes = RestauranteDAO.findAll();
        model.addAttribute("restaurantes", restaurantes);
        return "verRestaurantes";
    }

    // ************************************************** POSTMAPPING
    // ********************************************** */
    // Método que registra un cliente
    @PostMapping("/registrarCliente")
    public ResponseEntity<Cliente> registrarCliente(@ModelAttribute Cliente cliente) {
        // Comprobar si 'pass' no es nulo o vacío
        System.out.println("Cliente recibido: " + cliente.toString());
        if (cliente.getPass() == null || cliente.getPass().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Devuelve un error si 'pass' está vacío
        }

        Cliente clienteRegistrado = clienteDAO.save(cliente);
        System.out.println("Cliente registrado: " + clienteRegistrado);
        return new ResponseEntity<>(clienteRegistrado, HttpStatus.CREATED);
    }

    // Método que elimina un cliente por su id
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        clienteDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
