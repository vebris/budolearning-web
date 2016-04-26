package es.bris.budolearning.model;

public class EstadisticaMensual {

	private String fecha;
	private Long numero;
	private Long tamano;
	
	public EstadisticaMensual (){}
	public EstadisticaMensual (String fecha, Long numero, Long tamano){
		this.fecha = fecha;
		this.numero = numero;
		this.tamano = tamano;				
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public Long getTamano() {
		return tamano;
	}
	public void setTamano(Long tamano) {
		this.tamano = tamano;
	}
	
	
}
