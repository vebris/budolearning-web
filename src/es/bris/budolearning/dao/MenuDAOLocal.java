package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Menu;

@Local
public interface MenuDAOLocal {

	public abstract List<Menu> buscarTodos();

	Menu anadir(Menu elemento);

	Menu modificar(Menu elemento);

	void borrar(int id);

	Menu obtener(int id);


}