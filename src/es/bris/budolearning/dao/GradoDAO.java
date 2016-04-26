package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Grado;

/**
 * Session Bean implementation class GradoDAO
 */
@Stateless
@LocalBean
public class GradoDAO implements GradoDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public GradoDAO() {
        
    }
    
	@Override
	public Grado anadir(Grado elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Grado modificar(Grado elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Grado obtener(int id) {
		return entityManager.find(Grado.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Grado> buscarTodosGrados (){
    	Query query = entityManager.createQuery("SELECT u FROM Grado u ");
    	return query.getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public List<Grado> listarGrados (){
    	Query query = entityManager.createQuery("SELECT u FROM Grado u order by id");
    	return query.getResultList();
    }

}
