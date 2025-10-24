package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Usuario obtenerUsuarioActual() {
		// En una aplicación real, esto obtendría el usuario de la sesión
		// Por ahora simulamos con ID 1
		System.out.println("=== DEBUG: Obteniendo usuario actual (ID 1)");
		return usuarioRepository.findById(1).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
		Usuario usuarioExistente = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		// Actualizar campos
		usuarioExistente.setNombre(usuarioActualizado.getNombre());
		usuarioExistente.setEmail(usuarioActualizado.getEmail());
		usuarioExistente.setTelefono(usuarioActualizado.getTelefono());

		// Solo actualizar contraseña si se proporcionó una nueva
		if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
			usuarioExistente.setPassword(usuarioActualizado.getPassword());
		}

		return usuarioRepository.save(usuarioExistente);
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
}