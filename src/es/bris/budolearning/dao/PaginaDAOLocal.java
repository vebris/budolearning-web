package es.bris.budolearning.dao;

import javax.ejb.Local;

import es.bris.budolearning.model.Pagina;

@Local
public interface PaginaDAOLocal {

	Pagina anadir(Pagina elemento);

	Pagina modificar(Pagina elemento);

	void borrar(int id);

	Pagina obtener(int id);


}