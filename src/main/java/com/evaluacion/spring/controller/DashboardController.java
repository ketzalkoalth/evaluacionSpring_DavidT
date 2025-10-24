package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Cita;
import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.ICitaService;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	@Qualifier("citaServiceImplement") // ← AGREGA ESTA ANOTACIÓN
	private ICitaService citaService;

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping
	public String mostrarDashboard(Model model) {
		try {
			Usuario usuario = usuarioService.obtenerUsuarioActual();
			System.out.println("=== DASHBOARD DEBUG ===");
			System.out.println("Usuario ID: " + usuario.getId());
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