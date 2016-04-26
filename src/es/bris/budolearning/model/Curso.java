package es.bris.budolearning.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the club database table.
 * 
 */
@Entity
@Table(name="curso")
@NamedQuery(name="Curso.findAll", query="SELECT g FROM Curso g")
public class Curso extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@ManyToOne
	private Disciplina disciplina;
	@Column(length=1024)
	private String nombre;
	@Column(length=1024)
	private String direccion;
	@Column(length=2048)
	private String descripcion;
	@Column(length=1024)
	private String precios;
	@Column(length=1024)
	private String profesor;
	private Date inicio;
	private Date fin;
	/*
	@Basic(fetch=FetchType.LAZY)
	@Lob
	@JsonIgnore
	private byte[] cartel;
	*/

	public Curso() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}
	
	public void setFin(Date fin) {
		this.fin = fin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrecios() {
		return precios;
	}

	public void setPrecios(String precios) {
		this.precios = precios;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}
	
	/*
	public String getCartelImg(){
		if(cartel != null && cartel.length > 0)
			return new String(new org.apache.commons.codec.binary.Base64().encode(cartel));
		else
			return null;
	}
	public byte[] getCartel() {
		return cartel;
	}
	public void setCartel(byte[] cartel) {
		this.cartel = cartel;
	}
	*/
}