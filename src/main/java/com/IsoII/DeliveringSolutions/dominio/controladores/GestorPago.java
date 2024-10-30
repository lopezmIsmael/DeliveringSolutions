package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import com.IsoII.DeliveringSolutions.persistencia.PagoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.util.List;

@Controller
@RequestMapping("/pago")
public class GestorPago {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
    
    @Autowired
    private PagoDAO pagoDAO;

    @GetMapping("/findAll")
    @ResponseBody
    public List<Pago> findAll() {
        return pagoDAO.findAll();
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "RegistrarPedidos"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public Pago findById(@PathVariable Integer id) {
        return pagoDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarPago")
    public ResponseEntity<Pago> registrarPago(@ModelAttribute Pago pago) {
        System.out.println("Pago recibido: " + pago.toString());
        if (pago.getMetodoPago() == null || pago.getMetodoPago().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pago pagoRegistrado = pagoDAO.save(pago);
        System.out.println("Pago registrado: " + pagoRegistrado);
        return new ResponseEntity<>(pagoRegistrado, HttpStatus.CREATED);
    }
    
}