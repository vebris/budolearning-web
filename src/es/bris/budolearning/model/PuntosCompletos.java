package es.bris.budolearning.model;

import java.util.Date;


public class PuntosCompletos extends Puntos {
	private static final long serialVersionUID = 1L;

	private String fichero;
	
	public PuntosCompletos(int id, String tipo, int idUsuario, Date fecha, int puntos, Integer idFichero, String fichero) {
		this.id=id;
		this.tipo=tipo;
		this.idUsuario=idUsuario;
		this.fecha = fecha;
		this.puntos = puntos;
		this.idFichero = idFichero;
		this.setFichero(fichero);
	}
	public PuntosCompletos(int id, String tipo, int idUsuario, Date fecha, long puntos) {
		this.id=id;
		this.tipo=tipo;
		this.idUsuario=idUsuario;
		this.fecha = fecha;
		this.puntos = (int)puntos;
	}

	public String getFichero() {
		return fichero;
	}

	public void setFichero(String fichero) {
		this.fichero = fichero;
	}
	
}