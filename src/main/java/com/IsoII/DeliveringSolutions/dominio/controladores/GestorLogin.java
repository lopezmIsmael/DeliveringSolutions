package com.IsoII.DeliveringSolutions.dominio.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
public class GestorLogin {
	RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

	@GetMapping("/")
    public String index() {
        return "index"; // Busca index.html en src/main/resources/templates
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs"; // Busca aboutUs.html en src/main/resources/templates
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Busca register.html en src/main/resources/templates
    }
}