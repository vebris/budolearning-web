package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Pagina;

public class JsonRequestPagina extends JsonRequest{

	private Pagina data;
	
	public Pagina getData() {
		return data;
	}
	public void setData(Pagina data) {
		this.data = data;
	}
	
}
