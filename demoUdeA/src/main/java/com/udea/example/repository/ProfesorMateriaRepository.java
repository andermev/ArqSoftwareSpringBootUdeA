package com.udea.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udea.example.entity.ProfesorMateria;

@Repository("profesorMateriaRepository")
public interface ProfesorMateriaRepository extends JpaRepository<ProfesorMateria, Long> {

	@Transactional
	@Modifying
	@Query(value = "delete from ProfesorMateria e where e.profesorId = :profesorId")
	// @Query(value = "delete from ProfesorMateria e where e.profesor_id =
	// :profesorId", nativeQuery = true)
	void deleteProfesor(@Param("profesorId") Long profesorId);

}
