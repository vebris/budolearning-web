package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Curso;

@Local
public interface CursoDAOLocal {

	public abstract List<Curso> buscarTodosCursos();

	Curso anadir(Curso elemento);

	Curso modificar(Curso elemento);

	void borrar(int id);

	Curso obtener(int id);

	public abstract List<Curso> buscarCursos(Integer mes, Integer ano);

}