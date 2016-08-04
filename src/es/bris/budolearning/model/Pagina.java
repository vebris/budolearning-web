package es.bris.budolearning.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the pagina database table.
 * 
 */
@Entity
@Table(name="pagina")
@NamedQuery(name="Pagina.findAll", query="SELECT g FROM Pagina g")
public class Pagina extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String titulo;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable = true)
	private Pagina parent;
	private int tipoPagina;

	public Pagina() {
	}
	public Pagina(int id, String titulo, Pagina parent, int tipoPagina) {
		this.id=id;
		this.titulo=titulo;
		this.parent = parent;
		this.tipoPagina = tipoPagina;
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
	public Pagina getParent() {
		return parent;
	}
	public void setParent(Pagina parent) {
		this.parent = parent;
	}
	public int getTipoPagina() {
		return tipoPagina;
	}
	public void setTipoPagina(int tipoPagina) {
		this.tipoPagina = tipoPagina;
	}

	
	
}