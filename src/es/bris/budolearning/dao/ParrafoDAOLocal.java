package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Parrafo;

@Local
public interface ParrafoDAOLocal {

	public abstract List<Parrafo> buscarTodos(int idPagina);

	Parrafo anadir(Parrafo elemento);

	Parrafo modificar(Parrafo elemento);

	void borrar(int id);

	Parrafo obtener(int id);


}