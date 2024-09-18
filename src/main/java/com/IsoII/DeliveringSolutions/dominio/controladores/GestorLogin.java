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

	@GetMapping("/login")
	public String login(Model model) {
		// TODO - implement GestorLogin.login
		model.addAttribute("name", "password");
		return "/login";
	}

	@PostMapping("/login")
	public String loginPost(@ModelAttribute("name") String name, @ModelAttribute("password") String password) {
		System.out.println("name: " + name);
		System.out.println("password: " + password);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "Exito");
		// TODO - implement GestorLogin.loginPost
		return "redirect:/resultadoLogin";
	}

	@GetMapping("/resultadoLogin")
	public String resultadoLogin() {
		return "/resultadoLogin";
	}
}