package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.Recurso;

/**
 * Session Bean implementation class RecursoDAO
 */
@Stateless
@LocalBean
public class FicheroDAO implements FicheroDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public FicheroDAO() {

	}

	@Override
	public Fichero anadir(Fichero elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Fichero anadir(Fichero elemento, long tamano) {
		elemento.setTamano(tamano);
		return entityManager.merge(elemento);
	}

	@Override
	public Fichero modificar(Fichero elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Fichero modificar(Fichero elemento, long tamano) {
		elemento.setTamano(tamano);
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		Query query1 = entityManager
				.createNativeQuery("UPDATE log_download_file SET fichero_id=null WHERE fichero_id = :id");
		query1.setParameter("id", id);
		query1.executeUpdate();
		Query query = entityManager
				.createQuery("DELETE FROM Fichero u WHERE u.id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public Fichero obtener(int id) {
		return entityManager.find(Fichero.class, id);
	}

	@Override
	public Fichero obtenerCorto(int id) {
		Query query = entityManager
				.createQuery("SELECT new es.bris.budolearning.model.Fichero(f.id, f.descripcion, f.nombreFichero, f.extension, f.fecha, f.recurso) FROM Fichero f WHERE f.id = :id");
		query.setParameter("id", id);
		return (Fichero) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fichero> buscarFicheros(Recurso recurso) {
		Query query = entityManager
				.createQuery("SELECT f FROM Fichero f WHERE f.recurso.id = :idRecurso");
		query.setParameter("idRecurso", recurso.getId());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fichero> buscarFicherosCorto(Recurso recurso) {
		Query query = entityManager
				.createQuery("SELECT new es.bris.budolearning.model.Fichero(f.id, f.descripcion, f.nombreFichero, f.extension, f.fecha, f.recurso) FROM Fichero f WHERE f.recurso.id = :idRecurso");
		query.setParameter("idRecurso", recurso.getId());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fichero> buscarTodosFicheros() {
		Query query = entityManager.createQuery("SELECT f FROM Fichero f ");
		return query.getResultList();
	}

	/******************************************************/
	@SuppressWarnings("unchecked")
	public List<Fichero> buscarFicheros(int idUsuario, int idRecurso) {
		System.out.println(" buscarFicheros:  " + idUsuario + " " + idRecurso + " ==> " + entityManager.createQuery("from Fichero f where f.recurso.id is null").getResultList().size());
		String sql = "SELECT DISTINCT new es.bris.budolearning.model.Fichero"
				+ "	( f.id, f.descripcion, f.nombreFichero, f.extension, f.fecha, f.recurso, f.activo, f.tamano, "
				+ "   (select count(l.id) from LogDownloadFile l where l.fichero.id=f.id and l.usuario.id=:idUsuario), "
				+ "   f.coste,"
				+ "	  f.segundos,"
				+ "   f.propio"
				+ " ) from Fichero f, Usuario u "
				+ " WHERE "
				+ "		f.recurso.id = :idRecurso and u.id=:idUsuario "
				+ "		and ( 	"
				+ "				((f.activo=true and f.extension<>'pdf') or (f.activo=true and f.extension='pdf' and u.verPDF=true)) "
				+ "				or "
				+ "				(u.rol='ADMINISTRADOR') "
				+ "		)"
				+ " order by f.fecha desc";
		
		if(idRecurso < 0) {
			sql = "select distinct(f) from Fichero f, Usuario u WHERE "
					+ "		f.recurso.id = null and u.id=:idUsuario and u.rol='ADMINISTRADOR'"
					+ " order by f.fecha desc";			
		}
		
		System.out.println (sql);
		Query query =  entityManager.createQuery(sql);
		if(idRecurso > 0) {
			query.setParameter("idRecurso", idRecurso);
		}
		query.setParameter("idUsuario", idUsuario);
		List<Fichero> lista = (List<Fichero>) query.getResultList();
		for(Fichero f:lista){
			
			System.out.println(idUsuario + " " + f.getId() + " ==> "+ ficheroSinCoste(idUsuario, f.getId()));
			
			if(idRecurso < 0 || ficheroSinCoste(idUsuario, f.getId())){
				f.setCoste(0);
			} else {
				int multiplicador = 1000;
				
				for(DisciplinaGrado dg:f.getRecurso().getDisciplinaGrados()){
					int grado = selectGradoDisciplinaUsuario(dg.getDisciplina().getId(), idUsuario);
					if(multiplicador > dg.getGrado().getId() - grado) {
						multiplicador = dg.getGrado().getId() - grado;
					}
					
				}
				if(multiplicador>0) multiplicador--;
				f.setCoste(f.getCoste()*multiplicador);
			}
		}
		return lista;
	}
	
	
	private int selectGradoDisciplinaUsuario(Integer idDisciplina, Integer idUsuario) {
		String sql = "SELECT max(g.id) FROM Usuario u inner join u.disciplinaGrados dg inner join dg.grado g where u.id=:idUsuario and dg.disciplina.id=:idDisciplina";
		Query query =  entityManager.createQuery(sql);
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idUsuario", idUsuario);
		int max_grado = 0;
		try{ 
			max_grado = (int) query.getSingleResult();
		} catch(Exception e) {
			
		}
		return max_grado;
	}

	public boolean ficheroSinCoste(Integer idUsuario, Integer idFichero) {
		boolean ficheroSinCoste = false;
		Query query = entityManager
				.createQuery("SELECT distinct(f) FROM Usuario u join u.disciplinaGrados dg1, Recurso r join r.disciplinaGrados dg2, Fichero f "
						+ " WHERE "
						+ "		u.id = :idUsuario "
						+ "		and dg1.id=dg2.id "
						+ "		and r.id=f.recurso.id "
						+ "		and f.id=:idFichero"
						+ "");
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idFichero", idFichero);
		ficheroSinCoste = query.getResultList().size() > 0;
		
		if(!ficheroSinCoste){
			Query query2 = entityManager
					.createQuery("SELECT distinct(p) FROM Puntos p"
							+ " WHERE "
							+ "		p.idUsuario = :idUsuario "
							+ "		and p.idFichero = :idFichero"
							+ "");
			query2.setParameter("idUsuario", idUsuario);
			query2.setParameter("idFichero", idFichero);
			ficheroSinCoste |= query2.getResultList().size() > 0;
		}
		
		if(!ficheroSinCoste){
			Query query3 = entityManager
					.createQuery("SELECT distinct(ve) FROM VideoEspecial ve, Usuario u"
							+ " WHERE "
							+ "		ve.fichero.id = :idFichero"
							+ "		and u.id=:idUsuario"
							+ "		and ("
							+ "			ve.usuario.id = :idUsuario "
							+ "			or "
							+ "			((u.entrena.id = ve.club.id or u.profesor.id = ve.club.id) and ve.usuario is null)"
							+ "		)"
							+ "");
			query3.setParameter("idUsuario", idUsuario);
			query3.setParameter("idFichero", idFichero);
			ficheroSinCoste |= query3.getResultList().size() > 0;
		}
		
		return ficheroSinCoste;
	}

}
