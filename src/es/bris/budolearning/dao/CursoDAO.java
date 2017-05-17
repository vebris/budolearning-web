package es.bris.budolearning.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Curso;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class CursoDAO implements CursoDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public CursoDAO() {
        
    }
    
	@Override
	public Curso anadir(Curso elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Curso modificar(Curso elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Curso obtener(int id) {
		return entityManager.find(Curso.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Curso> buscarTodosCursos (){
    	Query query = entityManager.createQuery("SELECT u FROM Curso u order by u.id desc");
    	return query.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> buscarCursos(Integer mes, Integer ano) {
		Query query = entityManager.createQuery("SELECT u FROM Curso u where u.inicio between :inicio and :fin order by u.inicio");
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MONTH, mes);
		fechaInicio.set(Calendar.YEAR, ano);
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.setTimeInMillis(0);
		fechaFin.set(Calendar.DAY_OF_MONTH, 1);
		fechaFin.set(Calendar.MONTH, 1);
		fechaFin.set(Calendar.YEAR, ano+2);
		query.setParameter("inicio", fechaInicio.getTime());
		query.setParameter("fin", fechaFin.getTime());
    	return query.getResultList();
	}

}
