package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.TipoRecurso;

@Local
public interface TipoRecursoDAOLocal {

	public abstract List<TipoRecurso> buscarTodosTipoRecursos();

	TipoRecurso anadir(TipoRecurso elemento);

	TipoRecurso modificar(TipoRecurso elemento);

	void borrar(int id);

	TipoRecurso obtener(int id);

}