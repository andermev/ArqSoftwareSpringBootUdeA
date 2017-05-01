package com.udea.example.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A Estudiante.
 */
@Entity
@Table(name = "estudiante_materia")
public class EstudianteMateria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "estudiante_id")
	private Long estudianteId;

	@Column(name = "materia_id")
	private Long materiaId;

	public Long getEstudianteId() {
		return estudianteId;
	}

	public void setEstudianteId(Long estudianteId) {
		this.estudianteId = estudianteId;
	}

	public Long getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Long materiaId) {
		this.materiaId = materiaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EstudianteMateria estudiante = (EstudianteMateria) o;
		if (estudiante.estudianteId == null || estudiante.materiaId == null) {
			return false;
		}
		return Objects.equals(estudianteId, estudiante.estudianteId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(estudianteId);
	}

	@Override
	public String toString() {
		return "EstudianteMateria [estudianteId=" + estudianteId + ", materiaId=" + materiaId + "]";
	}
}
