package es.bris.budolearning.app;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.security.auth.spi.Util;

import es.bris.budolearning.app.json.JsonRequestUsuario;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Android;
import es.bris.budolearning.model.AndroidUsuario;
import es.bris.budolearning.model.Usuario;
import es.bris.budolearning.util.StringUtil;

@Path("/")
public class ServiceUsuario extends ServiceAbstract{

	@POST
	@Path("/Usuario/login")
	public JsonResponse login(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(----------)" + this.getClass().getSimpleName() + ".login ==> " + request.getData().getLogin() + " " + request.getData().getPassword() + " ("+request.getVersion()+")");
		
		Android ultimaVersion = vistaUsuarioDAO.obtenerUltimaVersion();
		JsonResponse response = new JsonResponse();
		
		if(ultimaVersion.getNumVersion() <= request.getVersion() || request.getVersion() == 0){
			AndroidUsuario data = vistaUsuarioDAO.loginAndroid(request.getData().getLogin(), request.getData().getPassword(), request.getVersion());
			puntosDAO.anadir(data.getId(), new Date(), Constants.TIPO_PUNTOS_LOGIN, Constants.CANTIDAD_PUNTOS_LOGIN, null);
			
			response.setPuntos(puntosDAO.saldo(data.getId()));
			response.setSuccess(data.getId() > 0);
			response.setMsg(data.getId() > 0?"":"Usuario y/o password erroneos");
			response.setData(data);
		} else {
			response.setSuccess(false);
			response.setMsg("Actualice la version");
		}
		
		return response;
	}
	@POST
	@Path("/Usuario/aeok/login")
	public JsonResponse loginAEOK(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(----------)" + this.getClass().getSimpleName() + ".login ==> " + request.getData().getLogin() + " " + request.getData().getPassword() + " ("+request.getVersion()+")");
		
		Android ultimaVersion = vistaUsuarioDAO.obtenerUltimaVersion("aeok");
		JsonResponse response = new JsonResponse();
		
		if(ultimaVersion.getNumVersion() <= request.getVersion() || request.getVersion() == 0){
			AndroidUsuario data = vistaUsuarioDAO.loginAndroid(request.getData().getLogin(), request.getData().getPassword(), request.getVersion());
			puntosDAO.anadir(data.getId(), new Date(), Constants.TIPO_PUNTOS_LOGIN, Constants.CANTIDAD_PUNTOS_LOGIN, null);
			
			response.setPuntos(puntosDAO.saldo(data.getId()));
			response.setSuccess(data.getId() > 0);
			response.setMsg(data.getId() > 0?"":"Usuario y/o password erroneos");
			response.setData(data);
		} else {
			response.setSuccess(false);
			response.setMsg("Actualice la version");
		}
		
		return response;
	}
	
	@POST
	@Path("/Usuario/cambioContrasena")
	public JsonResponse cambioContrasena(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(----------)" + this.getClass().getSimpleName() + ".cambioContraseï¿½a ==> " + request.getData().getMail());
		
		Usuario usuario = usuarioDAO.buscarUsuariosByEmail(request.getData().getMail());
		String password = StringUtil.randomString(10);
		String hash = Util.createPasswordHash("MD5", Util.BASE16_ENCODING, null, null, password);
		usuario.setPassword(new String(hash));
		
		usuarioDAO.modificar(usuario);
		
		mailEJB.enviarMailUsuarioContrasena (request.getData().getMail(), usuario, password);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Generada contraseña, compruebe su email para ver la nueva contraseña");
		response.setData(null);
		return response;
	}
	
	@POST
	@Path("/Usuario/activarUsuario")
	public JsonResponse  activarUsuario(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".activar ==> " + request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setData(null);
		
		try{
			Usuario usuario = usuarioDAO.obtener(request.getData().getId());
			usuario.setActivo(!usuario.getActivo());
			usuarioDAO.modificar(usuario);
			
			mailEJB.enviarMailUsuarioCreacion(usuario.getMail(), usuario);
			
			response.setSuccess(true);
			response.setMsg("Modificacion correcta.");
		}catch(Exception e){
			response.setSuccess(false);
			response.setMsg("Error al activar al usuario.");
		}
		return response;
	}
	
	@POST
	@Path("/Usuario/subirGrado")
	public JsonResponse subirGrado(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".subirGrado ==> " + request.getData().getId() + "/" + request.getDisciplina().getId() + "/"+request.getGrado().getId());
		
		AndroidUsuario au = vistaUsuarioDAO.getAlumnoSubirGrado(request.getUser().getId(), 
				request.getData().getId(), 
				request.getDisciplina().getId(), 
				request.getGrado().getId());
		
		if(au != null)
			puntosDAO.anadir(request.getData().getId(), new Date(), Constants.TIPO_PUNTOS_GRADO, Constants.CANTIDAD_PUNTOS_GRADO, null);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		if(au != null && au.getId() > 0){
			response.setSuccess(true);
			response.setMsg("Modificaciï¿½n correcta.");
		} else {
			response.setSuccess(false);
			response.setMsg("Error al subir de grado.");
		}
		response.setData(null);
		return response;
	}
	
	@POST
	@Path("/Usuario/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".list ==> " + request.getClub().getId());
		
		List<AndroidUsuario> data = vistaUsuarioDAO.getAlumnos(request.getUser().getId(), request.getClub().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Usuario/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + (request.getUser()!=null?String.format("%10d", request.getUser().getId()):"----------") + ")" + this.getClass().getSimpleName() + ".select ==> " + (request.getData().getLogin()!=null?request.getData().getLogin():request.getData().getId()));
		
		AndroidUsuario data = vistaUsuarioDAO.getUsuario(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Usuario/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		try{
			Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + (request.getUser()!=null?String.format("%10d", request.getUser().getId()):"----------") + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData().getLogin());
		}catch(Exception e){
			Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(----------)" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		}
		
		try{
			Usuario data = usuarioDAO.anadir(request.getData());
			
			Usuario responsable = usuarioDAO.buscarProfesor(data.getEntrena());
			if(responsable == null || data.getEntrena() == null || data.getEntrena().getId() == 0){
				responsable = usuarioDAO.buscarAdministrador();
			}
			
			mailEJB.enviarMailUsuarioRegistro (data.getMail(), data, responsable);
			puntosDAO.anadir(request.getData().getId(), new Date(), Constants.TIPO_PUNTOS_INICIO, Constants.CANTIDAD_PUNTOS_INICIO, null);
			
			JsonResponse response = new JsonResponse();
			if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
			response.setSuccess(true);
			response.setMsg("Usuario creado correctamente.");
			response.setData(data);
			return response;
		} catch (Exception e) {
			JsonResponse response = new JsonResponse();
			if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
			response.setSuccess(false);
			response.setMsg("Error al crear el usuario.");
			response.setData(null);
			return response;
		}
	}

	@POST
	@Override
	@Path("/Usuario/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Usuario data = usuarioDAO.obtener(request.getData().getId());
		
		if(request.getData().getLogin() != null) {
			data.setLogin(request.getData().getLogin());
		}
		if(request.getData().getPassword() != null) {
			data.setPassword(request.getData().getPassword());
		}
		if(request.getData().getActivo() != null) {
			data.setActivo(request.getData().getActivo());
		}
		
		if(request.getData().getNombre() != null) {
			data.setNombre(request.getData().getNombre());
		}
		if(request.getData().getApellido1() != null) {
			data.setApellido1(request.getData().getApellido1());
		}
		if(request.getData().getApellido2() != null) {
			data.setApellido2(request.getData().getApellido2());
		}
		if(request.getData().getDni() != null) {
			data.setDni(request.getData().getDni());
		}
		
		if(request.getData().getRol() != null) {
			data.setRol(request.getData().getRol());
		}
		
		if(request.getData().getDireccion() != null) {
			data.setDireccion(request.getData().getDireccion());
		}
		if(request.getData().getLocalidad() != null) {
			data.setLocalidad(request.getData().getLocalidad());
		}
		if(request.getData().getMail() != null) {
			data.setMail(request.getData().getMail());
		}
		if(request.getData().getTelefono() != null) {
			data.setTelefono(request.getData().getTelefono());
		}
		
		if(request.getData().getVersionAndroid() > 0) {
			data.setVersionAndroid(request.getData().getVersionAndroid());
		}
		
		if(request.getData().getEntrena() != null && request.getData().getEntrena().getId() > 0) {
			data.setEntrena(clubDAO.obtener(request.getData().getEntrena().getId()));
		}
		if(request.getData().getProfesor() != null) {
			data.setProfesor(clubDAO.obtener(request.getData().getProfesor().getId()));
		} else {
			data.setProfesor(null);
		}
		
		if(request.getData().getVerPDF() != null) {
			data.setVerPDF(request.getData().getVerPDF());
		}
		
		
		data = usuarioDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificaciï¿½n correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Usuario/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".delete ==> " + incomingData);
		
		Usuario data = usuarioDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		if(data != null) {
			disciplinaDAO.borrar(request.getData().getId());
			response.setSuccess(true);
			response.setMsg("Borrado correcto");
		} else {
			response.setSuccess(false);
			response.setMsg("Error al borrar");
		}
		return response;
	}

	@POST
	@Override
	@Path("/Usuario/insertFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response insertFile(MultipartFormDataInput input) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".insertFile ==> ");
		return Response.status(200).entity("uploadFile is called, NOT SUPPORTED: ").build();
	}

}
