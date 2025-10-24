package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

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
	public String procesarLogin(@RequestParam String email, @RequestParam String password, Model model,
			HttpSession session) {
		try {
			System.out.println("=== LOGIN ATTEMPT ===");
			System.out.println("Email: " + email);
			System.out.println("Password: " + password);

			// Autenticar usuario
			Usuario usuario = autenticarUsuario(email, password);
			if (usuario != null) {
				System.out.println("Login exitoso, usuario ID: " + usuario.getId());

				// Guardar usuario en sesión
				session.setAttribute("usuario", usuario);
				session.setAttribute("usuarioId", usuario.getId());

				System.out.println("Sesión creada para usuario ID: " + usuario.getId());
				return "redirect:/dashboard";
			} else {
				System.out.println("Credenciales inválidas");
				model.addAttribute("error", "Credenciales inválidas. Por favor, intenta nuevamente.");
				model.addAttribute("email", email);
				return "login";
			}

		} catch (Exception e) {
			System.out.println("Error en login: " + e.getMessage());
			model.addAttribute("error", "Error al procesar el login: " + e.getMessage());
			return "login";
		}
	}

	private Usuario autenticarUsuario(String email, String password) {
		try {
			// Autenticación con base de datos
			Usuario usuario = usuarioService.findByEmail(email);
			if (usuario != null && usuario.getPassword().equals(password)) {
				return usuario;
			}

			// Demo users para testing
			if ("juan@email.com".equals(email) && "password123".equals(password)) {
				return usuarioService.obtenerUsuarioPorId(1); // Juan Pérez
			}
			if ("maria@email.com".equals(email) && "password123".equals(password)) {
				return usuarioService.obtenerUsuarioPorId(2); // María Gómez
			}
			if ("carlos@email.com".equals(email) && "password123".equals(password)) {
				return usuarioService.obtenerUsuarioPorId(3); // Carlos López
			}

			return null;
		} catch (Exception e) {
			System.out.println("Error en autenticación: " + e.getMessage());
			return null;
		}
	}

	@GetMapping("/registro")
	public String mostrarRegistro(Model model) {
		model.addAttribute("error", "Registro no implementado aún.");
		return "login";
	}
}