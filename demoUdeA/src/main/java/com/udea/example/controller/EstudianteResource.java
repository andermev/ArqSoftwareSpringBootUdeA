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
import com.udea.example.entity.Estudiante;
import com.udea.example.repository.EstudianteMateriaRepository;
import com.udea.example.repository.EstudianteRepository;

/**
 * REST controller for managing Estudiante.
 */
@RestController
@RequestMapping("/api")
public class EstudianteResource {

	private final Logger log = LoggerFactory.getLogger(EstudianteResource.class);

	private static final String ENTITY_NAME = "estudiante";

	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private EstudianteMateriaRepository estudianteMateriaRepository;

	/**
	 * POST /estudiantes : Create a new estudiante.
	 *
	 * @param estudiante
	 *            the estudiante to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new estudiante, or with status 400 (Bad Request) if the
	 *         estudiante has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/estudiantes")
	public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) throws URISyntaxException {
		log.debug("REST request to save Estudiante : {}", estudiante);
		if (estudiante.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
					"A new estudiante cannot already have an ID")).body(null);
		}
		Estudiante result = estudianteRepository.save(estudiante);
		return ResponseEntity.created(new URI("/api/estudiantes/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /estudiantes : Updates an existing estudiante.
	 *
	 * @param estudiante
	 *            the estudiante to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         estudiante, or with status 400 (Bad Request) if the estudiante is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         estudiante couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/estudiantes")
	public ResponseEntity<Estudiante> updateEstudiante(@RequestBody Estudiante estudiante) throws URISyntaxException {
		log.debug("REST request to update Estudiante : {}", estudiante);
		if (estudiante.getId() == null) {
			return createEstudiante(estudiante);
		}
		Estudiante result = estudianteRepository.save(estudiante);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estudiante.getId().toString())).body(result);
	}

	/**
	 * GET /estudiantes : get all the estudiantes.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         estudiantes in body
	 */
	@GetMapping("/estudiantes")
	public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
		log.debug("REST request to get all Estudiantes");
		List<Estudiante> estudiantes = estudianteRepository.findAll();
		return new ResponseEntity<List<Estudiante>>(estudiantes, HttpStatus.OK);
	}

	/**
	 * GET /estudiantes/:id : get the "id" estudiante.
	 *
	 * @param id
	 *            the id of the estudiante to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         estudiante, or with status 404 (Not Found)
	 */
	@GetMapping("/estudiantes/{id}")
	public ResponseEntity<Estudiante> getEstudiante(@PathVariable Long id) {
		log.debug("REST request to get Estudiante : {}", id);
		Estudiante estudiante = estudianteRepository.findOne(id);
		return new ResponseEntity<>(estudiante, HttpStatus.OK);
	}

	/**
	 * DELETE /estudiantes/:id : delete the "id" estudiante.
	 *
	 * @param id
	 *            the id of the estudiante to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/estudiantes/{id}")
	public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
		log.debug("REST request to delete Estudiante : {}", id);
		estudianteMateriaRepository.deleteEstudiante(id);
		estudianteRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * GET /estudiantes/course/:nameCourse : get the estudiantes related with a
	 * course.
	 *
	 * @param nameCourse
	 *            the name of course to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         estudiantes, or with status 404 (Not Found)
	 */
	@GetMapping("/estudiantes/course/{nameCourse}")
	public ResponseEntity<List<Estudiante>> getEstudiantesByMateria(@PathVariable String nameCourse) {
		log.debug("REST request to get course related with the estudiantes : {}", nameCourse);
		List<Estudiante> estudiantes = estudianteRepository.cantidadEstudiantesByMateria(nameCourse);
		return new ResponseEntity<>(estudiantes, HttpStatus.OK);
	}

}
