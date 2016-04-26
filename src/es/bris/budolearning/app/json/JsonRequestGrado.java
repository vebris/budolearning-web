package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.Grado;

public class JsonRequestGrado extends JsonRequest{

	private Grado data;
	private Disciplina disciplina;
	
	public Grado getData() {
		return data;
	}
	public void setData(Grado data) {
		this.data = data;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	
}
