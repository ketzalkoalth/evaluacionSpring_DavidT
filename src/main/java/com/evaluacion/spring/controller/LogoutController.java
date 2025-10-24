package com.evaluacion.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("=== LOGOUT: Cerrando sesión ===");
        
        // Invalidar la sesión
        if (session != null) {
            session.invalidate();
        }
        
        return "redirect:/login?logout";
    }
}