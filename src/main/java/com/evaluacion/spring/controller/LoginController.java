package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/")
	public String mostrarLogin(Model model) {
		return "login";
	}

	@GetMapping("/login")
	public String mostrarLoginPage(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String procesarLogin(@RequestParam String email, @RequestParam String password, Model model) {
		try {
			System.out.println("=== LOGIN ATTEMPT ===");
			System.out.println("Email: " + email);
			System.out.println("Password: " + password);

			// Usar el servicio para autenticar
			if (autenticarUsuario(email, password)) {
				System.out.println("Login exitoso, redirigiendo al dashboard");
				return "redirect:/dashboard";
			} else {
				System.out.println("Credenciales inválidas");
				model.addAttribute("error", "Credenciales inválidas. Por favor, intenta nuevamente.");
				model.addAttribute("email", email); // Mantener el email en el formulario
				return "login";
			}

		} catch (Exception e) {
			System.out.println("Error en login: " + e.getMessage());
			model.addAttribute("error", "Error al procesar el login: " + e.getMessage());
			return "login";
		}
	}

	private boolean autenticarUsuario(String email, String password) {
		try {
			// Autenticación con base de datos
			Usuario usuario = usuarioService.findByEmail(email);
			if (usuario != null && usuario.getPassword().equals(password)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Error en autenticación: " + e.getMessage());
			return false;
		}
	}

	@GetMapping("/registro")
	public String mostrarRegistro(Model model) {
		// Para futura implementación
		model.addAttribute("error", "Registro no implementado aún.");
		return "login";
	}

}