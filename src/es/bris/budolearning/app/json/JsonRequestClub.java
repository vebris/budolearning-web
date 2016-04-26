package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Club;

public class JsonRequestClub extends JsonRequest{

	private Club data;
	
	public Club getData() {
		return data;
	}
	public void setData(Club data) {
		this.data = data;
	}
	
}
