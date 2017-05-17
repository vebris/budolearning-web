package es.bris.budolearning.app;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import es.bris.budolearning.app.json.JsonRequestFichero;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.Usuario;
import es.bris.budolearning.util.MapUtil;

@Path("/")
public class ServiceFichero extends ServiceAbstract{

	@POST
	@Path("/Fichero/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".list ==> " + request.getRecurso().getId());
		
		List<Fichero> data = ficheroDAO.buscarFicheros(request.getUser().getId(),request.getRecurso().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Fichero/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		int idFichero = request.getData()!=null?request.getData().getId():0;
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + idFichero);
		
		Fichero data = ficheroDAO.obtener(idFichero);

		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Fichero/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		
		request.getData().setUsuario(usuarioDAO.obtener(request.getUser().getId()));
		request.getData().setRecurso(recursoDAO.obtener(request.getRecurso().getId()));
		
		if(request.getData().getExtension() != null){
			if(request.getData().getExtension().equalsIgnoreCase("PDF") && Constants.COSTE_PDF < request.getData().getCoste()){
				request.getData().setCoste(Constants.COSTE_PDF);
			} else if(request.getData().getExtension().equalsIgnoreCase("MP4") && Constants.COSTE_VIDEO < request.getData().getCoste()){
				request.getData().setCoste(Constants.COSTE_VIDEO);
			}
		}
		
		Fichero data = ficheroDAO.anadir(request.getData());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Fichero/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Fichero data = ficheroDAO.obtener(request.getData().getId());
		
		data.setUsuario(usuarioDAO.obtener(request.getUser().getId()));
		if(request.getData().getDescripcion() != null) {
			data.setDescripcion(request.getData().getDescripcion());
		}
		data.setFecha(request.getData().getFecha());
		
		data.setFechaModificacion(new Date());
		data.setUsuario(request.getUser());
		data.setActivo(request.getData().getActivo());
		
		if(request.getData().getRecurso() != null) {
			data.setRecurso(request.getData().getRecurso());
		}
		data.setCoste(request.getData().getCoste());
		
		data = ficheroDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}
	
	@POST
	@Override
	@Path("/Fichero/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		Fichero data = ficheroDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		if(data != null) {
			ficheroDAO.borrar(request.getData().getId());
			response.setSuccess(true);
			response.setMsg("Borrado correcto");
		} else {
			response.setSuccess(false);
			response.setMsg("Error al borrar");
		}
		return response;
	}

	@SuppressWarnings("resource")
	@POST
	@Override
	@Path("/Fichero/insertFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response insertFile(MultipartFormDataInput input) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".insertFile ==> ");
		String fileName = "";
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		for (InputPart inputPart : inputParts) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = MapUtil.getFileName(header);
				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				byte[] bytes = IOUtils.toByteArray(inputStream);
				String[] parametros = fileName.split("_");
				int idFichero = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				
				if(idFichero>0){
				
					String requestedFile = Constants.VIDEO_PATH + "/"
							+ idFichero
							+ parametros[2].substring(parametros[2].indexOf("."));
					FileOutputStream outputStream = new FileOutputStream(requestedFile);
					outputStream.write(bytes, 0, bytes.length);
					Logger.getLogger(this.getClass().getSimpleName()).log( LOG_LEVEL,
							"Service uploadFile " + "(" + fileName + ") OK");
					
					Fichero fichero = ficheroDAO.obtener(idFichero);
					fichero.setFechaModificacion(new Date());
					fichero.setExtension(parametros[2].substring(parametros[2].indexOf(".")+1));
					fichero.setTamano(bytes.length);
					ficheroDAO.modificar(fichero);
					
					FileWriter ff =new FileWriter(Constants.VIDEO_PATH + "/nuevos.txt", true);
					ff.write(idFichero + "\n");
					ff.close();
				} else {
					Fichero fichero = new Fichero();
					fichero.setFecha(new Date());
					fichero.setFechaModificacion(new Date());
					fichero.setTamano(bytes.length);
					fichero.setActivo(false);
					fichero.setNombreFichero(parametros[1] + "_" + parametros[2]);
					fichero.setExtension(parametros[2].substring(parametros[2].indexOf(".")+1));
					fichero.setPropio(true);
					Usuario usuario = new Usuario();
					usuario.setId(Integer.parseInt(parametros[1]));
					fichero = ficheroDAO.anadir(fichero);
					
					
					String requestedFile = Constants.FILE_PATH + "/"
							+ fichero.getId()
							+ parametros[2].substring(parametros[2].indexOf("."));
					if(fichero.getExtension().equalsIgnoreCase("MP4")){
						requestedFile = Constants.VIDEO_PATH + "/"
								+ fichero.getId()
								+ parametros[2].substring(parametros[2].indexOf("."));
						
						FileWriter ff =new FileWriter(Constants.VIDEO_PATH + "/nuevos.txt", true);
						ff.write(fichero.getId() + "\n");
						ff.close();	
					}
					FileOutputStream outputStream = new FileOutputStream(requestedFile);
					outputStream.write(bytes, 0, bytes.length);
					Logger.getLogger(this.getClass().getSimpleName()).log( LOG_LEVEL,
							"Service insertFile " + "(" + fileName + ") OK");
					
					//mailEJB.enviarMailNuevoFichero(fichero, usuarioDAO.buscarAdministrador());
					
				}
			} catch (IOException e) {
				Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}
	
	@GET
	@Override
	@Path("/Fichero/downloadFile/{idUser}/{idFichero}")
	@Produces("*/*")
	public Response downloadFile(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadFile ==> " + idFichero);
		
		Fichero fichero = ficheroDAO.obtener(idFichero);
		
		int multiplicador = vistaUsuarioDAO.multiplicador(idUsuario, fichero.getRecurso().getDisciplinaGrados());
		if(multiplicador < 0 ) multiplicador = 0;
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "Multiplicador ==> " + multiplicador);
		
		if(multiplicador == 0 || ficheroDAO.ficheroSinCoste(idUsuario,idFichero)) {
			puntosDAO.anadir(idUsuario, new Date(), fichero.getExtension().equalsIgnoreCase("PDF")?Constants.TIPO_PUNTOS_PDF:Constants.TIPO_PUNTOS_VIDEO, 0, fichero.getId());
		} else if(fichero.getCoste()*multiplicador <= puntosDAO.saldo(idUsuario)) {
			puntosDAO.anadir(idUsuario, new Date(), fichero.getExtension().equalsIgnoreCase("PDF")?Constants.TIPO_PUNTOS_PDF:Constants.TIPO_PUNTOS_VIDEO, -fichero.getCoste() * multiplicador, fichero.getId());
		} else {
			return null;
		}
		
		return downloadFile(
				idUsuario, 
				idFichero, 
				"",
				fichero.getExtension());
	}

	@GET
	@Path("/Fichero/downloadFileEspecial/{idUser}/{idFichero}")
	@Produces("*/*")
	public Response downloadFileEspecial(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadFileEspecial ==> " + idFichero);
		
		Fichero fichero = ficheroDAO.obtener(idFichero);
		//puntosDAO.anadir(idUsuario, new Date(), fichero.getExtension().equalsIgnoreCase("pdf")?Constants.TIPO_PUNTOS_PDF:Constants.TIPO_PUNTOS_VIDEO, 0, fichero.getId());
		
		return downloadFile(
				idUsuario, 
				idFichero, 
				"",
				fichero.getExtension());
	}
	
	
	@POST
	@Path("/Fichero/listSinRecurso")
	public JsonResponse listSinRecurso(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".listSinRecurso " );
		
		List<Fichero> data = ficheroDAO.buscarFicheros(request.getUser().getId(),-1);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}
	@POST
	@Path("/Fichero/updateRecurso")
	public JsonResponse updateRecurso(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Fichero fichero = ficheroDAO.obtener(request.getData().getId());
		fichero.setActivo(true);
		
		if(request.getRecurso() != null) {
			fichero.setRecurso(recursoDAO.obtener(request.getRecurso().getId()));
			fichero = ficheroDAO.modificar(fichero);
			//mailEJB.enviarMailNuevoFicheroAceptado(fichero, fichero.getUsuario());
		} else {
			//mailEJB.enviarMailNuevoFicheroRechazado(fichero, fichero.getUsuario());
		}
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(fichero);
		return response;
	}
	
}

