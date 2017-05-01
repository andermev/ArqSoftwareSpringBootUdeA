package com.udea.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udea.example.entity.Profesor;

@Repository("profesorRepository")
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "select DISTINCT p.id, p.nombres, p.apellidos, p.carrera "
			+ "from profesor p "
			+ "inner join profesor_materia pm on pm.profesor_id = p.id "
			+ "inner join materia m on m.id = pm.materia_id "
			+ "where m.nombre = :nombreMateria", 
			nativeQuery = true)
	List<Profesor> cantidadProfesoresByMateria(@Param("nombreMateria") String nombreMateria);
	
}
