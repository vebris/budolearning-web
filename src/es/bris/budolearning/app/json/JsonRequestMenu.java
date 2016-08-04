package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Menu;

public class JsonRequestMenu extends JsonRequest{

	private Menu data;
	
	public Menu getData() {
		return data;
	}
	public void setData(Menu data) {
		this.data = data;
	}
	
}
