package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Cita;
import com.evaluacion.spring.repository.ICitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImplement implements ICitaService {

    @Autowired
    private ICitaRepository citaRepository;

    @Override
    public List<Cita> obtenerCitasPorUsuario(Integer usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarCita(Integer id) {
        citaRepository.deleteById(id);
    }
}