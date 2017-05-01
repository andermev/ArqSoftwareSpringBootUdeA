package com.udea.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udea.example.entity.EstudianteMateria;

@Repository("estudianteMateriaRepository")
public interface EstudianteMateriaRepository extends JpaRepository<EstudianteMateria, Long>{
	
	@Query(value = "delete from EstudianteMateria e where e.estudianteId = :estudianteId")
	void deleteEstudiante(@Param("estudianteId") Long estudianteId);
	
}
