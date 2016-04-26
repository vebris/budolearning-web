package es.bris.budolearning.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.Usuario;
import es.bris.budolearning.model.VideoEspecial;

/**
 * Session Bean implementation class VistaUsuarioDAO
 */
@Stateless
@LocalBean
public class VideoEspecialDAO extends GenericDAO implements VideoEspecialDAOLocal {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;

	@Override
	public VideoEspecial anadir(VideoEspecial elemento) {
    	return entityManager.merge(elemento);
	}

	@Override
	public VideoEspecial modificar(VideoEspecial elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public boolean borrar(int id) {
		boolean borrado = false;
		VideoEspecial videoEspecial = obtener(id);
		entityManager.remove(videoEspecial);
		return borrado;
	}

	@Override
	public VideoEspecial obtener(int id) {
		return entityManager.find(VideoEspecial.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<VideoEspecial> buscarVideos(Club club, Disciplina disciplina, Grado grado, Usuario usuario) {
		Query query = entityManager.createQuery("SELECT new es.bris.budolearning.model.VideoEspecial(ve) "
				+ " FROM VideoEspecial ve, Usuario u "
				+ " WHERE "
				+ "   u.id = :idUser "
				+ "   AND "
				+ "   ("
				+ "     u.rol = 'ADMINISTRADOR' "
				+ "     OR "
				+ "     (u.rol = 'PROFESOR' AND u.profesor.id = ve.club.id)"
				+ "		OR"
				+ "     ("
				+ "			("
				+ "				(ve.usuario.id is null AND u.entrena.id=ve.club.id AND ve.club.id=:idClub )"
				+ "				OR ve.usuario.id = u.id"
				+ "			)"
				+ "			AND "
				+ "			:ahora between ve.inicio and ve.fin"
				+ "		)"
				+ "   )"
				+ " order by ve.inicio desc");
		query.setParameter("idUser", usuario.getId());
		query.setParameter("idClub", club.getId());
		query.setParameter("ahora", new Date());
    	return query.getResultList();
	}
	
}
