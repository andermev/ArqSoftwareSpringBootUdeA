package com.udea.example.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.udea.example.controller.util.HeaderUtil;
import com.udea.example.entity.Aula;
import com.udea.example.repository.AulaRepository;

/**
 * REST controller for managing Aula.
 */
@Controller
@RequestMapping("/api")
public class AulaResource {

	private final Logger log = LoggerFactory.getLogger(AulaResource.class);

	private static final String ENTITY_NAME = "aula";

	@Autowired
	private AulaRepository aulaRepository;

	/**
	 * POST /aulas : Create a new aula.
	 *
	 * @param aula
	 *            the aula to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new aula, or with status 400 (Bad Request) if the aula has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	// @PostMapping("/aulas")
	@RequestMapping(value = "/aulas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Aula> createAula(@RequestBody Aula aula) throws URISyntaxException {
		log.debug("REST request to save Aula : {}", aula);
		if (aula.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aula cannot already have an ID"))
					.body(null);
		}
		Aula result = aulaRepository.save(aula);
		return ResponseEntity.created(new URI("/api/aulas/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /aulas : Updates an existing aula.
	 *
	 * @param aula
	 *            the aula to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         aula, or with status 400 (Bad Request) if the aula is not valid,
	 *         or with status 500 (Internal Server Error) if the aula couldnt be
	 *         updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/aulas")
	public ResponseEntity<Aula> updateAula(@RequestBody Aula aula) throws URISyntaxException {
		log.debug("REST request to update Aula : {}", aula);
		if (aula.getId() == null) {
			return createAula(aula);
		}
		Aula result = aulaRepository.save(aula);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aula.getId().toString()))
				.body(result);
	}

	/**
	 * GET /aulas : get all the aulas.
	 *
	 * @param filter
	 *            the filter of the request
	 * @return the ResponseEntity with status 200 (OK) and the list of aulas in
	 *         body
	 */
	// @GetMapping("aulas")
	@RequestMapping(value = "/aulas", method = RequestMethod.GET)
	public ResponseEntity<List<Aula>> getAulas() {
		List<Aula> aulas = aulaRepository.findAll();
		return new ResponseEntity<List<Aula>>(aulas, HttpStatus.OK);
	}

	/**
	 * GET /aulas/:id : get the "id" aula.
	 *
	 * @param id
	 *            the id of the aula to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the aula,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/aulas/{id}")
	public ResponseEntity<Aula> getAula(@PathVariable Long id) {
		log.debug("REST request to get Aula : {}", id);
		Aula aula = aulaRepository.findOne(id);
		return new ResponseEntity<>(aula, HttpStatus.OK);
	}

	/**
	 * DELETE /aulas/:id : delete the "id" aula.
	 *
	 * @param id
	 *            the id of the aula to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/aulas/{id}")
	public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
		log.debug("REST request to delete Aula : {}", id);
		aulaRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

}
