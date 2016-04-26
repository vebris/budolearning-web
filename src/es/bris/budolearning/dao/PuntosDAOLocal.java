package es.bris.budolearning.dao;

import java.util.Date;

import javax.ejb.Local;

import es.bris.budolearning.model.Puntos;

@Local
public interface PuntosDAOLocal {

	Puntos anadir(Puntos elemento);

	Puntos  modificar(Puntos  elemento);

	void borrar(int id);

	Puntos obtener(int id);
	
	int saldo(int idUsuario);

	int puntosDiaTipo(int idUsuario, String tipo, Date fecha);

	void anadir(int idUsuario, Date date, String tipo, int cantidad, Integer idFichero);

	int puntosHoraTipo(int idUsuario, String tipo, Date fecha, int idFichero);


}