package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Pagina;

/**
 * Session Bean implementation class PaginaDAO
 */
@Stateless
@LocalBean
public class PaginaDAO implements PaginaDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public PaginaDAO() {
        
    }
    
	@Override
	public Pagina anadir(Pagina elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Pagina modificar(Pagina elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Pagina obtener(int id) {
		return entityManager.find(Pagina.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pagina> listParent (Pagina elemento){
    	Query query = entityManager.createQuery("SELECT r FROM Pagina r WHERE r.parent.id = :parent_id order by r.id asc");
    	query.setParameter("parent_id", elemento.getId());
    	return query.getResultList();
    }

}
