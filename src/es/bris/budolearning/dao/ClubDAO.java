package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Club;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class ClubDAO implements ClubDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public ClubDAO() {
        
    }
    
	@Override
	public Club anadir(Club elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Club modificar(Club elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Club obtener(int id) {
		return entityManager.find(Club.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Club> buscarTodosClubs (){
    	Query query = entityManager.createQuery("SELECT u FROM Club u order by u.gradoProfesor desc, u.id asc");
    	return query.getResultList();
    }

}
