package com.udea.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.example.entity.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long>{
//	List<Aula> obtenerAulasByProfesor(String profesor);
	
}
