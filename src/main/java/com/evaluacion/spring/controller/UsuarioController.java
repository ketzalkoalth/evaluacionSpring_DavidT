package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}