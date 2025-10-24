package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Servicio;
import java.util.List;

public interface IServicioService {
    List<Servicio> obtenerTodosLosServicios();
    Servicio obtenerServicioPorId(Integer id);
}