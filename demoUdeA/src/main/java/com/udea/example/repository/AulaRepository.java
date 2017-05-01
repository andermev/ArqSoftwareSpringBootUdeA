package com.udea.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.example.entity.Aula;

@Repository("aulaRepository")
public interface AulaRepository extends JpaRepository<Aula, Long>{
	
}
