package es.bris.budolearning.dao;

import javax.ejb.Local;

import es.bris.budolearning.model.Android;

@Local
public interface AndroidDAOLocal {

	Android anadir(Android elemento);

	Android modificar(Android elemento);

	void borrar(int id);

	Android obtener(int id);

}