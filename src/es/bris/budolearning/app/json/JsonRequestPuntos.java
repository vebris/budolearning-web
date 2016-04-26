package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Puntos;
import es.bris.budolearning.model.Usuario;

public class JsonRequestPuntos extends JsonRequest{

	private Usuario data;
	private Puntos puntos;
	public Usuario getData() {
		return data;
	}
	public void setData(Usuario data) {
		this.data = data;
	}
	public Puntos getPuntos() {
		return puntos;
	}
	public void setPuntos(Puntos puntos) {
		this.puntos = puntos;
	}
	
	
}
