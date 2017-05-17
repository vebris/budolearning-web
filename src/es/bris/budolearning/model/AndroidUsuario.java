package es.bris.budolearning.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AndroidUsuario {

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
	
	private List<AndroidDisciplina> disciplinas;
	private AndroidClub entrena;
	private AndroidClub profesor;
	private List<Fichero> videosEspeciales;
	private int puntos;
	private int version;
	private Boolean verPDF;
	
	public class AndroidClub{
		private int id;
		private String descripcion;
		private String nombre;
		
		public AndroidClub(){}
		public AndroidClub(Club club){
			if(club != null){
				id = club.getId();
				nombre = club.getNombre();
				descripcion = club.getDescripcion();
			}
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
	public class AndroidDisciplina{
		private int id;
		private String descripcion;
		private String nombre;
		private List<AndroidGrado> grados;
		
		public AndroidDisciplina(){}
		public AndroidDisciplina(Disciplina disciplina){
			if(disciplina != null){
				id = disciplina.getId();
				nombre = disciplina.getNombre();
				descripcion = disciplina.getDescripcion();
			}
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public List<AndroidGrado> getGrados() {
			return grados;
		}
		public void setGrados(List<AndroidGrado> grados) {
			this.grados = grados;
		}
	}
	public class AndroidGrado{
		private int id;
		private String descripcion;
		private String nombre;
		private List<AndroidRecurso> recursos;
		public AndroidGrado(){}
		public AndroidGrado(Grado grado){
			if (grado != null) {
				id = grado.getId();
				nombre = grado.getNombre();
				descripcion = grado.getDescripcion();
			}
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public List<AndroidRecurso> getRecursos() {
			return recursos;
		}
		public void setRecursos(List<AndroidRecurso> recursos) {
			this.recursos = recursos;
		}
	}
	public class AndroidRecurso{
		private int id;
		private String nombre;
		private AndroidTipoRecurso tipo;
		private AndroidTipoArma arma;
		private List<AndroidFichero> ficheros;
		private int numVideos;
		private int numPdf;
		private boolean enPrograma;
		
		public AndroidRecurso(){}
		public AndroidRecurso(Recurso recurso){
			if(recurso != null) {
				id = recurso.getId();
				nombre = recurso.getNombre();
				tipo = new AndroidTipoRecurso(recurso.getTipo());
				arma = new AndroidTipoArma(recurso.getArma());
				numVideos = recurso.getNumVideos();
				numPdf = recurso.getNumPdf();
				enPrograma = recurso.isEnPrograma();
			}
		}
		public boolean disciplinas(){ return enPrograma;}
		public void setEnPrograma(boolean enPrograma){this.enPrograma=enPrograma;}
		public int getNumVideos(){return numVideos;}
		public void setNumVideos(int numVideos){this.numVideos=numVideos;}
		public int getNumPdf(){return numPdf;}
		public void setNumPdf(int numPdf){this.numPdf=numPdf;}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public AndroidTipoRecurso getTipo() {
			return tipo;
		}
		public AndroidTipoArma getArma() {
			return arma;
		}
		public void setTipoArma(AndroidTipoArma arma) {
			this.arma = arma;
		}
		public void setTipo(AndroidTipoRecurso tipo) {
			this.tipo = tipo;
		}
		public List<AndroidFichero> getFicheros() {
			return ficheros;
		}
		public void setFicheros(List<AndroidFichero> ficheros) {
			this.ficheros = ficheros;
		}
		public boolean isEnPrograma(){return enPrograma;}
	}
	public class AndroidTipoRecurso{
		private int id;
		private String descripcion;
		private String nombre;
		
		public AndroidTipoRecurso(){}
		public AndroidTipoRecurso(TipoRecurso tipoRecurso){
			if(tipoRecurso != null){
				id = tipoRecurso.getId();
				nombre = tipoRecurso.getNombre();
				descripcion = tipoRecurso.getDescripcion();
			}
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
	public class AndroidTipoArma{
		private int id;
		private String descripcion;
		private String nombre;
		
		public AndroidTipoArma(){}
		public AndroidTipoArma(TipoArma tipoArma){
			if(tipoArma != null){
				id = tipoArma.getId();
				nombre = tipoArma.getNombre();
				descripcion = tipoArma.getDescripcion();
			}
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
	public class AndroidFichero{
		private int id;
		private String descripcion;
		private String nombreFichero;
		private String extension;
		private Date fecha;
		private Date fechaModificacion;
		private Boolean activo;
		private long tamano;
		private long visitas;
		private int coste;
		private Boolean propio;
		private int segundos;
		
		public AndroidFichero(){}
		public AndroidFichero(Fichero fichero){
			if(fichero != null){
				id = fichero.getId();
				descripcion = fichero.getDescripcion();
				nombreFichero = fichero.getNombreFichero();
				extension = fichero.getExtension();
				fecha = fichero.getFecha();
				fechaModificacion = fichero.getFechaModificacion();
				activo = fichero.getActivo();
				tamano = fichero.getTamano();
				visitas = fichero.getVisitas();
				propio = fichero.getPropio();
				segundos = fichero.getSegundos();
				setCoste(fichero.getCoste());
			}
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
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
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		public Date getFechaModificacion() {
			return fechaModificacion;
		}
		public void setFechaModificacion(Date fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
		}
		public Boolean getActivo() {
			return activo;
		}
		public void setActivo(Boolean activo) {
			this.activo = activo;
		}
		public long getTamano() {
			return tamano;
		}
		public void setTamano(long tamano) {
			this.tamano = tamano;
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
		public Boolean getPropio() {
			return propio;
		}
		public void setPropio(Boolean propio) {
			this.propio = propio;
		}
		public int getSegundos() {
			return segundos;
		}
		public void setSegundos(int segundos) {
			this.segundos = segundos;
		}
	}
	
	public AndroidUsuario(){}
	public AndroidUsuario(Usuario usuario){
		if(usuario != null){
			id = usuario.getId();
			login = usuario.getLogin(); 
			password = usuario.getPassword();
			rol = usuario.getRol();
			activo = usuario.getActivo();
			
			nombre = usuario.getNombre();
			apellido1 = usuario.getApellido1();
			apellido2 = usuario.getApellido2();
			dni = usuario.getDni();
			direccion = usuario.getDireccion();
			localidad = usuario.getLocalidad();
			mail = usuario.getMail();
			telefono = usuario.getTelefono();
			
			entrena = new AndroidClub(usuario.getEntrena());
			profesor = new AndroidClub(usuario.getProfesor());
			version = usuario.getVersionAndroid();
			verPDF = usuario.getVerPDF();
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public List<AndroidDisciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<AndroidDisciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public AndroidClub getEntrena() {
		return entrena;
	}
	public void setEntrena(AndroidClub entrena) {
		this.entrena = entrena;
	}
	public AndroidClub getProfesor() {
		return profesor;
	}
	public void setProfesor(AndroidClub profesor) {
		this.profesor = profesor;
	}
	
	
	public List<AndroidDisciplina> toListAndroidDisciplina(List<Disciplina> disciplinas){
		List<AndroidDisciplina> androidDisciplinas = new ArrayList<AndroidDisciplina>();
		for(Disciplina d:disciplinas){
			androidDisciplinas.add(new AndroidDisciplina(d));
		}
		return androidDisciplinas;
	}
	public List<AndroidGrado> toListAndroidGrado(List<Grado> grados){
		List<AndroidGrado> androidGrados = new ArrayList<AndroidGrado>();
		for(Grado d:grados){
			androidGrados.add(new AndroidGrado(d));
		}
		return androidGrados;
	}
	public List<AndroidRecurso> toListAndroidRecursos(List<Recurso> recursos){
		List<AndroidRecurso> androidDisciplinas = new ArrayList<AndroidRecurso>();
		for(Recurso d:recursos){
			androidDisciplinas.add(new AndroidRecurso(d));
		}
		return androidDisciplinas;
	}
	public List<AndroidFichero> toListAndroidFicheros(List<Fichero> ficheros){
		List<AndroidFichero> androidFicheros = new ArrayList<AndroidFichero>();
		for(Fichero d:ficheros){
			androidFicheros.add(new AndroidFichero(d));
		}
		return androidFicheros;
	}
	public List<Fichero> getVideosEspeciales() {
		return videosEspeciales;
	}
	public void setVideosEspeciales(List<Fichero> videosEspeciales) {
		this.videosEspeciales = videosEspeciales;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Boolean getVerPDF() {
		return verPDF;
	}
	public void setVerPDF(Boolean verPDF) {
		this.verPDF = verPDF;
	}
	
}
