package com.udea.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udea.example.entity.Aula;
import com.udea.example.repository.AulaRepository;

/**	
 * REST controller for managing Aula.
 */
@Controller
@RequestMapping("/api")
public class AulaResource {

    private final Logger log = LoggerFactory.getLogger(AulaResource.class);

    @Autowired
	private AulaRepository aulaRepository;
    
	@GetMapping("aulas")
	public ResponseEntity<List<Aula>> getAulas() {
	    List<Aula> aulas = aulaRepository.findAll();
	    return new ResponseEntity<List<Aula>>(aulas, HttpStatus.OK);
	}


}
