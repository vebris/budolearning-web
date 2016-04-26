package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Club;

@Local
public interface ClubDAOLocal {

	public abstract List<Club> buscarTodosClubs();

	Club anadir(Club elemento);

	Club modificar(Club elemento);

	void borrar(int id);

	Club obtener(int id);

}