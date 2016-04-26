package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.DisciplinaGrado;

/**
 * Session Bean implementation class DisciplinaDAO
 */
@Stateless
@LocalBean
public class DisciplinaGradoDAO implements DisciplinaGradoDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public DisciplinaGradoDAO() {
        
    }
    
    @Override
	public DisciplinaGrado anadir(DisciplinaGrado disciplinaGrado) {
		return entityManager.merge(disciplinaGrado);
	}

	@Override
	public void borrar(int idDisciplinaGrado) {
		Query query = entityManager.createQuery(
			      "DELETE FROM DisciplinaGrado dg WHERE dg.id = :id");
		query.setParameter("id", idDisciplinaGrado).executeUpdate();
	}

    @Override
	public DisciplinaGrado obtener(int idDisciplinaGrado) {
		DisciplinaGrado disciplinaGrado = entityManager.find(DisciplinaGrado.class, idDisciplinaGrado);
		return disciplinaGrado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<DisciplinaGrado> buscarTodasDisciplinasGrados (){
    	Query query = entityManager.createQuery("SELECT u FROM DisciplinaGrado u ");
    	return query.getResultList();
    }

	@Override
	public DisciplinaGrado obtenerDisciplinaGrado (int idDisciplina, int idGrado){
		Query query = entityManager.createQuery("from DisciplinaGrado dg where dg.disciplina.id=:idDisciplina and dg.grado.id=:idGrado");
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
		return (DisciplinaGrado) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DisciplinaGrado> buscarTodasDisciplinasGrados(int idDisciplina) {
		Query query = entityManager.createQuery("from DisciplinaGrado dg where dg.disciplina.id=:idDisciplina and dg.grado.id > 1");
		query.setParameter("idDisciplina", idDisciplina);
		return query.getResultList();
	}

}
