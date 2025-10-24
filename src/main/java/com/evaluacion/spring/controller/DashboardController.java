package com.evaluacion.spring.controller;

import com.evaluacion.spring.model.Cita;
import com.evaluacion.spring.model.Usuario;
import com.evaluacion.spring.service.ICitaService;
import com.evaluacion.spring.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ICitaService citaService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public String mostrarDashboard(Model model, 
                                 @RequestParam(value = "success", required = false) String success) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioActual();
            List<Cita> citas = citaService.obtenerCitasPorUsuario(usuario.getId());
            
            model.addAttribute("usuario", usuario);
            model.addAttribute("citas", citas);
            
            // Agregar mensaje de éxito si existe
            if (success != null) {
                model.addAttribute("success", success);
            }
            
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
            return "index";
        }
    }
}