package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Profesional;
import java.util.List;

public interface IProfesionalService {
    List<Profesional> obtenerTodosLosProfesionales();
    Profesional obtenerProfesionalPorId(Integer id);
}