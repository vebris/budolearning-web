package es.bris.budolearning.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the club database table.
 * 
 */
@Entity
@Table(name="log_download_file")
public class LogDownloadFile extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fichero_id")
	private Fichero fichero;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	private Date fecha;
	private long descargado;
	private String extension;
	@Transient
	private int coste;

	public LogDownloadFile() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fichero getFichero() {
		return fichero;
	}

	public void setFichero(Fichero fichero) {
		this.fichero = fichero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setFecha(Long fecha) {
		this.fecha = new Date(fecha);
	}
	public void setFecha(String fecha) {
		this.fecha = new Date(Long.parseLong(fecha));
	}

	public long getDescargado() {
		return descargado;
	}

	public void setDescargado(long descargado) {
		this.descargado = descargado;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getCoste() {
		return coste;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}
	
}