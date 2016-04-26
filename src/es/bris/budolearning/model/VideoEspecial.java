package es.bris.budolearning.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the videoEspecial database table.
 * 
 */
@Entity
@Table(name="video_especial")
@NamedQuery(name="VideoEspecial.findAll", query="SELECT g FROM VideoEspecial g")
public class VideoEspecial extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@ManyToOne
	@JoinColumn(name="fichero_id")
	private Fichero fichero;
	@ManyToOne
	@JoinColumn(name="club_id")
	private Club club;
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	private Date inicio;
	private Date fin;
	
	public VideoEspecial() {
	}
	public VideoEspecial(VideoEspecial ve) {
		this.id=ve.id;
		this.fichero = ve.getFichero();
		this.club = ve.getClub();
		if(ve.getUsuario() != null) {
			this.usuario = new Usuario();
			this.usuario.setId(ve.getUsuario().getId());
			this.usuario.setNombre(ve.getUsuario().getNombre());
			this.usuario.setApellido1(ve.getUsuario().getApellido1());
			this.usuario.setApellido2(ve.getUsuario().getApellido2());
		}
		this.inicio = ve.inicio;
		this.fin = ve.fin;
	}
	public int getId() {
		return id;
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
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}