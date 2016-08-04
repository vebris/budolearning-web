package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Parrafo;

/**
 * Session Bean implementation class ParrafoDAO
 */
@Stateless
@LocalBean
public class ParrafoDAO implements ParrafoDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public ParrafoDAO() {
        
    }
    
	@Override
	public Parrafo anadir(Parrafo elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Parrafo modificar(Parrafo elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Parrafo obtener(int id) {
		return entityManager.find(Parrafo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Parrafo> buscarTodos (int idPagina){
    	Query query = entityManager.createQuery("SELECT u FROM Parrafo u where u.pagina.id=:idPagina order by u.orden asc, u.id asc");
    	query.setParameter("idPagina", idPagina);
    	return query.getResultList();
    }

}
