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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public String crearCita(@RequestParam Integer servicioId,
                          @RequestParam Integer profesionalId,
                          @RequestParam String fechaHora,
                          Model model) {
        try {
            // Obtener el usuario actual (simulado como ID 1)
            Usuario usuario = usuarioService.obtenerUsuarioActual();
            
            // Obtener servicio y profesional
            Servicio servicio = servicioService.obtenerServicioPorId(servicioId);
            Profesional profesional = profesionalService.obtenerProfesionalPorId(profesionalId);
            
            // Convertir String a LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime fechaHoraLocal = LocalDateTime.parse(fechaHora, formatter);
            
            // Crear nueva cita
            Cita nuevaCita = new Cita();
            nuevaCita.setFechaHora(fechaHoraLocal);
            nuevaCita.setEstado("PENDIENTE");
            nuevaCita.setUsuario(usuario);
            nuevaCita.setServicio(servicio);
            nuevaCita.setProfesional(profesional);
            
            // Guardar en base de datos
            citaService.guardarCita(nuevaCita);
            
            // Redirigir al dashboard con mensaje de éxito
            return "redirect:/dashboard?success=Cita agendada exitosamente";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al agendar la cita: " + e.getMessage());
            return "nueva-cita";
        }
    }

    @GetMapping("/historial")
    public String verHistorial() {
        return "historial-citas";
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