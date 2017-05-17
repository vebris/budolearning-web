package es.bris.budolearning.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * The persistent class for the club database table.
 * 
 */
@Entity
@Table(name="android")
@NamedQuery(name="Android.findAll", query="SELECT g FROM Android g")
public class Android extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private int numVersion;
	private String version;
	@Basic(fetch=FetchType.LAZY)
	@Lob
	@JsonIgnore
	private byte[] fichero;
	private String aplicacion;

	public Android() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumVersion() {
		return numVersion;
	}

	public void setNumVersion(int numVersion) {
		this.numVersion = numVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public byte[] getFichero() {
		return fichero;
	}

	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	
	
}