package com.IsoII.DeliveringSolutions.dominio.controladores;

import com.IsoII.DeliveringSolutions.dominio.entidades.ItemMenu;
import com.IsoII.DeliveringSolutions.dominio.entidades.Pago;
import com.IsoII.DeliveringSolutions.persistencia.PagoDAO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

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

    // In GestorPago.java, update the mapping annotation
// In GestorPago.java
@PostMapping("/register")
public String mostrarFormularioRegistro(@RequestParam("cartData") String cartData, Model model) {
    System.out.println("<<ESTOY EN REGISTER: GestorPago>>");
    System.out.println("<<CartData>>: " + cartData);

    // Create ObjectMapper and configure it
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    List<ItemMenu> carrito = new ArrayList<>();

    try {
        carrito = objectMapper.readValue(cartData, new TypeReference<List<ItemMenu>>() {});
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println("<<Carrito size>>: " + carrito.size());
    for (ItemMenu item : carrito) {
        System.out.println("<<Item>>: " + item.getNombre() + ", Precio: " + item.getPrecio());
    }

    double totalPrice = 0;
    for (ItemMenu item : carrito) {
        totalPrice += item.getPrecio();
    }
    System.out.println("<<Total Price>>: " + totalPrice);

    model.addAttribute("carrito", carrito);
    model.addAttribute("total", totalPrice);

    return "RegistrarPedidos";
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