package com.evaluacion.spring.repository;

import com.evaluacion.spring.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICitaRepository extends JpaRepository<Cita, Integer> {
    
    @Query("SELECT c FROM Cita c WHERE c.usuario.id = :usuarioId")
    List<Cita> findByUsuarioId(Integer usuarioId);
    
    List<Cita> findByEstado(String estado);
}