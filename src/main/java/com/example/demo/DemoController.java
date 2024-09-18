package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String index(Model model) {
        // Puedes añadir atributos al modelo si es necesario
        return "index"; // Nombre de la vista sin la extensión .html
    }
}
