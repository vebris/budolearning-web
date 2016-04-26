package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.Recurso;

public class JsonRequestRecurso extends JsonRequest{

	private Recurso data;
	private Disciplina disciplina;
	private Grado grado;
	
	public Recurso getData() {
		return data;
	}
	public void setData(Recurso data) {
		this.data = data;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public Grado getGrado() {
		return grado;
	}
	public void setGrado(Grado grado) {
		this.grado = grado;
	}
	
	
}
