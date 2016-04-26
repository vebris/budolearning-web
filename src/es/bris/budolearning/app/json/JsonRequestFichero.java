package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.Recurso;

public class JsonRequestFichero extends JsonRequest{

	private Fichero data;
	private Recurso recurso;
	
	public Fichero getData() {
		return data;
	}
	public void setData(Fichero data) {
		this.data = data;
	}
	public Recurso getRecurso() {
		return recurso;
	}
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	
}
