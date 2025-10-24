package com.evaluacion.spring.repository;

import com.evaluacion.spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByEmail(String email);

	Optional<Usuario> findById(Integer id);
}