package es.bris.budolearning.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Formula;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="recurso")
@NamedQuery(name="Recurso.findAll", query="SELECT u FROM Recurso u")
public class Recurso extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String nombre;
	@ManyToOne
	private TipoRecurso tipo;
	@ManyToOne
	private TipoArma arma;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="recurso_disciplina_grado"
		, joinColumns={
			@JoinColumn(name="recurso_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="disciplina_grado_id")
			}
		)
	@JsonIgnore
	private List<DisciplinaGrado> disciplinaGrados;
	
	private boolean enPrograma;
	
	@OneToMany (fetch=FetchType.LAZY, orphanRemoval=true)
	@JsonIgnore
	private List<Fichero> ficheros;
	
	@Formula("(select count(f.id) from fichero f where f.recurso_id = id and f.activo = 1 and f.extension='mp4')")
	private int numVideos;
	
	@Formula("(select count(f.id) from fichero f where f.recurso_id = id and f.activo = 1 and f.extension='pdf')")
	private int numPdf;

	public Recurso() {
	}
	public Recurso(Recurso r) {
		this.id = r.id;
		this.nombre = r.nombre;
		this.disciplinaGrados = r.disciplinaGrados;
		this.enPrograma = r.enPrograma;
		this.ficheros = r.ficheros;
		this.tipo = r.tipo;
	}
	public Recurso(Recurso r, long numVideos, long numPdf) {
		this.id = r.id;
		this.nombre = r.nombre;
		this.disciplinaGrados = r.disciplinaGrados;
		this.enPrograma = r.enPrograma;
		this.ficheros = r.ficheros;
		this.tipo = r.tipo;
		this.numVideos = (int)numVideos;
		this.numPdf = (int)numPdf;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<DisciplinaGrado> getDisciplinaGrados() {
		return this.disciplinaGrados;
	}
	public void setDisciplinaGrados(List<DisciplinaGrado> disciplinaGrados) {
		this.disciplinaGrados = disciplinaGrados;
	}
	public DisciplinaGrado addRecursoGrado(DisciplinaGrado disciplinaGrados) {
		getDisciplinaGrados().add(disciplinaGrados);
		return disciplinaGrados;
	}
	public DisciplinaGrado removeRecursoGrado(DisciplinaGrado disciplinaGrados) {
		getDisciplinaGrados().remove(disciplinaGrados);
		return disciplinaGrados;
	}
	public TipoRecurso getTipo() {
		return tipo;
	}
	public void setTipo(TipoRecurso tipo) {
		this.tipo = tipo;
	}
	public TipoArma getArma() {
		return arma;
	}
	public void setArma(TipoArma arma) {
		this.arma = arma;
	}
	public List<Fichero> getFicheros() {
		return ficheros;
	}
	public void setFicheros(List<Fichero> ficheros) {
		this.ficheros = ficheros;
	}
	public boolean isEnPrograma() {
		return enPrograma;
	}
	public void setEnPrograma(boolean enPrograma) {
		this.enPrograma = enPrograma;
	}
	public int getNumVideos() {
		return numVideos;
	}
	public void setNumVideos(int numVideos) {
		this.numVideos = numVideos;
	}
	public int getNumPdf() {
		return numPdf;
	}
	public void setNumPdf(int numPdf) {
		this.numPdf = numPdf;
	}
	
}