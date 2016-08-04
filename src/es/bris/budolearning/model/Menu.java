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
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(name="menu")
@NamedQuery(name="Menu.findAll", query="SELECT g FROM Menu g")
public class Menu extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String titulo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = true)
	private Menu parent;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pagina_id", nullable = true)
	private Pagina pagina;
	private int logado;

	public Menu() {
	}
	public Menu(int id, String titulo, Menu parent, Pagina pagina) {
		this.id=id;
		this.titulo=titulo;
		this.parent = parent;
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
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public Pagina getPagina() {
		return pagina;
	}
	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}
	public int getLogado() {
		return logado;
	}
	public void setLogado(int logado) {
		this.logado = logado;
	}

	
	
}