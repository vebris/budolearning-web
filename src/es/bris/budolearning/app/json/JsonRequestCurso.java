package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Curso;

public class JsonRequestCurso extends JsonRequest{

	private Curso data;
	
	public Curso getData() {
		return data;
	}
	public void setData(Curso data) {
		this.data = data;
	}
	
}
