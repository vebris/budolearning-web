package es.bris.budolearning.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.bris.budolearning.model.Android;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class AndroidDAO implements AndroidDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public AndroidDAO() {
        
    }
    
	@Override
	public Android anadir(Android elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Android modificar(Android elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Android obtener(int id) {
		return entityManager.find(Android.class, id);
	}

}
