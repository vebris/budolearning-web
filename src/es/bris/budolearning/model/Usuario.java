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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"login"}) )
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String login; 
	private String password;
	private String rol;
	private Boolean activo;
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private String direccion;
	private String localidad;
	private String mail;
	private String telefono;
	private int versionAndroid;
	private Boolean verPDF;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="usuario_disciplina_grado"
		, joinColumns={
			@JoinColumn(name="usuario_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="disciplina_grado_id")
			}
		)
	private List<DisciplinaGrado> disciplinaGrados;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="profesor_id")
	private Club profesor;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entrena_id")
	private Club entrena;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setDisciplinaGrados(List<DisciplinaGrado> disciplinaGrados) {
		this.disciplinaGrados = disciplinaGrados;
	}

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<DisciplinaGrado> getDisciplinaGrados() {
		return this.disciplinaGrados;
	}

	public void setDisiciplinaGrados(List<DisciplinaGrado> disciplinaGrado) {
		this.disciplinaGrados = disciplinaGrado;
	}

	public DisciplinaGrado addUsuarioGrado(DisciplinaGrado disciplinaGrado) {
		getDisciplinaGrados().add(disciplinaGrado);
		return disciplinaGrado;
	}

	public DisciplinaGrado removeUsuarioGrado(DisciplinaGrado disiciplinaGrado) {
		getDisciplinaGrados().remove(disiciplinaGrado);
		return disiciplinaGrado;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Club getProfesor() {
		return profesor;
	}

	public void setProfesor(Club profesor) {
		this.profesor = profesor;
	}

	public Club getEntrena() {
		return entrena;
	}

	public void setEntrena(Club entrena) {
		this.entrena= entrena;
	}

	public int getVersionAndroid() {
		return versionAndroid;
	}

	public void setVersionAndroid(int versionAndroid) {
		this.versionAndroid = versionAndroid;
	}

	public Boolean getVerPDF() {
		return verPDF;
	}

	public void setVerPDF(Boolean verPDF) {
		this.verPDF = verPDF;
	}

}