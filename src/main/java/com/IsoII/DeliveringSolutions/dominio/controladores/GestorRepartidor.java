package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Repartidor;
import com.IsoII.DeliveringSolutions.persistencia.RepartidorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;

@Controller
@RequestMapping("/repartidores")
public class GestorRepartidor {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @Autowired
    private RepartidorDAO repartidorDAO;

    // ************************************************** GETMAPPING ********************************************** */

    // Método que devuelve una lista de todos los repartidores
    @GetMapping("/findAll")
    @ResponseBody
    public List<Repartidor> findAll() {
        return repartidorDAO.findAll();
    }

    // Método que muestra el formulario de registro de repartidor
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterRepartidor"; // Nombre del archivo HTML sin la extensión
    }

    // Método que busca un solo repartidor por su id
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Repartidor findById(@PathVariable String id) {
        return repartidorDAO.findById(id).orElse(null);
    }

    // ************************************************** POSTMAPPING ********************************************** */
    // Método que registra un repartidor
    @PostMapping("/registrarRepartidor")
    public ResponseEntity<Repartidor> registrarRepartidor(@ModelAttribute Repartidor repartidor) {
        // Comprobar si 'pass' no es nulo o vacío
        System.out.println("Repartidor recibido: " + repartidor.toString());
        if (repartidor.getPass() == null || repartidor.getPass().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Devuelve un error si 'pass' está vacío
        }
        
        Repartidor repartidorRegistrado = repartidorDAO.save(repartidor);
        System.out.println("Repartidor registrado: " + repartidorRegistrado);
        return new ResponseEntity<>(repartidorRegistrado, HttpStatus.CREATED);
    }
}
