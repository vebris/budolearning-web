package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Articulo;
import es.bris.budolearning.model.Usuario;

@Local
public interface ArticuloDAOLocal {

	Articulo anadir(Articulo elemento);

	Articulo modificar(Articulo elemento);

	void borrar(int id);

	Articulo obtener(int id);
	
	List<Articulo> obtenerTodos();

	List<Articulo> obtenerTodosCorto(Usuario usuario);

}