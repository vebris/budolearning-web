package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Usuario;

@Local
public interface UsuarioDAOLocal {

	public abstract List<Usuario> buscarUsuarios(String login);

	public abstract List<Usuario> buscarTodosUsuarios();

	boolean borrar(int id);

	Usuario modificar(Usuario elemento);

	Usuario anadir(Usuario elemento);

	Usuario obtener(int id);
	
	Usuario anadirGrado(int idUsuario, DisciplinaGrado disciplinaGrado);

	Usuario borrarGrado(int idUsuario, DisciplinaGrado disciplinaGrado);

	public abstract List<Usuario> buscarTodosUsuariosClub(Usuario profesor);
	public abstract List<Usuario> buscarTodosUsuariosClub(int idClub);

	public abstract boolean verFichero(Usuario u, int idFichero);

	public abstract Usuario buscarProfesor(Club club);

	public abstract Usuario buscarAdministrador();

	public abstract List<Usuario> buscarTodosUsuariosEstilo(int id);

	public abstract Usuario buscarUsuariosByEmail(String mail);

	

}
