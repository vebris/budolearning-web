package es.bris.budolearning.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.EstadisticaMensual;
import es.bris.budolearning.model.EstadisticaUsuario;
import es.bris.budolearning.model.LogDownloadFile;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class LogDownloadFileDAO implements LogDownloadFileDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public LogDownloadFileDAO() {
        
    }
    
	@Override
	public LogDownloadFile anadir(LogDownloadFile elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public LogDownloadFile modificar(LogDownloadFile elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public LogDownloadFile obtener(int id) {
		return entityManager.find(LogDownloadFile.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<LogDownloadFile> buscarTodosLogs (){
    	Query query = entityManager.createQuery("SELECT u FROM LogDownloadFile u order by u.id asc");
    	return query.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaMensual> porMeses() {
		Query query = entityManager.createQuery("select new es.bris.budolearning.model.EstadisticaMensual (DATE_FORMAT(fecha, '%m-%Y'), count(descargado), sum(descargado)) from LogDownloadFile group by DATE_FORMAT(fecha, '%m-%Y') order by DATE_FORMAT(fecha, '%m-%Y') asc");
		return query.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaUsuario> porUsuarioTotal() {
		Query query = entityManager.createQuery("select new es.bris.budolearning.model.EstadisticaUsuario (concat(usuario.login,' - ',usuario.nombre,' ',usuario.apellido1,' ',usuario.apellido2), count(descargado), sum(descargado)) from LogDownloadFile group by usuario.login order by sum(descargado) desc ");
    	return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaUsuario> porUsuarioMes() {
		String sql = "select "
				+ "	new es.bris.budolearning.model.EstadisticaUsuario ("
				+ "		concat(usuario.login,' - ',usuario.nombre,' ',usuario.apellido1,' ',usuario.apellido2) AS usuario, "
				+ "		count(descargado) AS totalNumero, "
				+ "		sum(descargado) AS totalTamano"
				+ " ) "
				+ " from LogDownloadFile l "
				+ " where l.fecha >= :fecha"
				+ " group by usuario.login"
				+ " order by sum(descargado) desc ";
		Query query = entityManager.createQuery(sql);
		Calendar c = Calendar.getInstance();
		c.set(
				Calendar.getInstance().get(Calendar.YEAR), 
				Calendar.getInstance().get(Calendar.MONTH), 
				1, 0, 0, 0);
		query.setParameter("fecha", c.getTime());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaUsuario> porVideosTotal() {
		Query query = entityManager.createQuery("select new es.bris.budolearning.model.EstadisticaUsuario (fichero.descripcion, count(descargado), sum(descargado)) from LogDownloadFile group by fichero.descripcion order by sum(descargado) desc ");
    	return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaUsuario> porVideosMes() {
		String sql = "select "
				+ "	new es.bris.budolearning.model.EstadisticaUsuario ("
				+ "		fichero.descripcion AS usuario, "
				+ "		count(descargado) AS totalNumero, "
				+ "		sum(descargado) AS totalTamano"
				+ " ) "
				+ " from LogDownloadFile l "
				+ " where l.fecha >= :fecha"
				+ " group by fichero.descripcion"
				+ " order by sum(descargado) desc ";
		Query query = entityManager.createQuery(sql);
		Calendar c = Calendar.getInstance();
		c.set(
				Calendar.getInstance().get(Calendar.YEAR), 
				Calendar.getInstance().get(Calendar.MONTH), 
				1, 0, 0, 0);
		query.setParameter("fecha", c.getTime());
		return query.getResultList();
	}

}
