package es.bris.budolearning.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the club database table.
 * 
 */
@Entity
@Table(name="puntos")
@NamedQuery(name="Puntos.findAll", query="SELECT g FROM Puntos g")
public class Puntos extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	protected int id;
	protected String tipo;
	protected int idUsuario;
	protected Date fecha;
	protected int puntos;
	protected Integer idFichero;
	
	public Puntos() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getIdFichero() {
		return idFichero;
	}
	public void setIdFichero(Integer idFichero) {
		this.idFichero = idFichero;
	}
	
}