package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Grado;

@Local
public interface GradoDAOLocal {

	public abstract List<Grado> buscarTodosGrados();

	Grado anadir(Grado elemento);

	Grado modificar(Grado elemento);

	void borrar(int id);

	Grado obtener(int id);


}