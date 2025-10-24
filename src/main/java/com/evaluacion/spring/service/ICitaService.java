package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Cita;
import java.util.List;

public interface ICitaService {
    List<Cita> obtenerCitasPorUsuario(Integer usuarioId);
    List<Cita> obtenerTodasLasCitas();
    Cita guardarCita(Cita cita);
    void eliminarCita(Integer id);
}