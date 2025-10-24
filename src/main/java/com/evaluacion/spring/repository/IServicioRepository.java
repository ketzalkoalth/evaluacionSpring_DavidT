package com.evaluacion.spring.repository;

import com.evaluacion.spring.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer> {
}