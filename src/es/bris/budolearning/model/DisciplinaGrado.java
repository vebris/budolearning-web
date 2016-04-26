package es.bris.budolearning.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the usuario_grado database table.
 * 
 */
@Entity
@Table(name="disciplina_grado")
@NamedQuery(name="DisciplinaGrado.findAll", query="SELECT u FROM DisciplinaGrado u")
public class DisciplinaGrado extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
	private Disciplina disciplina;

	@ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
	private Grado grado;
	
	public DisciplinaGrado() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Grado getGrado() {
		return this.grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getId() == ((DisciplinaGrado)obj).getId();
	}

}