package com.evaluacion.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

	@GetMapping("/logout")
	public String logout() {
		// En una aplicación real, aquí invalidarías la sesión
		// Por ahora redirigimos al login con mensaje de logout
		return "redirect:/login?logout";
	}
}