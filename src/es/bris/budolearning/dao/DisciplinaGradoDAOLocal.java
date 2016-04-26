package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.DisciplinaGrado;

@Local
public interface DisciplinaGradoDAOLocal {

	DisciplinaGrado obtener(int id);

	List<DisciplinaGrado> buscarTodasDisciplinasGrados();

	DisciplinaGrado obtenerDisciplinaGrado(int idDisciplina, int idGrado);

	List<DisciplinaGrado> buscarTodasDisciplinasGrados(int idDisciplina);

	void borrar(int idDisciplinaGrado);

	DisciplinaGrado anadir(DisciplinaGrado disciplinaGrado);

}
