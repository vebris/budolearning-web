package es.bris.budolearning.model;

import java.util.Date;

public class EstadisticaVideosUsuario {

	
	private int idFichero;
	private String descFichero;
	private Long totalNumero;
	private Long totalTamano;
	private Date ultimaVisualizacion;
	
	public EstadisticaVideosUsuario (){}

	public EstadisticaVideosUsuario (int idFichero, String descFichero, Long totalNumero, Long totalTamano, Date ultimaVisualizacion){
		this.idFichero = idFichero;
		this.descFichero = descFichero;
		this.totalNumero = totalNumero;
		this.totalTamano = totalTamano;		
		this.setUltimaVisualizacion(ultimaVisualizacion);
	}
	
	public int getIdFichero() {
		return idFichero;
	}

	public void setIdFichero(int idFichero) {
		this.idFichero = idFichero;
	}

	public String getDescFichero() {
		return descFichero;
	}

	public void setDescFichero(String descFichero) {
		this.descFichero = descFichero;
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

	public Date getUltimaVisualizacion() {
		return ultimaVisualizacion;
	}

	public void setUltimaVisualizacion(Date ultimaVisualizacion) {
		this.ultimaVisualizacion = ultimaVisualizacion;
	}
	
	
}
