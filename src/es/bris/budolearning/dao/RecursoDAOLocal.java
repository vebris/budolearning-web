package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Recurso;

@Local
public interface RecursoDAOLocal {

	public abstract List<Recurso> buscarRecursos(String nombre);

	public abstract List<Recurso> buscarTodosRecursos();

	boolean borrar(int id);

	Recurso modificar(Recurso elemento);

	Recurso anadir(Recurso elemento);

	Recurso obtener(int id);
	
	Recurso anadirGrado(int idRecurso, DisciplinaGrado disciplinaGrado);

	Recurso borrarGrado(int idRecurso, DisciplinaGrado disciplinaGrado);

	public abstract List<Recurso> buscarRecursosDisciplinaDisciplinaGrado(int idDisciplina, int idDisciplinaGrado);

	public abstract List<Recurso> buscarRecursosDisciplina(int idDisciplina);

}
