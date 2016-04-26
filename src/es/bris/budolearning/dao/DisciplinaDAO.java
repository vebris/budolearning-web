package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.DisciplinaGrado;

/**
 * Session Bean implementation class DisciplinaDAO
 */
@Stateless
@LocalBean
public class DisciplinaDAO implements DisciplinaDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public DisciplinaDAO() {
        
    }
    
    @Override
	public Disciplina anadir(Disciplina elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Disciplina modificar(Disciplina elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public boolean borrar(int id) {
		boolean borrado = false;
		Disciplina disciplina = obtener(id);
		if(disciplina != null){
			for(DisciplinaGrado dg:disciplina.getDisciplinaGrados()){
				entityManager.remove(dg);
			}
			disciplina.getDisciplinaGrados().clear();
			entityManager.remove(disciplina);
			borrado = true;
		}
		return borrado;
	}

	@Override
	public Disciplina obtener(int id) {
		Disciplina disciplina = entityManager.find(Disciplina.class, id);
		return disciplina;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Disciplina> buscarTodasDisciplinas (){
    	Query query = entityManager.createQuery("SELECT u FROM Disciplina u order by u.nombre");
    	return query.getResultList();
    }

	
	@SuppressWarnings("unchecked")
	public List<Disciplina> disciplinasUsuario(Integer idUser) {
		String sql = 
				"select distinct(d) "
				+ " from Usuario u INNER JOIN u.disciplinaGrados dg INNER JOIN dg.disciplina d"
				+ " where "
				+ "		u.id = :idUsuario "
				+ " order by d.id asc";
		Query query =  entityManager.createQuery(sql);
		query.setParameter("idUsuario", idUser);
		return (List<Disciplina>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Disciplina> listarTodasDisciplinas() {
		Query query = entityManager.createQuery("SELECT u FROM Disciplina u order by u.id");
    	return query.getResultList();
	}
	
}
