package com.udea.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.example.entity.Profesor;

@Repository("profesorRepository")
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
//	List<Profesor> obtenerProfesoresByMateria(String materia);
}
