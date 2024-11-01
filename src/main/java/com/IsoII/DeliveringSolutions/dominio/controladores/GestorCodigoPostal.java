package com.IsoII.DeliveringSolutions.dominio.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.IsoII.DeliveringSolutions.dominio.entidades.CodigoPostal;
import com.IsoII.DeliveringSolutions.dominio.service.ServiceCodigoPostal;
import com.IsoII.DeliveringSolutions.persistencia.CodigoPostalDAO;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/codigoPostal")
public class GestorCodigoPostal {
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
    
    @Autowired
    private CodigoPostalDAO codigoPostalDAO;

    @Autowired
    private ServiceCodigoPostal serviceCodigoPostal;

    @GetMapping("/findAll")
    @ResponseBody
    public List<CodigoPostal> findAll() {
        return codigoPostalDAO.findAll();
    }

    @GetMapping("/mostrarCodigos")
    public String mostrarCodigosPostales(Model model) {
        List<CodigoPostal> codigosPostales = serviceCodigoPostal.findAll();
        model.addAttribute("codigosPostales", codigosPostales);
        return "/administrador/ListaCodigoPostales"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "Pruebas-RegisterCodigoPostal"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public CodigoPostal findById(@PathVariable Integer id) {
        return codigoPostalDAO.findById(id).orElse(null);
    }

    @PostMapping("/registrarCodigoPostal")
    public ResponseEntity<CodigoPostal> registrarCodigoPostal(@ModelAttribute CodigoPostal codigoPostal) {
        System.out.println("CodigoPostal recibido: " + codigoPostal.toString());
        if (codigoPostal.getCodigo() == null || codigoPostal.getCodigo().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CodigoPostal codigoPostalRegistrado = codigoPostalDAO.save(codigoPostal);
        System.out.println("CodigoPostal registrado: " + codigoPostalRegistrado);
        return new ResponseEntity<>(codigoPostalRegistrado, HttpStatus.CREATED);
    }
    
}