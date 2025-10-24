package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Cita;
import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	@Qualifier("citaServiceImplement")
	private ICitaService citaService;

	@GetMapping
	public String mostrarDashboard(Model model, HttpSession session) {
		try {
			// Obtener usuario de la sesión
			Usuario usuario = (Usuario) session.getAttribute("usuario");

			if (usuario == null) {
				System.out.println("=== DEBUG: No hay usuario en sesión, redirigiendo al login");
				return "redirect:/login";
			}

			System.out.println("=== DASHBOARD DEBUG ===");
			System.out.println("Usuario ID desde sesión: " + usuario.getId());
			System.out.println("Usuario Nombre: " + usuario.getNombre());

			List<Cita> citas = citaService.obtenerCitasPorUsuario(usuario.getId());
			System.out.println("Total citas en controller: " + citas.size());

			model.addAttribute("usuario", usuario);
			model.addAttribute("citas", citas);

			return "index";
		} catch (Exception e) {
			System.out.println("ERROR en dashboard: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
			return "index";
		}
	}
}