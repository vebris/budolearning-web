package es.bris.budolearning.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.app.Constants;
import es.bris.budolearning.model.Puntos;
import es.bris.budolearning.model.PuntosCompletos;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class PuntosDAO implements PuntosDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public PuntosDAO() {
        
    }
    
	@Override
	public Puntos anadir(Puntos elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Puntos modificar(Puntos elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Puntos obtener(int id) {
		return entityManager.find(Puntos.class, id);
	}

	@Override
	public int saldo(int idUsuario) {
		Query query = entityManager.createQuery("FROM Puntos p where p.idUsuario=:idUsuario");
		query.setParameter("idUsuario", idUsuario);
		int puntos = 0;
		for(Object p:query.getResultList()){
			puntos += ((Puntos)p).getPuntos();
		}
    	return puntos;
	}
	
	@Override
	public int puntosDiaTipo(int idUsuario, String tipo, Date fecha) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date fecha1 = c.getTime();
		c.add(Calendar.DATE, 1);
		Date fecha2 = c.getTime();
		Query query = entityManager.createQuery("FROM Puntos p where p.idUsuario=:idUsuario and p.tipo=:tipo and p.fecha between :fecha1 and :fecha2)");
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("tipo", tipo);
		query.setParameter("fecha1", fecha1);
		query.setParameter("fecha2", fecha2);
		int puntos = 0;
		for(Object p:query.getResultList()){
			puntos += ((Puntos)p).getPuntos();
		}
    	return puntos;
	}
	@Override
	public int puntosHoraTipo(int idUsuario, String tipo, Date fecha, int idFichero) {
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		Date fecha2 = c.getTime();
		c.add(Calendar.HOUR, -1);
		Date fecha1 = c.getTime();
		
		String sql = "FROM Puntos p where p.idUsuario=:idUsuario and p.tipo=:tipo and p.fecha between :fecha1 and :fecha2";
		if(idFichero > 0){
			sql += " and p.idFichero = :idFichero";  
		}
		
		Query query = entityManager.createQuery(sql);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("tipo", tipo);
		query.setParameter("fecha1", fecha1);
		query.setParameter("fecha2", fecha2);
		if(idFichero > 0){
			query.setParameter("idFichero", idFichero);
		}
		
		int puntos = 0;
		for(Object p:query.getResultList()){
			puntos += ((Puntos)p).getPuntos();
		}
    	return puntos;
	}

	@Override
	public void anadir(int idUsuario, Date date, String tipo, int cantidad, Integer idFichero) {
		boolean insertar = true;
		if(tipo.equals(Constants.TIPO_PUNTOS_LOGIN) || tipo.equals(Constants.TIPO_PUNTOS_BONUS) ){
			insertar = puntosDiaTipo(idUsuario, tipo, date) == 0;
		}
		if(tipo.equals(Constants.TIPO_PUNTOS_VIDEO) || tipo.equals(Constants.TIPO_PUNTOS_PDF) ){
			insertar = puntosHoraTipo(idUsuario, tipo, date, idFichero) == 0;
		}
		
		if(insertar){
			Puntos p = new Puntos();
			p.setIdUsuario(idUsuario);
			p.setTipo(tipo);
			p.setPuntos(cantidad);
			p.setFecha(date);
			p.setIdFichero(idFichero);
			anadir(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PuntosCompletos> getPuntos(int idUsuario) {
		Query query = entityManager.createQuery("select new es.bris.budolearning.model.PuntosCompletos(max(p.id), '', max(p.idUsuario), max(p.fecha), sum(p.puntos)) FROM Puntos p where p.idUsuario=:idUsuario group by date(p.fecha) order by date(p.fecha) desc");
		query.setParameter("idUsuario", idUsuario);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<PuntosCompletos> getListadoPuntos(int idUsuario) {
		Query query = entityManager.createQuery("select new es.bris.budolearning.model.PuntosCompletos(p.id, p.tipo, p.idUsuario, p.fecha, p.puntos, p.idFichero, (select distinct(f.descripcion) from Fichero f where f.id=p.idFichero)) FROM Puntos p where p.idUsuario=:idUsuario order by p.fecha desc");
		query.setParameter("idUsuario", idUsuario);
		return query.getResultList();
	}

}
