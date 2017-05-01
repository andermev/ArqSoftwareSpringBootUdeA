package com.udea.example.web.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.udea.example.DemoUdeAApplication;
import com.udea.example.controller.EstudianteResource;
import com.udea.example.repository.EstudianteMateriaRepository;
import com.udea.example.repository.EstudianteRepository;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoUdeAApplication.class)
public class EstudianteResourceIntTest {

	@Inject
	private EstudianteRepository estudianteRepository;

	@Inject
	private EstudianteMateriaRepository estudianteMateriaRepository;

	private MockMvc restEstudianteMockMvc;

	@Before
	public void setup() {
		EstudianteResource estudianteResource = new EstudianteResource();
		ReflectionTestUtils.setField(estudianteResource, "estudianteRepository", estudianteRepository);
		ReflectionTestUtils.setField(estudianteResource, "estudianteMateriaRepository", estudianteMateriaRepository);
		this.restEstudianteMockMvc = MockMvcBuilders.standaloneSetup(estudianteResource).build();
	}

	@Test
	public void testGetExistingEstudiantes() throws Exception {
		restEstudianteMockMvc.perform(get("/api/estudiantes")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].nombres").value(hasItem("Ana")))
				.andExpect(jsonPath("$.[*].apellidos").value(hasItem("Agudelo")))
				.andExpect(jsonPath("$.[*].carrera").value(hasItem("ING SISTEMAS")));
	}

	@Test
	public void testGetExistingEstudiante() throws Exception {
		restEstudianteMockMvc.perform(get("/api/estudiantes/{id}", 5)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombres").value("Harold"))
				.andExpect(jsonPath("$.apellidos").value("Castañeda"))
				.andExpect(jsonPath("$.carrera").value("ING SISTEMAS"));
	}
	
	@Test
	public void testNotExistingEstudiante() throws Exception {
		restEstudianteMockMvc.perform(get("/api/estudiantes/{id}", 2)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombres").doesNotExist())
				.andExpect(jsonPath("$.apellidos").doesNotExist())
				.andExpect(jsonPath("$.carrera").doesNotExist());
	}
	
	@Test
	public void testGetExistingEstudianteByMateria() throws Exception {
		restEstudianteMockMvc.perform(get("/api/estudiantes/course/{nameCourse}", "BASE DE DATOS")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nombres").value(hasItem("Juan")))
				.andExpect(jsonPath("$.[*].apellidos").value(hasItem("Andrade")))
				.andExpect(jsonPath("$.[*].carrera").value(hasItem("ING SISTEMAS")));
	}

}
