package com.evaluacion.spring.service;

import com.evaluacion.spring.model.Profesional;
import com.evaluacion.spring.repository.IProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalServiceImplement implements IProfesionalService {

    @Autowired
    private IProfesionalRepository profesionalRepository;

    @Override
    public List<Profesional> obtenerTodosLosProfesionales() {
        return profesionalRepository.findAll();
    }

    @Override
    public Profesional obtenerProfesionalPorId(Integer id) {
        return profesionalRepository.findById(id).orElse(null);
    }
}