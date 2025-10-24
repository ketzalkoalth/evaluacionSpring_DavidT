package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Usuario;

public interface IUsuarioService {
	Usuario obtenerUsuarioActual();

	Usuario obtenerUsuarioPorId(Integer id);

	Usuario guardarUsuario(Usuario usuario);

	Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado);

	Usuario findByEmail(String email);
}