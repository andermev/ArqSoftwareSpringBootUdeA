package com.udea.example.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udea.example.controller.util.HeaderUtil;
import com.udea.example.entity.Materia;
import com.udea.example.repository.MateriaRepository;

/**
 * REST controller for managing Materia.
 */
@RestController
@RequestMapping("/api")
public class MateriaResource {

	private final Logger log = LoggerFactory.getLogger(MateriaResource.class);

	private static final String ENTITY_NAME = "materia";

	@Autowired
	private MateriaRepository materiaRepository;

	/**
	 * POST /materias : Create a new materia.
	 *
	 * @param materia
	 *            the materia to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new materia, or with status 400 (Bad Request) if the materia has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/materias")
	public ResponseEntity<Materia> createMateria(@RequestBody Materia materia) throws URISyntaxException {
		log.debug("REST request to save Materia : {}", materia);
		if (materia.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new materia cannot already have an ID"))
					.body(null);
		}
		Materia result = materiaRepository.save(materia);
		return ResponseEntity.created(new URI("/api/materias/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /materias : Updates an existing materia.
	 *
	 * @param materia
	 *            the materia to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         materia, or with status 400 (Bad Request) if the materia is not
	 *         valid, or with status 500 (Internal Server Error) if the materia
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/materias")
	public ResponseEntity<Materia> updateMateria(@RequestBody Materia materia) throws URISyntaxException {
		log.debug("REST request to update Materia : {}", materia);
		if (materia.getId() == null) {
			return createMateria(materia);
		}
		Materia result = materiaRepository.save(materia);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materia.getId().toString()))
				.body(result);
	}

	/**
	 * GET /materias : get all the materias.
	 *
	 * @param filter
	 *            the filter of the request
	 * @return the ResponseEntity with status 200 (OK) and the list of materias
	 *         in body
	 */
	@GetMapping("/materias")
	public List<Materia> getAllMaterias(@RequestParam(required = false) String filter) {
		if ("aula-is-null".equals(filter)) {
			log.debug("REST request to get all Materias where aula is null");
			return StreamSupport.stream(materiaRepository.findAll().spliterator(), false)
					.filter(materia -> materia.getAula() == null).collect(Collectors.toList());
		}
		log.debug("REST request to get all Materias");
		List<Materia> materias = materiaRepository.findAll();
		return materias;
	}

	/**
	 * GET /materias/:id : get the "id" materia.
	 *
	 * @param id
	 *            the id of the materia to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         materia, or with status 404 (Not Found)
	 */
	@GetMapping("/materias/{id}")
	public ResponseEntity<Materia> getMateria(@PathVariable Long id) {
		log.debug("REST request to get Materia : {}", id);
		Materia materia = materiaRepository.findOne(id);
		return new ResponseEntity<>(materia, HttpStatus.OK);
	}

	/**
	 * DELETE /materias/:id : delete the "id" materia.
	 *
	 * @param id
	 *            the id of the materia to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/materias/{id}")
	public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
		log.debug("REST request to delete Materia : {}", id);
		materiaRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

}
