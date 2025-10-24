package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Cita;
import com.evaluacion.spring.model.Servicio;
import com.evaluacion.spring.model.Profesional;
import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.ICitaService;
import com.evaluacion.spring.service.IServicioService;
import com.evaluacion.spring.service.IProfesionalService;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
public class ApiCitaController {

	@Autowired
	private ICitaService citaService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IServicioService servicioService;

	@Autowired
	private IProfesionalService profesionalService;

	// GET /api/citas/usuario/1
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> obtenerCitasPorUsuario(@PathVariable Integer usuarioId) {
		try {
			List<Cita> citas = citaService.obtenerCitasPorUsuario(usuarioId);

			Map<String, Object> response = new HashMap<>();
			response.put("citas", citas);
			response.put("total", citas.size());

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al obtener citas: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// GET /api/citas
	@GetMapping
	public ResponseEntity<?> obtenerTodasLasCitas() {
		try {
			List<Cita> citas = citaService.obtenerTodasLasCitas();

			Map<String, Object> response = new HashMap<>();
			response.put("citas", citas);
			response.put("total", citas.size());

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al obtener citas: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// GET /api/citas/1
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerCitaPorId(@PathVariable Integer id) {
		try {
			Cita cita = citaService.obtenerCitasPorUsuario(id).stream().filter(c -> c.getId().equals(id)).findFirst()
					.orElse(null);

			if (cita == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Cita no encontrada");
				return ResponseEntity.status(404).body(error);
			}

			return ResponseEntity.ok(cita);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al obtener cita: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// POST /api/citas
	@PostMapping
	public ResponseEntity<?> crearCita(@RequestBody Map<String, Object> citaData) {
		try {
			Integer usuarioId = (Integer) citaData.get("usuarioId");
			Integer servicioId = (Integer) citaData.get("servicioId");
			Integer profesionalId = (Integer) citaData.get("profesionalId");
			String fechaHoraStr = (String) citaData.get("fechaHora");

			// Validar datos requeridos
			if (usuarioId == null || servicioId == null || profesionalId == null || fechaHoraStr == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Datos incompletos. Se requieren: usuarioId, servicioId, profesionalId, fechaHora");
				return ResponseEntity.badRequest().body(error);
			}

			Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
			Servicio servicio = servicioService.obtenerServicioPorId(servicioId);
			Profesional profesional = profesionalService.obtenerProfesionalPorId(profesionalId);

			if (usuario == null || servicio == null || profesional == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Usuario, servicio o profesional no encontrado");
				return ResponseEntity.badRequest().body(error);
			}

			LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr);

			// Validar que la fecha sea futura
			if (fechaHora.isBefore(LocalDateTime.now())) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "La fecha y hora deben ser futuras");
				return ResponseEntity.badRequest().body(error);
			}

			Cita nuevaCita = new Cita();
			nuevaCita.setFechaHora(fechaHora);
			nuevaCita.setEstado("PENDIENTE");
			nuevaCita.setUsuario(usuario);
			nuevaCita.setServicio(servicio);
			nuevaCita.setProfesional(profesional);

			Cita citaGuardada = citaService.guardarCita(nuevaCita);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Cita creada exitosamente");
			response.put("cita", citaGuardada);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al crear cita: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// DELETE /api/citas/1
	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelarCita(@PathVariable Integer id) {
		try {
			// Verificar que la cita existe
			Cita cita = citaService.obtenerCitasPorUsuario(1).stream().filter(c -> c.getId().equals(id)).findFirst()
					.orElse(null);

			if (cita == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Cita no encontrada");
				return ResponseEntity.status(404).body(error);
			}

			citaService.eliminarCita(id);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Cita cancelada exitosamente");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al cancelar cita: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// PUT /api/citas/1/estado
	@PutMapping("/{id}/estado")
	public ResponseEntity<?> actualizarEstado(@PathVariable Integer id, @RequestBody Map<String, String> estadoData) {
		try {
			String nuevoEstado = estadoData.get("estado");

			if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "El estado es requerido");
				return ResponseEntity.badRequest().body(error);
			}

			// Buscar la cita (necesitarías un método en el servicio para obtener cita por
			// ID)
			Cita cita = citaService.obtenerCitasPorUsuario(1).stream().filter(c -> c.getId().equals(id)).findFirst()
					.orElse(null);

			if (cita == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Cita no encontrada");
				return ResponseEntity.status(404).body(error);
			}

			cita.setEstado(nuevoEstado.toUpperCase());
			Cita citaActualizada = citaService.guardarCita(cita);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Estado actualizado exitosamente");
			response.put("cita", citaActualizada);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al actualizar estado: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}
}