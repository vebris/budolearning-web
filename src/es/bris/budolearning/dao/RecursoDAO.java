package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Recurso;

/**
 * Session Bean implementation class RecursoDAO
 */
@Stateless
@LocalBean
public class RecursoDAO implements RecursoDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public RecursoDAO() {
        
    }
    
    @Override
	public Recurso anadir(Recurso elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public Recurso modificar(Recurso elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public boolean borrar(int id) {
		boolean borrado = false;
		Recurso recurso = obtener(id);
		if(recurso != null){
			recurso.getDisciplinaGrados().clear();
			entityManager.remove(recurso);
			borrado = true;
		}
		return borrado;
	}

	@Override
	public Recurso obtener(int id) {
		Recurso recurso = entityManager.find(Recurso.class, id); 
		return recurso;
	}

	@SuppressWarnings("unchecked")
	@Override
    public List<Recurso> buscarRecursos (String nombre){
    	Query query = entityManager.createQuery("SELECT r FROM Recurso r WHERE r.nombre = :nombre order by r.id asc");
    	query.setParameter("nombre", nombre);
    	return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Recurso> buscarTodosRecursos (){
    	Query query = entityManager.createQuery("SELECT r FROM Recurso r order by r.id asc");
    	return query.getResultList();
    }

	@Override
	public Recurso borrarGrado(int idRecurso, DisciplinaGrado disciplinaGrado) {
		Recurso recurso = obtener(idRecurso);
		recurso.getDisciplinaGrados().remove(disciplinaGrado);
		recurso = modificar(recurso);
		return recurso;
	}

	@Override
	public Recurso anadirGrado(int idRecurso, DisciplinaGrado disciplinaGrado) {
		Recurso recurso = obtener(idRecurso);
		recurso.getDisciplinaGrados().add(disciplinaGrado);
		recurso = modificar(recurso);
		return recurso;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recurso> buscarRecursosDisciplinaDisciplinaGrado(int idDisciplina, int idDisciplinaGrado) {
		Query query = entityManager.createQuery("SELECT distinct(r) FROM Recurso r " + 
					" join r.disciplinaGrados dg " + 
					" where " + 
					"   (:idDisciplinaGrado = 0 OR dg.id=:idDisciplinaGrado) " + 
					"   and " + 
					"   (:idDisciplina = 0 OR dg.disciplina.id=:idDisciplina) " +
					" order by r.id asc");
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idDisciplinaGrado", idDisciplinaGrado);
    	return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Recurso> buscarRecursosDisciplinaGrado(int idDisciplina, int idGrado) {
		Query query = entityManager.createQuery("SELECT distinct(r) FROM Recurso r " + 
					" join r.disciplinaGrados dg " + 
					" where " + 
					"   (:idGrado = 0 OR dg.grado.id=:idGrado) " + 
					"   and " + 
					"   (:idDisciplina = 0 OR dg.disciplina.id=:idDisciplina) " +
					" order by r.enPrograma desc, r.tipo.id asc, r.id asc" );
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
    	return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Recurso> buscarRecursosDisciplina(int idDisciplina) {
		if(idDisciplina > 0){
			Query query = entityManager.createQuery("SELECT distinct(r) FROM Recurso r " + 
						" join r.disciplinaGrados dg " + 
						" where " + 
						"   (:idDisciplina = 0 OR dg.disciplina.id=:idDisciplina) " +
						" order by r.enPrograma desc, dg.grado.id desc");
			query.setParameter("idDisciplina", idDisciplina);
			List<Recurso> recursos = query.getResultList();
			return recursos;
		} else {
			Query query = entityManager.createQuery("SELECT distinct(r) FROM Recurso r " + 
					" left join r.disciplinaGrados dg " + 
					" where " + 
					"   dg is null " +
					" order by r.id asc");
			return query.getResultList();
		}
	}

}
