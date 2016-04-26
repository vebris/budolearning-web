package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Disciplina;

public class JsonRequestDisciplina extends JsonRequest{

	private Disciplina data;
	
	public Disciplina getData() {
		return data;
	}
	public void setData(Disciplina data) {
		this.data = data;
	}
	
}
