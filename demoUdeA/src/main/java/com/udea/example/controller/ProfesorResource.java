package com.udea.example.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.example.controller.util.HeaderUtil;
import com.udea.example.entity.Profesor;
import com.udea.example.repository.ProfesorMateriaRepository;
import com.udea.example.repository.ProfesorRepository;

/**
 * REST controller for managing Profesor.
 */
@RestController
@RequestMapping("/api")
public class ProfesorResource {

	private final Logger log = LoggerFactory.getLogger(ProfesorResource.class);

	private static final String ENTITY_NAME = "profesor";

	@Autowired
	private ProfesorRepository profesorRepository;

	@Autowired
	private ProfesorMateriaRepository profesorMateriaRepository;

	/**
	 * POST /profesors : Create a new profesor.
	 *
	 * @param profesor
	 *            the profesor to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new profesor, or with status 400 (Bad Request) if the profesor
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/profesors")
	public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor profesor) throws URISyntaxException {
		log.debug("REST request to save Profesor : {}", profesor);
		if (profesor.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profesor cannot already have an ID"))
					.body(null);
		}
		Profesor result = profesorRepository.save(profesor);
		return ResponseEntity.created(new URI("/api/profesors/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /profesors : Updates an existing profesor.
	 *
	 * @param profesor
	 *            the profesor to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         profesor, or with status 400 (Bad Request) if the profesor is not
	 *         valid, or with status 500 (Internal Server Error) if the profesor
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/profesors")
	public ResponseEntity<Profesor> updateProfesor(@RequestBody Profesor profesor) throws URISyntaxException {
		log.debug("REST request to update Profesor : {}", profesor);
		if (profesor.getId() == null) {
			return createProfesor(profesor);
		}
		Profesor result = profesorRepository.save(profesor);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profesor.getId().toString()))
				.body(result);
	}

	/**
	 * GET /profesors : get all the profesors.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of profesors
	 *         in body
	 */
	@GetMapping("/profesors")
	public ResponseEntity<List<Profesor>> getAllEstudiantes() {
		log.debug("REST request to get all Profesors");
		List<Profesor> profesors = profesorRepository.findAll();
		return new ResponseEntity<List<Profesor>>(profesors, HttpStatus.OK);
	}

	/**
	 * GET /profesors/:id : get the "id" profesor.
	 *
	 * @param id
	 *            the id of the profesor to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         profesor, or with status 404 (Not Found)
	 */
	@GetMapping("/profesors/{id}")
	public ResponseEntity<Profesor> getProfesor(@PathVariable Long id) {
		log.debug("REST request to get Profesor : {}", id);
		Profesor profesor = profesorRepository.findOne(id);
		return new ResponseEntity<>(profesor, HttpStatus.OK);
	}

	/**
	 * DELETE /profesors/:id : delete the "id" profesor.
	 *
	 * @param id
	 *            the id of the profesor to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/profesors/{id}")
	public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
		log.debug("REST request to delete Profesor : {}", id);
		profesorMateriaRepository.deleteProfesor(id);
		profesorRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

}
