package es.bris.budolearning.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.Fichero;
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

	public VideoEspecial videoDelDia() {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Random generator = new Random(c.getTimeInMillis());
	    double num = generator.nextDouble();
		
		Query query = entityManager.createQuery(
				" SELECT f " +
				" FROM Fichero f join f.recurso.disciplinaGrados dg "+  
				" where  "+
				"	f.recurso.arma.id > 0 and f.propio=1 and f.extension='mp4'"+
				"	and dg.disciplina.id=2 " +
				" order by f.id asc"
				);
		System.out.println(c + " // " + c.getTimeInMillis());
		int maxElementos = query.getResultList().size();
		query.setFirstResult((int) (num*maxElementos));
		query.setMaxResults(1);
		
		VideoEspecial ve = new VideoEspecial();
		ve.setFichero((Fichero) query.getSingleResult());
		ve.setInicio(new Date());
		ve.setFin(new Date());
		return ve;
	}
	
}
