package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class ApiUsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	// GET /api/usuarios/1
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
		try {
			Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
			if (usuario == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Usuario no encontrado");
				return ResponseEntity.status(404).body(error);
			}

			return ResponseEntity.ok(usuario);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al obtener usuario: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// PUT /api/usuarios/1
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
		try {
			Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
			if (usuarioExistente == null) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "Usuario no encontrado");
				return ResponseEntity.status(404).body(error);
			}

			// Actualizar campos
			usuarioExistente.setNombre(usuarioActualizado.getNombre());
			usuarioExistente.setEmail(usuarioActualizado.getEmail());
			usuarioExistente.setTelefono(usuarioActualizado.getTelefono());

			// Solo actualizar contraseña si se proporcionó
			if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
				usuarioExistente.setPassword(usuarioActualizado.getPassword());
			}

			Usuario usuarioGuardado = usuarioService.guardarUsuario(usuarioExistente);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Usuario actualizado exitosamente");
			response.put("usuario", usuarioGuardado);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al actualizar usuario: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	// GET /api/usuarios/actual
	@GetMapping("/actual")
	public ResponseEntity<?> obtenerUsuarioActual() {
		try {
			Usuario usuario = usuarioService.obtenerUsuarioActual();
			return ResponseEntity.ok(usuario);

		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al obtener usuario actual: " + e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}
}