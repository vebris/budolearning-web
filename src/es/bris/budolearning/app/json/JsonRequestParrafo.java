package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Parrafo;

public class JsonRequestParrafo extends JsonRequest{

	private Parrafo data;
	
	public Parrafo getData() {
		return data;
	}
	public void setData(Parrafo data) {
		this.data = data;
	}
	
}
