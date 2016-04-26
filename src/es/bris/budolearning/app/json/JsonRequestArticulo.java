package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Articulo;

public class JsonRequestArticulo extends JsonRequest{

	private Articulo data;
	
	public Articulo getData() {
		return data;
	}
	public void setData(Articulo data) {
		this.data = data;
	}
	
}
