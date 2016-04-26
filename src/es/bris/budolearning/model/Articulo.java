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
@Table(name="articulo")
@NamedQuery(name="Articulo.findAll", query="SELECT g FROM Articulo g")
public class Articulo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private Boolean activo;
	private Date fecha;
	private String titulo;
	private String autor;
	private Boolean visibleUsuarios;

	public Articulo() {
	}
	public Articulo(int id, Boolean activo, Date fecha, String titulo, String autor, Boolean visibleUsuarios) {
		this.id=id;
		this.activo=activo;
		this.fecha=fecha;
		this.titulo=titulo;
		this.autor=autor;
		this.visibleUsuarios = visibleUsuarios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Boolean getVisibleUsuarios() {
		return visibleUsuarios;
	}
	public void setVisibleUsuarios(Boolean visibleUsuarios) {
		this.visibleUsuarios = visibleUsuarios;
	}

	
	
}