package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.Recurso;

@Local
public interface FicheroDAOLocal {

	public abstract List<Fichero> buscarFicheros(Recurso recurso);

	public abstract List<Fichero> buscarTodosFicheros();

	public abstract void borrar(int id);

	public abstract Fichero modificar(Fichero elemento);
	public abstract Fichero modificar(Fichero elemento, long tamano);

	public abstract Fichero anadir(Fichero elemento);
	public abstract Fichero anadir(Fichero elemento, long tamano);

	public abstract Fichero obtener(int id);

	public abstract List<Fichero> buscarFicherosCorto(Recurso recurso);

	public abstract Fichero obtenerCorto(int id);

}
