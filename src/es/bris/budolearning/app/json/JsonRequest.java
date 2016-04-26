package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Usuario;

public class JsonRequest {

	protected Usuario user;	
	protected int version;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	
}
