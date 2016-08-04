package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Menu;

/**
 * Session Bean implementation class GradoDAO
 */
@Stateless
@LocalBean
public class MenuDAO implements MenuDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public MenuDAO() {
        
    }
    
	@Override
	public Menu anadir(Menu elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Menu modificar(Menu elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Menu obtener(int id) {
		return entityManager.find(Menu.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Menu> buscarTodos(){
    	Query query = entityManager.createQuery("FROM Menu m order by m.id asc");
    	return query.getResultList();
    }

}
