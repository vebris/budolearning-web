package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Articulo;
import es.bris.budolearning.model.Usuario;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class ArticuloDAO implements ArticuloDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public ArticuloDAO() {
        
    }
    
	@Override
	public Articulo anadir(Articulo elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Articulo modificar(Articulo elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public void borrar(int id) {
		entityManager.remove(obtener(id));
	}

	@Override
	public Articulo obtener(int id) {
		return entityManager.find(Articulo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Articulo> obtenerTodos() {
		Query query = entityManager.createQuery("SELECT u FROM Articulo u ");
    	return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Articulo> obtenerTodosCorto(Usuario usuario) {
		boolean activos = !usuario.getRol().equalsIgnoreCase("ADMINISTRADOR");
		boolean visibleUsuarios = usuario.getRol().equalsIgnoreCase("USER");
		
		String sql = "SELECT new es.bris.budolearning.model.Articulo(a.id, a.activo, a.fecha, a.titulo, a.autor, a.visibleUsuarios) FROM Articulo a ";
		
		String where = " where 1=1 ";
		if(activos)
			where += " and a.activo=1";
		if(visibleUsuarios)
			where += " and a.visibleUsuarios=1";
		
		Query query = entityManager.createQuery(sql + where);
		return query.getResultList();
	}

}
