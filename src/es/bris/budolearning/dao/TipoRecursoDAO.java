package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.TipoRecurso;

/**
 * Session Bean implementation class TipoRecursoDAO
 */
@Stateless
@LocalBean
public class TipoRecursoDAO implements TipoRecursoDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public TipoRecursoDAO() {
        
    }
    
	@Override
	public TipoRecurso anadir(TipoRecurso elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public TipoRecurso modificar(TipoRecurso elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public TipoRecurso obtener(int id) {
		return entityManager.find(TipoRecurso.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<TipoRecurso> buscarTodosTipoRecursos (){
    	Query query = entityManager.createQuery("SELECT tr FROM TipoRecurso tr ");
    	return query.getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public TipoRecurso buscarTipoRecurso (String nombre){
    	Query query = entityManager.createQuery("SELECT tr FROM TipoRecurso tr where tr.nombre=:nombre");
    	query.setParameter("nombre", nombre);
    	List<TipoRecurso> lista = query.getResultList();
    	if(lista.size() > 0)
    		return (TipoRecurso) lista.get(0);
    	else
    		return null;
    }

}
