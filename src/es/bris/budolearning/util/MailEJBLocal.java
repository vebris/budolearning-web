package es.bris.budolearning.util;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.Curso;
import es.bris.budolearning.model.Usuario;

@Local
public interface MailEJBLocal {

	public void enviarMailUsuarioCreacion(String mail, Usuario usuario);

	public void enviarMailUsuarioBorrado(String mail, Usuario usuario);

	public void enviarMailUsuarioMasInfo(String mail, Usuario usuario);

	public void enviarMailUsuarioRegistro(String mail, Usuario usuario, Usuario profesor);
	
	public void enviarMailUsuarioContrasena(String mail, Usuario usuario, String password);

	public int enviarMailCurso(Curso curso, List<Usuario> usuarios);

	

}