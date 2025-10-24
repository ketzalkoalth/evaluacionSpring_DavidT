package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Servicio;
import com.evaluacion.spring.repository.IServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImplement implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepository;

    @Override
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtenerServicioPorId(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }
}