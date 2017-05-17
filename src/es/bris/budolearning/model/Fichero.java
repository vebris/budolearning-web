package es.bris.budolearning.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="fichero")
@NamedQuery(name="Fichero.findAll", query="SELECT u FROM Fichero u")
public class Fichero extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String descripcion;
	private String nombreFichero;
	private String extension;
	private Date fecha;
	private Date fechaModificacion;
	@ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
	@JsonIgnore
	private Recurso recurso;
	/*@Basic(fetch=FetchType.LAZY)
	@Lob
	@JsonIgnore
	private byte[] fichero;
	*/
	private Boolean activo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = true)
	@JsonIgnore
	private Usuario usuario;
	private long tamano;
	@Transient
	private long visitas;
	private int coste;
	private int segundos;
	private Boolean propio;

	public Fichero() {
	}
	
	public Fichero(int id, String descripcion, String nombreFichero, String extension, Date fecha, Recurso recurso, Boolean activo, long tamano, long visitas, int coste, int segundos, Boolean propio) {
		this.id = id;
		this.descripcion = descripcion;
		this.nombreFichero = nombreFichero;
		this.extension = extension;
		this.fecha = fecha;
		this.recurso = recurso;
		this.activo = activo;
		this.tamano = tamano;
		this.visitas = visitas;
		this.coste = coste;
		this.segundos=segundos;
		this.propio=propio;
	}
	public Fichero(int id, String descripcion, String nombreFichero, String extension, Date fecha, Recurso recurso) {
		this.id = id;
		this.descripcion = descripcion;
		this.nombreFichero = nombreFichero;
		this.extension = extension;
		this.fecha = fecha;
		this.recurso = recurso;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	/*
	public byte[] getFichero() {
		return fichero;
	}

	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}
	*/

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@SuppressWarnings("deprecation")
	public void setFecha(String fecha){
		try{
			this.fecha = new Date(fecha);
		} catch(Exception e){
			this.fecha = new Date();
		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public long getTamano() {
		return tamano;
	}

	public void setTamano(long tamano) {
		this.tamano = tamano;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getVisitas() {
		return visitas;
	}

	public void setVisitas(long visitas) {
		this.visitas = visitas;
	}

	public int getCoste() {
		return coste;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	public Boolean getPropio() {
		return propio;
	}

	public void setPropio(Boolean propio) {
		this.propio = propio;
	}
	
}