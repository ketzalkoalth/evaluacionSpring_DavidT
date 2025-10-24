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
		// Este método ya no será usado para obtener el usuario de sesión
		// Se mantiene por compatibilidad pero debería eliminarse gradualmente
		System.out.println("=== ADVERTENCIA: obtenerUsuarioActual() simula usuario ID 1");
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

		usuarioExistente.setNombre(usuarioActualizado.getNombre());
		usuarioExistente.setEmail(usuarioActualizado.getEmail());
		usuarioExistente.setTelefono(usuarioActualizado.getTelefono());

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