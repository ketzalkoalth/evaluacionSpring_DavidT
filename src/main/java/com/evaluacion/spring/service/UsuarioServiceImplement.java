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
        // En una aplicación real, esto obtendría el usuario de la sesión o contexto de seguridad
        return usuarioRepository.findById(1).orElseThrow(() -> 
            new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}