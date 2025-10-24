package com.evaluacion.spring.repository;

import com.evaluacion.spring.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfesionalRepository extends JpaRepository<Profesional, Integer> {
}