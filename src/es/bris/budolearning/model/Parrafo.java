package es.bris.budolearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * The persistent class for the parrafo database table.
 * 
 */
@Entity
@Table(name="parrafo")
@NamedQuery(name="Parrafo.findAll", query="SELECT g FROM Parrafo g")
public class Parrafo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String titulo;
	@Column(length = 4096)
	private String texto;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pagina_id", nullable = true)
	@JsonIgnore
	private Pagina pagina;
	private int orden;
	private boolean imagen;

	public Parrafo() {
	}
	public Parrafo(int id, String titulo, String texto, Pagina pagina) {
		this.id=id;
		this.titulo=titulo;
		this.texto = texto;
		this.pagina = pagina;
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
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Pagina getPagina() {
		return pagina;
	}
	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public boolean isImagen() {
		return imagen;
	}
	public void setImagen(boolean imagen) {
		this.imagen = imagen;
	}

	
	
}