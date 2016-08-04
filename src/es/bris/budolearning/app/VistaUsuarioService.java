package es.bris.budolearning.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.security.auth.spi.Util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import es.bris.budolearning.dao.ArticuloDAO;
import es.bris.budolearning.dao.CursoDAO;
import es.bris.budolearning.dao.FicheroDAO;
import es.bris.budolearning.dao.LogDownloadFileDAO;
import es.bris.budolearning.dao.RecursoDAO;
import es.bris.budolearning.dao.UsuarioDAO;
import es.bris.budolearning.dao.VistaUsuarioDAO;
import es.bris.budolearning.model.Android;
import es.bris.budolearning.model.AndroidUsuario;
import es.bris.budolearning.model.Articulo;
import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.Curso;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.EstadisticaVideosUsuario;
import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.LogDownloadFile;
import es.bris.budolearning.model.Recurso;
import es.bris.budolearning.model.Usuario;
import es.bris.budolearning.util.MailEJB;
import es.bris.budolearning.util.MapUtil;
import es.bris.budolearning.util.StringUtil;


@Path("/")
@Produces("application/json")
public class VistaUsuarioService{
	
	private static Level LOG_LEVEL = Level.WARNING;
	
	@EJB
	private static MailEJB mailEJB;	
	@EJB
	private static VistaUsuarioDAO vistaUsuarioDAO;
	@EJB
	private static CursoDAO cursoDAO;
	@EJB
	private static UsuarioDAO usuarioDAO;
	@EJB
	private static LogDownloadFileDAO logDownloadFileDAO;
	@EJB
	private static FicheroDAO ficheroDAO;
	@EJB
	private static RecursoDAO recursoDAO;
	@EJB
	private static ArticuloDAO articuloDAO;
	
	public VistaUsuarioService(){}
	
	@GET
	@Path("/test/")
	public String[][] test(){
		String[][] str = new String[7][2];
		str[0][0] = "mailEJB";
		str[1][0] = "vistaUsuarioDAO";
		str[2][0] = "cursoDAO";
		str[3][0] = "usuarioDAO";
		str[4][0] = "logDownloadFileDAO";
		str[5][0] = "ficheroDAO";
		str[6][0] = "recursoDAO";
		str[0][1] = Boolean.toString(mailEJB != null);
		str[1][1] = Boolean.toString(vistaUsuarioDAO != null);
		str[2][1] = Boolean.toString(cursoDAO != null);
		str[3][1] = Boolean.toString(usuarioDAO != null);
		str[4][1] = Boolean.toString(logDownloadFileDAO != null);
		str[5][1] = Boolean.toString(ficheroDAO != null);
		str[6][1] = Boolean.toString(recursoDAO != null);
		
		return str;
	}
	
	/**
	 * @param login
	 * @param pass
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/loginAndroid/{version}/{login}/{pass}")
	public AndroidUsuario loginAndroid(@PathParam("version") Integer version, @PathParam("login") String login, @PathParam("pass") String pass){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + login + ") Service loginAndroid " + pass);
		
		String password = pass;
		while (password.length() < 32){
			password = '0' + password;
		}
		
		AndroidUsuario usuario = vistaUsuarioDAO.loginAndroid(login, password, version);
		if(usuario == null) {
			usuario = new AndroidUsuario();
		}
		return usuario;
	}
	
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/disciplinas/{idUser}")
	public List<Disciplina> getDisciplinas(@PathParam("idUser") Integer idUsuario){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getDisciplinas");
		List<Disciplina> lista = vistaUsuarioDAO.disciplinasUsuario(idUsuario);
		if(lista == null)
			lista = new ArrayList<Disciplina>();
		return lista;
	}
		
	/**
	 * @param idUsuario
	 * @param idDisciplina
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/grados/{idUser}/{idDisciplina}")
	public List<Grado> getGrados(@PathParam("idUser") Integer idUsuario, @PathParam("idDisciplina") Integer idDisciplina){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getGrados");
		List<Grado> lista = vistaUsuarioDAO.gradosDisciplinaUsuario(idUsuario, idDisciplina);
		if(lista == null)
			lista = new ArrayList<Grado>();
		return lista;
	}
	
	/**
	 * @param idUsuario
	 * @param idDisciplina
	 * @param idGrado
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/recursos/{idUser}/{idDisciplina}/{idGrado}")
	public List<Recurso> getRecursos(@PathParam("idUser") Integer idUsuario, @PathParam("idDisciplina") Integer idDisciplina, @PathParam("idGrado") Integer idGrado){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getRecursos");
		List<Recurso> lista = vistaUsuarioDAO.recursosGradoDisciplinaUsuario(idUsuario, idDisciplina, idGrado);
		if(lista == null)
			lista = new ArrayList<Recurso>();
		return lista;
	}
	
	/**
	 * @param idUsuario
	 * @param idRecurso
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/ficheros/{idUser}/{idRecurso}")
	public List<Fichero> getFicheros(@PathParam("idUser") Integer idUsuario, @PathParam("idRecurso") Integer idRecurso){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getFicheros");
		List<Fichero> lista = vistaUsuarioDAO.ficherosRecursoUsuario(idRecurso, idUsuario, 0);
		if(lista == null)
			lista = new ArrayList<Fichero>();
		return lista;
	}
	
	
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/clubes/{idUser}")
	public List<Club> getClubes(@PathParam("idUser") Integer idUsuario){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getClubes");
		List<Club> lista = vistaUsuarioDAO.clubes();
		if(lista == null)
			lista = new ArrayList<Club>();
		return lista;
	}
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/articulos/{idUser}")
	public List<Articulo> getArticulos(@PathParam("idUser") Integer idUsuario){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getArticulos");
		List<Articulo> lista = articuloDAO.obtenerTodosCorto(usuarioDAO.obtener(idUsuario));
		if(lista == null)
			lista = new ArrayList<Articulo>();
		return lista;
	}
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/articulo/{idUser}/{idArticulo}")
	public Articulo getArticulo(@PathParam("idUser") Integer idUsuario, @PathParam("idArticulo") Integer idArticulo){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getArticulos");
		return articuloDAO.obtener(idArticulo);
	}
	
	/**
	 * @param idUsuario
	 * @param mes
	 * @param ano
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/calendario/{idUser}/{mes}/{ano}")
	public List<Curso> calendario(@PathParam("idUser") Integer idUsuario,
			@PathParam("mes") Integer mes,
			@PathParam("ano") Integer ano
			){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service calendario");
		//Usuario usuario = usuarioDAO.obtener(idUser);
		List<Curso> cursos = cursoDAO.buscarCursos(mes, ano);
		return cursos;
	}
	
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/alumnos/{idUser}/{idClub}")
	public List<AndroidUsuario> getAlumnos(@PathParam("idUser") Integer idUsuario, @PathParam("idClub") Integer idClub){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getAlumnos (" + idClub + ")");
		List<AndroidUsuario> lista = vistaUsuarioDAO.getAlumnos(idUsuario, idClub);
		if(lista == null)
			lista = new ArrayList<AndroidUsuario>();
		return lista;
	}
	
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/alumnos/subirGrado/{idUser}/{idAlumno}/{idDisciplina}/{idGrado}")
	public AndroidUsuario getAlumnoSubirGrado(@PathParam("idUser") Integer idUsuario, @PathParam("idAlumno") Integer idAlumno, 
			@PathParam("idDisciplina") Integer idDisciplina, @PathParam("idGrado") Integer idGrado){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service getAlumnos (" + idAlumno + " " + idDisciplina + ")");
		return vistaUsuarioDAO.getAlumnoSubirGrado(idUsuario, idAlumno, idDisciplina, idGrado);
	}
	
	/**
	 * @param idUsuario
	 * @return
	 */
	@GET
    @Path("/VistaUsuarioService/estadisticas/{idUser}")
	public List<EstadisticaVideosUsuario> estadisticas(@PathParam("idUser") Integer idUsuario){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service estadisticas ");
		return vistaUsuarioDAO.getEstadisticas(idUsuario);
	}
	
	
	/* *****************************************************************************
	 * 
	 * 								USUARIO
	 * 
	 * *****************************************************************************
	 */
	@POST
    @Path("/UsuarioService/olvidoContrasena/{email}")
	public String cambioContrasena(
			@FormParam("email") String email,
			@Context HttpServletResponse servletResponse
	){
		Usuario usuario = usuarioDAO.buscarUsuariosByEmail(email);
		String password = StringUtil.randomString(10);
		String hash = Util.createPasswordHash("MD5", 
				Util.BASE16_ENCODING, null, null, password);
		usuario.setPassword(new String(hash));
		
		usuarioDAO.modificar(usuario);
		
		mailEJB.enviarMailUsuarioContrasena (email, usuario, password);
		
		return Boolean.TRUE.toString();
	}
	
	/**
	 * @param idUsuario
	 * @param idUser
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dni
	 * @param email
	 * @param direccion
	 * @param localidad
	 * @param telefono
	 * @param idClub
	 * @param servletResponse
	 * @return
	 */
	@POST
    @Path("/UsuarioService/nuevoUsuario/{idUser}")
	public String nuevoUsuario(
			@PathParam("idUser") Integer idUsuario,
			@FormParam("id") Integer idUser,
			@FormParam("login") String login,
			@FormParam("password") String password,
			@FormParam("rol") String rol,
			@FormParam("nombre") String nombre,
			@FormParam("apellido1") String apellido1,
			@FormParam("apellido2") String apellido2,
			@FormParam("dni") String dni,
			@FormParam("email") String email,
			@FormParam("direccion") String direccion,
			@FormParam("localidad") String localidad,
			@FormParam("telefono") String telefono,
			@FormParam("idClub") Integer idClub,
			@Context HttpServletResponse servletResponse
			){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + login + ") Service nuevoUsuario");
		try{
			Usuario usuario = usuarioDAO.buscarUsuariosByEmail(email); 
			if(usuario == null  || usuario.getLogin().isEmpty()){
				
				if(usuarioDAO.buscarUsuarios(login).size()>0){
					return "Ya existe un usuario registrado con ese login.";
				} else {
					usuario = new Usuario();
					usuario.setLogin(login);
					String pass = password;
					while (pass.length() < 32){
						pass = '0' + pass;
					}
					usuario.setPassword(pass);
					usuario.setRol(rol);
					usuario.setNombre(nombre);
					usuario.setApellido1(apellido1);
					usuario.setApellido2(apellido2);
					usuario.setDni(dni);
					usuario.setMail(email);
					usuario.setDireccion(direccion);
					usuario.setLocalidad(localidad);
					usuario.setTelefono(telefono);
					Club entrena = new Club();
					entrena.setId(idClub);
					usuario.setEntrena(entrena);
					try{
						usuarioDAO.anadir(usuario);
						Usuario responsable = usuarioDAO.buscarProfesor(usuario.getEntrena());
						if(responsable == null){
							responsable = usuarioDAO.buscarAdministrador();
						}
						Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service nuevoUsuario ENVIAR EMAILS");
						mailEJB.enviarMailUsuarioRegistro (email, usuario, responsable);
						
						Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service nuevoUsuario ==> " + Boolean.TRUE.toString());
						
						
						return Boolean.TRUE.toString();
					} catch(Exception e){
						return "Error al registrar al usuario.";
					}
				}
			} else {
				return "Ya existe un usuario registrado con ese email.";
			}
		}catch(Exception e){
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service nuevoUsuario ==> " + Boolean.FALSE.toString());
			return Boolean.FALSE.toString();
		}
	}
	/**
	 * @param idUsuario
	 * @param idUser
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dni
	 * @param email
	 * @param direccion
	 * @param localidad
	 * @param telefono
	 * @param idClub
	 * @param servletResponse
	 * @return
	 */
	@POST
    @Path("/UsuarioService/nuevoUsuario/null")
	public String nuevoUsuario2(
			@PathParam("idUser") Integer idUsuario,
			@FormParam("id") Integer idUser,
			@FormParam("login") String login,
			@FormParam("password") String password,
			@FormParam("rol") String rol,
			@FormParam("nombre") String nombre,
			@FormParam("apellido1") String apellido1,
			@FormParam("apellido2") String apellido2,
			@FormParam("dni") String dni,
			@FormParam("email") String email,
			@FormParam("direccion") String direccion,
			@FormParam("localidad") String localidad,
			@FormParam("telefono") String telefono,
			@FormParam("idClub") Integer idClub,
			@Context HttpServletResponse servletResponse
			){
		return nuevoUsuario(idUsuario, idUser, login, password, rol, nombre, apellido1, apellido2, dni, email, direccion, localidad, telefono, idClub, servletResponse);
	}
	/**
	 * @param idUsuario
	 * @param idUser
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dni
	 * @param email
	 * @param direccion
	 * @param localidad
	 * @param telefono
	 * @param servletResponse
	 * @return
	 */
	@POST
    @Path("/UsuarioService/modificarUsuario/{idUser}")
	public String modificarUsuario(
			@PathParam("idUser") Integer idUsuario,
			@FormParam("id") Integer idUser,
			@FormParam("nombre") String nombre,
			@FormParam("apellido1") String apellido1,
			@FormParam("apellido2") String apellido2,
			@FormParam("dni") String dni,
			@FormParam("email") String email,
			@FormParam("direccion") String direccion,
			@FormParam("localidad") String localidad,
			@FormParam("telefono") String telefono,
			@FormParam("idClub") Integer idClub,
			@Context HttpServletResponse servletResponse
			){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario");
		try{
			Usuario usuario = usuarioDAO.obtener(idUser);
			usuario.setNombre(nombre);
			usuario.setApellido1(apellido1);
			usuario.setApellido2(apellido2);
			usuario.setDni(dni);
			usuario.setMail(email);
			usuario.setDireccion(direccion);
			usuario.setLocalidad(localidad);
			usuario.setTelefono(telefono);
			Club entrena = new Club();
			entrena.setId(idClub);
			usuario.setEntrena(entrena);
			usuarioDAO.modificar(usuario);
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario ==> " + Boolean.TRUE.toString());
			return Boolean.TRUE.toString();
		}catch(Exception e){
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario ==> " + Boolean.FALSE.toString());
			return Boolean.FALSE.toString();
		}
	}
	/**
	 * @param idUsuario
	 * @param idUser
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dni
	 * @param email
	 * @param direccion
	 * @param localidad
	 * @param telefono
	 * @param servletResponse
	 * @return
	 */
	@POST
    @Path("/UsuarioService/activarUsuario/{idUser}")
	public String activarUsuario(
			@PathParam("idUser") Integer idUsuario,
			@FormParam("id") Integer idUser,
			@Context HttpServletResponse servletResponse
			){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario");
		try{
			Usuario usuario = usuarioDAO.obtener(idUser);
			usuario.setActivo(!usuario.getActivo());
			usuarioDAO.modificar(usuario);
			
			mailEJB.enviarMailUsuarioCreacion(usuario.getMail(), usuario);
			
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario ==> " + Boolean.TRUE.toString());
			return Boolean.TRUE.toString();
		}catch(Exception e){
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario ==> " + Boolean.FALSE.toString());
			return Boolean.FALSE.toString();
		}
	}
	
	/**
	 * @param idUsuario
	 * @param idUser
	 * @param login
	 * @param oldPass
	 * @param newPass
	 * @param servletResponse
	 * @return
	 */
	@POST
    @Path("/UsuarioService/cambiarPassword/{idUser}")
	public String cambiarPasswordUsuario(
			@PathParam("idUser") Integer idUsuario,
			@FormParam("id") Integer idUser,
			@FormParam("login") String login,
			@FormParam("oldPass") String oldPass,
			@FormParam("newPass") String newPass,
			@Context HttpServletResponse servletResponse
			){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service cambiarPasswordrUsuario");
		try{
			Usuario usuario = usuarioDAO.obtener(idUser);
			if(usuario.getLogin().equalsIgnoreCase(login)
					&& usuario.getPassword().equalsIgnoreCase(oldPass)){
				String pass = newPass;
				while (pass.length() < 32){
					pass = '0' + pass;
				}
				usuario.setPassword(pass);
				usuarioDAO.modificar(usuario);
				Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service cambiarPasswordrUsuario ==> " + Boolean.TRUE.toString());
				return Boolean.TRUE.toString();
			} 
		}catch(Exception e){
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarUsuario ==> " + Boolean.FALSE.toString());
			return Boolean.FALSE.toString();
		}
		return Boolean.FALSE.toString();
	}
	
	/**
	 * @param idUsuario
	 * @param passAnt
	 * @param passNew
	 * @return
	 */
	/*
	@GET
    @Path("modificarPassUsuario/{idUser}/{passAnt}/{passNew}")
	public String modificarPassUsuario(@PathParam("idUser") Integer idUsuario,
			@PathParam("passAnt") String passAnt,
			@PathParam("passNew") String passNew
			){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service modificarPassUsuario");
		Usuario usuario = usuarioDAO.obtener(idUsuario);
		if(passAnt.equalsIgnoreCase(usuario.getPassword())){
			usuario.setPassword(passNew);
			return Boolean.TRUE.toString();
		}
		return Boolean.FALSE.toString();
	}
	*/
	
	@SuppressWarnings("resource")
	/* ********************************************************************
	 * 
	 * 					FICHERO
	 * 
	 * ********************************************************************
	 */
	@POST
	@Path("/FicheroService/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
		
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "Service uploadFile ");
 
		String fileName = "";
 
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
 
		for (InputPart inputPart : inputParts) {
 
		 try {
 
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = MapUtil.getFileName(header);
 
			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
 
			byte [] bytes = IOUtils.toByteArray(inputStream);
 
			String[] parametros = fileName.split("_");
			
			if(parametros[0].equalsIgnoreCase("VIDEO")){
				int idUsuario = Integer.parseInt(parametros[1]);
				//int idDisciplina = Integer.parseInt(parametros[2]);
				//int idGrado = Integer.parseInt(parametros[3]);
				int idRecurso = Integer.parseInt(parametros[4]);
				long hora = Long.parseLong(parametros[5].substring(0, parametros[5].indexOf(".")));
				
				Fichero fichero = new Fichero();
				fichero.setActivo(true);
				fichero.setNombreFichero(fileName);
				fichero.setExtension(fileName.substring(fileName.lastIndexOf(".")+1));
				//fichero.setFichero(bytes);
				
				fichero.setFecha(new java.util.Date(hora));
				fichero.setFechaModificacion(fichero.getFecha());
				fichero.setTamano(bytes.length);
				
				Usuario usuario = usuarioDAO.obtener(idUsuario);
				fichero.setUsuario(usuario);
				Recurso recurso = recursoDAO.obtener(idRecurso);
				fichero.setRecurso(recurso);
				fichero.setDescripcion(recurso.getNombre() + " - " + usuario.getLogin());
				
				fichero = ficheroDAO.anadir(fichero, bytes.length);
				
				String requestedFile = Constants.VIDEO_PATH + "/"+ fichero.getId() + ".mp4";
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);
			}
			
			if(parametros[0].equalsIgnoreCase("FOTO-CLUB")){
				int idClub = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/club_"+ idClub + parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);	
			}
			if(parametros[0].equalsIgnoreCase("FOTO-DISCIPLINA")){
				int idDisciplina = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/disciplina_"+ idDisciplina + parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);
			}
			if(parametros[0].equalsIgnoreCase("FOTO-USUARIO")){
				int idUser = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/usuario_"+ idUser + parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);
			}
			if(parametros[0].equalsIgnoreCase("FOTO-CURSO")){
				int idCurso = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/cartel_"+ idCurso + parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);	
			}
			if(parametros[0].equalsIgnoreCase("PDF-ARTICULO")){
				int idArticulo = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/articulo_"+ idArticulo + parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);	
			}
			
			Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "Service uploadFile " + "(" + fileName + ") OK");
		  } catch (IOException e) {
			e.printStackTrace();
		  }
 
		}
 
		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();
 
	}
 
	/**
	 * @param idUsuario
	 * @param idFichero
	 * @return
	 */
	@GET
    @Path("/FicheroService/downloadFile/{idUser}/{idFichero}")
	@Produces("video/mp4")
	public Response downloadFile(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service downloadFile " + idFichero);
		
		String requestedFile = Constants.VIDEO_PATH + "/"+idFichero+".mp4";
		
		File downloadFile = new File(requestedFile);
		Fichero fichero;
		if(!downloadFile.exists()){
			fichero = ficheroDAO.obtener(idFichero);
		}else{
			fichero = new Fichero();
			fichero.setId(idFichero);
		}
		
		ResponseBuilder response = Response.ok((Object) downloadFile);
		response.header("Content-Disposition", "attachment; filename=\""+idFichero+".bl\"");
		response.type("video/mp4");
		
		LogDownloadFile ldf = new LogDownloadFile();
		ldf.setFichero(fichero);
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		ldf.setUsuario(usuario);
		ldf.setFecha(new java.util.Date());
		ldf.setExtension(fichero.getExtension() + " Download");
		ldf.setDescargado(fichero.getTamano());
		logDownloadFileDAO.anadir(ldf);
		
		return response.build();
	}
	
	/**
	 * @param idUsuario
	 * @param idFichero
	 * @return
	 */
	@GET
    @Path("/FicheroService/downloadFoto/{idUser}/{idFichero}")
	@Produces("video/mp4")
	public Response downloadFoto(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service downloadFoto " + idFichero);
		
		File folder = new File(Constants.FILE_PATH);
		File downloadFile = null;
		String extension = "*";
		for(File f: folder.listFiles()){
			String canonicalPath = f.getName(); 
			if(canonicalPath.contains("_"+idFichero+".")){
				downloadFile = f;
				extension = canonicalPath.substring(canonicalPath.indexOf("."));
				break;
			}
		}
		
		ResponseBuilder response = Response.ok((Object) downloadFile);
		response.header("Content-Disposition", "attachment; filename=\""+idFichero+".bl\"");
		response.type("image/" + extension);
		
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		
		return response.build();
	}
	/**
	 * @param idUsuario
	 * @param idFichero
	 * @return
	 */
	@GET
    @Path("/FicheroService/downloadPdf/{idUser}/{idFichero}")
	@Produces("video/mp4")
	public Response downloadPdf(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service downloadPdf " + idFichero);
		
		String requestedFile = Constants.FILE_PATH + "/articulo_"+idFichero+".pdf";
		
		File downloadFile = new File(requestedFile);
		
		ResponseBuilder response = Response.ok((Object) downloadFile);
		response.header("Content-Disposition", "attachment; filename=\""+idFichero+".bl\"");
		response.type("application/pdf");
		
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		
		return response.build();
	}
	
    @POST
    @Path("/FicheroService/visualizaciones/{idUser}")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean cargarVisualizaciones(@PathParam("idUser") Integer idUsuario, InputStream incomingData){
    	Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service cargarVisualizaciones");
		JsonArray visualizaciones = transformToJsonArray(incomingData);
		for(int i = 0; i< visualizaciones.size(); i++){
			JsonObject jsonObject = visualizaciones.get(i).getAsJsonObject();
			LogDownloadFile ldf = new LogDownloadFile();
			Fichero fichero = new Fichero();
			fichero.setId(jsonObject.get("fichero_id").getAsInt());
			ldf.setFichero(fichero);
			Usuario usuario = new Usuario();
			usuario.setId(idUsuario);
			ldf.setUsuario(usuario);
			ldf.setFecha(new java.util.Date(jsonObject.get("fecha").getAsLong()));
			ldf.setExtension(jsonObject.get("extension").getAsString());
			ldf.setDescargado(jsonObject.get("descargado").getAsLong());
			logDownloadFileDAO.anadir(ldf);
		}
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "(" + idUsuario + ") Service cargarVisualizaciones ==> " + visualizaciones.toString());
		return true;
	}
    private JsonArray transformToJsonArray (InputStream incomingData){
		 StringBuilder jsonBuilder = new StringBuilder();
       try {
           BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
           String line = null;
           while ((line = in.readLine()) != null) {
           	jsonBuilder .append(line);
           }
       } catch (Exception e) {
    	   Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "Error al transformar entrada a JsonArray");
       }
       JsonParser jsonParser = new JsonParser();
       return jsonParser.parse(jsonBuilder.toString()).getAsJsonArray();
	 }
 
    
    /* *****************************************************************************************
     * 
     * 							UTILES
     * 
     * *****************************************************************************************
     */
    /**
	 * @param version
	 * @return
	 */
	@GET
	@Path("/UtilesService/comprobarVersion/{version}")
	public boolean comprobarVersion (@PathParam("version") int version){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "Service comprobarVersion " + "(" + version + ")");
		Android ultimaVersion = vistaUsuarioDAO.obtenerUltimaVersion();
		if(version < ultimaVersion.getNumVersion()){
			return true;
		}
		return false;
	}
	
	/**
	 * @return
	 */
	@GET
	@Path("/UtilesService/descargarUltimaVersion/")
	public Response descargarUltimaVersion(){
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "Service descargarUltimaVersion");
		Android ultimaVersion = vistaUsuarioDAO.obtenerUltimaVersion();
		Logger.getLogger(this.getClass().getCanonicalName()).log(LOG_LEVEL, "Service descargarUltimaVersion " + ultimaVersion.getId());
		if(ultimaVersion != null && ultimaVersion.getFichero() != null && ultimaVersion.getFichero().length>0){
			ResponseBuilder response = Response.ok((Object) ultimaVersion.getFichero());
			response.header("Content-Disposition", "attachment; filename=\"budolearning.apk\"");
			response.type("application/vnd.android.package-archive");
			return response.build();
		} else {
			ResponseBuilder response = Response.status(Status.BAD_REQUEST);
			return response.build();
		}
	}
	
}
