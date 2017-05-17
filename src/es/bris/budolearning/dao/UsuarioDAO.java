package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Usuario;

/**
 * Session Bean implementation class UsuarioDAO
 */
@Stateless
@LocalBean
public class UsuarioDAO implements UsuarioDAOLocal {

	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public UsuarioDAO() {
        
    }
    
    @Override
	public Usuario anadir(Usuario elemento) {
    	if(elemento.getProfesor() == null || elemento.getProfesor().getId() == 0){
    		elemento.setProfesor(null);
    	}
    	return entityManager.merge(elemento);
	}

	@Override
	public Usuario modificar(Usuario elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public boolean borrar(int id) {
		boolean borrado = false;
		Usuario usuario = obtener(id);
		if(usuario != null){
			usuario.getDisciplinaGrados().clear();
			entityManager.remove(usuario);
			borrado = true;
		}
		return borrado;
	}

	@Override
	public Usuario obtener(int id) {
		return entityManager.find(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
    public List<Usuario> buscarUsuarios (String login){
    	Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
    	query.setParameter("login", login);
    	return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Usuario> buscarTodosUsuarios (){
    	Query query = entityManager.createQuery("SELECT u FROM Usuario u ");
    	return query.getResultList();
    }

	@Override
	public Usuario borrarGrado(int idUsuario, DisciplinaGrado disciplinaGrado) {
		Usuario usuario = obtener(idUsuario);
		usuario.getDisciplinaGrados().remove(disciplinaGrado);
		usuario = modificar(usuario);
		return usuario;
	}

	@Override
	public Usuario anadirGrado(int idUsuario, DisciplinaGrado disciplinaGrado) {
		Usuario usuario = obtener(idUsuario);
		usuario.getDisciplinaGrados().add(disciplinaGrado);
		usuario = modificar(usuario);
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarTodosUsuariosClub(int idClub) {
		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.entrena.id = :idClub)");
    	query.setParameter("idClub", idClub);
    	return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarTodosUsuariosClub(Usuario profesor) {
		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.entrena.id = :idClub)");
    	query.setParameter("idClub", profesor.getProfesor().getId());
    	return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verFichero(Usuario u, int idFichero) {
		Query query =  entityManager.createQuery("SELECT dg.id FROM Fichero f INNER JOIN f.recurso r "
				+ " INNER JOIN r.disciplinaGrados dg "
				+ " WHERE f.id = :idFichero ");
		query.setParameter("idFichero", idFichero);
		List<Integer> disciplinaGradosFichero = query.getResultList();
		
		Query query2 = entityManager.createQuery("SELECT DISTINCT dg2.id FROM Usuario u INNER JOIN u.disciplinaGrados dg2 WHERE u.id=:idUsuario");
		query2.setParameter("idUsuario", u.getId());
		List<Integer> disciplinaGradosUsuario = query2.getResultList();
		
		for(Integer d1: disciplinaGradosFichero){
			for(Integer d2: disciplinaGradosUsuario){
				if(d1.compareTo(d2) == 0) return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario buscarProfesor(Club club) {
		if(club == null) return null;
		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.profesor.id = :club and activo=1");
    	query.setParameter("club", club.getId());
    	List<Usuario> profesores = query.getResultList();
    	if(profesores.size() > 0)
    		return profesores.get(0);
    	else 
    		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario buscarAdministrador() {
		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.rol = 'ADMINISTRADOR' and activo=1");
    	List<Usuario> profesores = query.getResultList();
    	if(profesores.size() > 0)
    		return profesores.get(0);
    	else 
    		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarTodosUsuariosEstilo(int id) {
		Query query = entityManager.createQuery("SELECT distinct(u) FROM Usuario u inner join u.disciplinaGrados as dg inner join dg.disciplina as d WHERE d.id = :idDisciplina)");
    	query.setParameter("idDisciplina", id);
    	return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario buscarUsuariosByEmail(String mail) {
		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail");
    	query.setParameter("mail", mail);
    	List<Usuario> usuarios = query.getResultList();
    	if(usuarios.size() == 1){
    		return usuarios.get(0);
    	}
    	return null;
	}
}
