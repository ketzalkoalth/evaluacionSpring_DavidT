package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/editar")
	public String editarPerfilForm(Model model) {
		try {
			Usuario usuario = usuarioService.obtenerUsuarioActual();
			model.addAttribute("usuario", usuario);
			return "editar-perfil";
		} catch (Exception e) {
			model.addAttribute("error", "No se pudo cargar la información del usuario");
			return "editar-perfil";
		}
	}

	@PostMapping("/editar")
	public String actualizarPerfil(@RequestParam String nombre, @RequestParam String email,
			@RequestParam String telefono, @RequestParam(required = false) String password,
			RedirectAttributes redirectAttributes) {
		try {
			// Obtener el usuario actual
			Usuario usuario = usuarioService.obtenerUsuarioActual();

			// Actualizar los datos del usuario
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			usuario.setTelefono(telefono);

			// Actualizar contraseña solo si se proporcionó una nueva
			if (password != null && !password.trim().isEmpty()) {
				usuario.setPassword(password);
			}

			// Guardar los cambios
			usuarioService.guardarUsuario(usuario);

			// Redirigir al dashboard con mensaje de éxito
			redirectAttributes.addFlashAttribute("success", "Perfil actualizado exitosamente");
			return "redirect:/dashboard";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
			return "redirect:/usuarios/editar";
		}
	}
}