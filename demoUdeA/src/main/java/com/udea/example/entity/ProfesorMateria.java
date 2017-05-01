package com.udea.example.entity;

import java.io.Serializable;

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
@Table(name = "profesor_materia")
public class ProfesorMateria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "profesor_id")
	private Long profesorId;

	@Column(name = "materia_id")
	private Long materiaId;

	public Long getProfesorId() {
		return profesorId;
	}

	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}

	public Long getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Long materiaId) {
		this.materiaId = materiaId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfesorMateria other = (ProfesorMateria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (materiaId == null) {
			if (other.materiaId != null)
				return false;
		} else if (!materiaId.equals(other.materiaId))
			return false;
		if (profesorId == null) {
			if (other.profesorId != null)
				return false;
		} else if (!profesorId.equals(other.profesorId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((materiaId == null) ? 0 : materiaId.hashCode());
		result = prime * result + ((profesorId == null) ? 0 : profesorId.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ProfesorMateria [id=" + id + ", profesorId=" + profesorId + ", materiaId=" + materiaId + "]";
	}
}
