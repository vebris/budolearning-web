package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Disciplina;

@Local
public interface DisciplinaDAOLocal {

	List<Disciplina> buscarTodasDisciplinas();

	Disciplina anadir(Disciplina elemento);

	Disciplina modificar(Disciplina elemento);

	boolean borrar(int id);

	Disciplina obtener(int id);

}
