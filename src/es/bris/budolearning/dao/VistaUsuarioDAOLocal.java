package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Android;
import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Recurso;

@Local
public interface VistaUsuarioDAOLocal {

	public abstract List<DisciplinaGrado> buscarDisciplinasGradoUsuario(int idUsuario);

	public abstract List<Recurso> buscarRecursosUsuarioDisciplinaGrado(int idUsuario, int idDisciplina, int idGrado);

	public abstract List<DisciplinaGrado> buscarDisciplinasGradoRecurso(int idDisciplina, int idRecurso);

	public abstract List<Recurso> buscarRecursosUsuarioDisciplina(int idUsuario, int idDisciplina);

	public abstract int buscarGradoMaximoDisciplina(int idUsuario, int idDisciplina);
	
	public abstract Android obtenerUltimaVersion();
	
	public abstract List<Club> clubes();

}
