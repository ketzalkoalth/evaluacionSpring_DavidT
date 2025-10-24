package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Cita;
import com.evaluacion.spring.model.Servicio;
import com.evaluacion.spring.model.Profesional;
import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.ICitaService;
import com.evaluacion.spring.service.IServicioService;
import com.evaluacion.spring.service.IProfesionalService;
import com.evaluacion.spring.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaController {

	@Autowired
	private ICitaService citaService;

	@Autowired
	private IServicioService servicioService;

	@Autowired
	private IProfesionalService profesionalService;

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/nueva")
	public String nuevaCitaForm(Model model) {
		// Agregar datos necesarios para el formulario
		model.addAttribute("servicios", servicioService.obtenerTodosLosServicios());
		model.addAttribute("profesionales", profesionalService.obtenerTodosLosProfesionales());
		return "nueva-cita";
	}

	@PostMapping("/nueva")
	public String crearCita(@RequestParam Integer servicioId, @RequestParam Integer profesionalId,
			@RequestParam String fechaHora, Model model, HttpSession session) {
		try {
			// Obtener el usuario actual desde la sesión
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			if (usuario == null) {
				return "redirect:/login";
			}

			// Resto del código igual...
			Servicio servicio = servicioService.obtenerServicioPorId(servicioId);
			Profesional profesional = profesionalService.obtenerProfesionalPorId(profesionalId);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			LocalDateTime fechaHoraLocal = LocalDateTime.parse(fechaHora, formatter);

			Cita nuevaCita = new Cita();
			nuevaCita.setFechaHora(fechaHoraLocal);
			nuevaCita.setEstado("PENDIENTE");
			nuevaCita.setUsuario(usuario); // Usar usuario de sesión
			nuevaCita.setServicio(servicio);
			nuevaCita.setProfesional(profesional);

			citaService.guardarCita(nuevaCita);

			return "redirect:/dashboard?success=Cita agendada exitosamente";

		} catch (Exception e) {
			model.addAttribute("error", "Error al agendar la cita: " + e.getMessage());
			return "nueva-cita";
		}
	}

	@GetMapping("/historial")
	public String verHistorial(Model model) {
		try {
			Usuario usuario = usuarioService.obtenerUsuarioActual();
			System.out.println("=== HISTORIAL DEBUG: Usuario ID: " + usuario.getId());

			List<Cita> citasHistorial = citaService.obtenerCitasPorUsuario(usuario.getId());
			System.out.println("=== HISTORIAL DEBUG: Citas encontradas: " + citasHistorial.size());

			// Debug detallado
			for (Cita cita : citasHistorial) {
				System.out.println("Cita ID: " + cita.getId() + ", Estado: " + cita.getEstado() + ", Servicio: "
						+ (cita.getServicio() != null ? cita.getServicio().getNombre() : "null") + ", Fecha: "
						+ cita.getFechaHora());
			}

			model.addAttribute("citas", citasHistorial);
			return "historial-citas";

		} catch (Exception e) {
			System.out.println("ERROR en historial: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error al cargar el historial: " + e.getMessage());
			return "historial-citas";
		}
	}

	@PostMapping("/{id}/cancelar")
	@ResponseBody
	public ResponseEntity<?> cancelarCita(@PathVariable Integer id) {
		try {
			citaService.eliminarCita(id);
			return ResponseEntity.ok().body("{\"message\": \"Cita cancelada exitosamente\"}");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("{\"error\": \"Error al cancelar la cita\"}");
		}
	}
}