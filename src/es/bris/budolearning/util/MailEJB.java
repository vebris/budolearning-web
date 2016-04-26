package es.bris.budolearning.util;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import es.bris.budolearning.model.Curso;
import es.bris.budolearning.model.Usuario;

/**
 * Session Bean implementation class ClubDAO
 */
@Stateless
@LocalBean
public class MailEJB implements MailEJBLocal {

	@Resource(mappedName = "java:jboss/mail/es.bris.budolearning")
    javax.mail.Session mailSession;
	
	/**
     * Default constructor. 
     */
    public MailEJB() {
        
    }
    
    @Override
    public void enviarMailUsuarioCreacion(String mail, Usuario usuario){
		MimeMessage message = new MimeMessage(mailSession);
		try {
			//message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
			InternetAddress[] address = {new InternetAddress(mail)};
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("[BudoLearning] Usuario Activo");
			message.setSentDate(new java.util.Date());
			String sms = "¡Bienvenido!<br/><br/>" +
				"Su profesor ha habilitado su acceso y actualizado su grado.<br/><br/>" + 
				"Esperamos que disfrute de su experiencia. <br/><br/><br/>" +
				"<b>Información de la cuenta</b><br/><br/>" +
				"<b>Nombre de usuario:</b> " + usuario.getLogin() + "<br/>" +
				"<b>URL</b>: http://budolearning.noip.me" + 
				"<br/><br/>Saludos.<br/>BUDOLEARNING";			
			message.setContent(sms, "text/html");
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
	
    @Override
	public void enviarMailUsuarioBorrado(String mail, Usuario usuario){
		MimeMessage message = new MimeMessage(mailSession);
		try {
			//message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
			InternetAddress[] address = {new InternetAddress(mail)};
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("[BudoLearning] Usuario Eliminado");
			message.setSentDate(new java.util.Date());
			String sms = "Buenos días,<br/><br/>" +
				"Sus datos personales no pueden ser autenticados por lo que hemos procedido a eliminar su usuario.<br/><br/>" + 
				"Si sigue interesado en realizar el registro puede hacerlo en cualquier momento." +
				"<br/><br/>Saludos.<br/>BUDOLEARNING";			
			message.setContent(sms, "text/html");
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
	
    @Override
	public void enviarMailUsuarioMasInfo(String mail, Usuario usuario){
		MimeMessage message = new MimeMessage(mailSession);
		try {
			//message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
			InternetAddress[] address = {new InternetAddress(mail)};
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("[BudoLearning] Petición de Información");
			message.setSentDate(new java.util.Date());
			String sms = "Buenos días,<br/><br/>" +
				"Necesitamos cotejar sus datos personales, grado y titulaciones. <br/><br/>" + 
				"Para ello, puede responder al correo con toda la información que considere oportuna. <br/><br/>" +
				"Saludos.<br/><br/>BUDOLEARNING";			
			message.setContent(sms, "text/html");
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
	
    @Override
	public void enviarMailUsuarioRegistro(String mail, Usuario usuario, Usuario profesor){
		MimeMessage message = new MimeMessage(mailSession);
		try {
			InternetAddress[] address = {new InternetAddress(mail)};
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("[BudoLearning] Registro");
			message.setSentDate(new java.util.Date());
			String sms = "¡Bienvenido!<br/><br/>" +
					"Gracias por unirse a nosotros. <br/><br/>" + 
					"Falta la validación del profesor asociado al Club ("+usuario.getEntrena().getNombre()+") "+ 
					" su grado y datos personales. <br/><br/>" +
					"En cuanto esté realizada la validación le enviaremos un correo para que informale de que puede acceder a la aplicación." + 
					"<br/><br/>Saludos.<br/>BUDOLEARNING";
			message.setContent(sms, "text/html");
			Transport.send(message);
			
			InternetAddress[] address2 = {new InternetAddress(profesor.getMail())};
			message.setRecipients(Message.RecipientType.TO, address2);
			message.setSubject("[BudoLearning] Registro 2");
			message.setSentDate(new java.util.Date());
			sms = "Buenos días " + profesor.getNombre() + ",<br/><br/>" +
					"Has de validar al usuario " + usuario.getLogin() + " (" + usuario.getNombre() + " " + usuario.getApellido1() + " " + usuario.getApellido1() + ") para que pueda acceder a la aplicación." +
					"<br/><br/>Saludos.<br/>BUDOLEARNING";
					;
			message.setContent(sms, "text/html");
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
	
    @Override
	public int enviarMailCurso(Curso curso, List<Usuario> usuarios){
		MimeMessage message = new MimeMessage(mailSession);
		try {
			for(Usuario usuario:usuarios){
				if(usuario.getActivo()){
					InternetAddress[] address = {new InternetAddress(usuario.getMail())};
					message.setRecipients(Message.RecipientType.BCC, address);
				}
			}
			if(message.getRecipients(Message.RecipientType.BCC).length == 0){
				return 0;
			}
			message.setSubject("[BudoLearning] Curso de " + curso.getDisciplina().getNombre());
			message.setSentDate(new java.util.Date());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String sms = "Buenos días,<br/><br/>" +
					" Nombre del Curso: " + curso.getNombre() +
					" Descripcion: " + curso.getDescripcion() +
					" Profesor/es: " + curso.getProfesor() +
					" Duración : " + sdf.format(curso.getInicio()) + " a " + sdf.format(curso.getFin()) + 
					" Lugar: " + curso.getDireccion() +
					" Precio " + curso.getPrecios() +
				"<br/><br/>Saludos.<br/>BUDOLEARNING";
			message.setContent(sms, "text/html");
			Transport.send(message);
			return message.getRecipients(Message.RecipientType.BCC).length;
		} catch (MessagingException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	@Override
	public void enviarMailUsuarioContrasena(String mail, Usuario usuario,
			String password) {
		MimeMessage message = new MimeMessage(mailSession);
		try {
			InternetAddress[] address = {new InternetAddress(mail)};
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("[BudoLearning] Cambio de Contraseña");
			message.setSentDate(new java.util.Date());
			String sms = "¡Bienvenido!<br/><br/>" +
					"Gracias por unirse a nosotros. <br/><br/>" + 
					"Login = "+usuario.getLogin()+" <br/>"+ 
					"Password = "+ password+"<br/><br/>" +
					"Debes realizar el cambio de contraseña tan pronto accedas a la aplicación." + 
					"<br/><br/>Saludos.<br/>BUDOLEARNING";
			message.setContent(sms, "text/html");
			Transport.send(message);
			
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		
	}
    
	

}
