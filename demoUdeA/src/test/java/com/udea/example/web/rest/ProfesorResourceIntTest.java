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
import com.udea.example.controller.ProfesorResource;
import com.udea.example.repository.ProfesorMateriaRepository;
import com.udea.example.repository.ProfesorRepository;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoUdeAApplication.class)
public class ProfesorResourceIntTest {

	@Inject
	private ProfesorRepository profesorRepository;

	@Inject
	private ProfesorMateriaRepository profesorMateriaRepository;

	private MockMvc restProfesorMockMvc;

	@Before
	public void setup() {
		ProfesorResource profesorResource = new ProfesorResource();
		ReflectionTestUtils.setField(profesorResource, "profesorRepository", profesorRepository);
		ReflectionTestUtils.setField(profesorResource, "profesorMateriaRepository", profesorMateriaRepository);
		this.restProfesorMockMvc = MockMvcBuilders.standaloneSetup(profesorResource).build();
	}

	@Test
	public void testGetExistingProfesors() throws Exception {
		restProfesorMockMvc.perform(get("/api/profesors")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].apellidos").value(hasItem("Mejia")))
				.andExpect(jsonPath("$.[*].nombres").value(hasItem("Anderson")))
				.andExpect(jsonPath("$.[*].carrera").value(hasItem("ING SISTEMAS")));
	}

	@Test
	public void testGetExistingProfesor() throws Exception {
		restProfesorMockMvc.perform(get("/api/profesors/{id}", 2)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombres").value("Diego"))
				.andExpect(jsonPath("$.apellidos").value("Botia"))
				.andExpect(jsonPath("$.carrera").value("ING SISTEMAS"));
	}
	
	@Test
	public void testNotExistingProfesors() throws Exception {
		restProfesorMockMvc.perform(get("/api/profesors/{id}", 5)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombres").doesNotExist())
				.andExpect(jsonPath("$.apellidos").doesNotExist())
				.andExpect(jsonPath("$.carrera").doesNotExist());
	}
	
	@Test
	public void testGetExistingProfesorsByMateria() throws Exception {
		restProfesorMockMvc.perform(get("/api/profesors/course/{nameCourse}", "BASE DE DATOS")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nombres").value(hasItem("Diego")))
				.andExpect(jsonPath("$.[*].apellidos").value(hasItem("Botia")))
				.andExpect(jsonPath("$.[*].carrera").value(hasItem("ING SISTEMAS")));
	}

}
