package es.bris.budolearning.model;

public class EstadisticaUsuario {

	
	private String usuario;
	private Long totalNumero;
	private Long totalTamano;
	
	public EstadisticaUsuario (){}
	public EstadisticaUsuario (Long usuario, Long totalNumero, Long totalTamano){
		this.setUsuario(usuario+"");
		this.totalNumero = totalNumero;
		this.totalTamano = totalTamano;				
	}
	public EstadisticaUsuario (String usuario, Long totalNumero, Long totalTamano){
		this.setUsuario(usuario);
		this.totalNumero = totalNumero;
		this.totalTamano = totalTamano;				
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Long getTotalNumero() {
		return totalNumero;
	}
	public void setTotalNumero(Long totalNumero) {
		this.totalNumero = totalNumero;
	}
	public Long getTotalTamano() {
		return totalTamano;
	}
	public void setTotalTamano(Long totalTamano) {
		this.totalTamano = totalTamano;
	}
	
	
}
