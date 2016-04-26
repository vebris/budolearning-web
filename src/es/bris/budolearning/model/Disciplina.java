package es.bris.budolearning.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * The persistent class for the disciplina database table.
 * 
 */
@Entity
@Table(name="disciplina")
@NamedQuery(name="Disciplina.findAll", query="SELECT d FROM Disciplina d")
public class Disciplina extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String descripcion;
	private String nombre;

	@OneToMany(mappedBy="disciplina",fetch=FetchType.EAGER)
	@JsonIgnore
	private List<DisciplinaGrado> disciplinaGrados;

	public Disciplina() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DisciplinaGrado> getDisciplinaGrados() {
		return this.disciplinaGrados;
	}

	public void setDisciplinaGrados(List<DisciplinaGrado> disciplinaGrados) {
		this.disciplinaGrados = disciplinaGrados;
	}

	public boolean addDisciplinaGrado(DisciplinaGrado disciplinaGrado) {
		return getDisciplinaGrados().add(disciplinaGrado);
	}

	public boolean removeDisciplinaGrado(DisciplinaGrado disciplinaGrado) {
		return getDisciplinaGrados().remove(disciplinaGrado);
	}

}