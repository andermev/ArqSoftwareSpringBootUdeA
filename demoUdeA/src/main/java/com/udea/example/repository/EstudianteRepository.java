package com.udea.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udea.example.entity.Estudiante;

@Repository("estudianteRepository")
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "select DISTINCT e.id, e.nombres, e.apellidos, e.carrera "
			+ "from estudiante e "
			+ "inner join estudiante_materia em on em.estudiante_id = e.id "
			+ "inner join materia m on m.id = em.materia_id "
			+ "where m.nombre = :nombreMateria", 
			nativeQuery = true)
	List<Estudiante> cantidadEstudiantesByMateria(@Param("nombreMateria") String nombreMateria);
	
}
