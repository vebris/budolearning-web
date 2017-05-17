package es.bris.budolearning.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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

import es.bris.budolearning.app.json.JsonRequestClub;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Club;
import es.bris.budolearning.util.MapUtil;

@Path("/")
public class ServiceClub extends ServiceAbstract{

	@POST
	@Path("/Club/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestClub request = (JsonRequestClub) transformInput(incomingData, JsonRequestClub.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + (request != null && request.getUser() != null?String.format("%10d", request.getUser().getId()):"----------") + ")" + this.getClass().getSimpleName() + ".list ");
		
		List<Club> data = clubDAO.buscarTodosClubs();
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Club/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestClub request = (JsonRequestClub) transformInput(incomingData, JsonRequestClub.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + request.getData().getId());
		
		Club data = clubDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Club/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestClub request = (JsonRequestClub) transformInput(incomingData, JsonRequestClub.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		
		Club data = clubDAO.anadir(request.getData());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Club/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestClub request = (JsonRequestClub) transformInput(incomingData, JsonRequestClub.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Club data = clubDAO.obtener(request.getData().getId());
		if(request.getData().getNombre() != null) {
			data.setNombre(request.getData().getNombre());
		}
		if(request.getData().getDescripcion() != null) {
			data.setDescripcion(request.getData().getDescripcion());
		}
		if(request.getData().getDireccion() != null) {
			data.setDireccion(request.getData().getDireccion());
		}
		if(request.getData().getLocalidad() != null) {
			data.setLocalidad(request.getData().getLocalidad());
		}
		if(request.getData().getEmail() != null) {
			data.setEmail(request.getData().getEmail());
		}
		if(request.getData().getTelefono() != null) {
			data.setTelefono(request.getData().getTelefono());
		}
		if(request.getData().getWeb() != null) {
			data.setWeb(request.getData().getWeb());
		}
		if(request.getData().getProfesor() != null) {
			data.setProfesor(request.getData().getProfesor());
		}
		data = clubDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Club/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestClub request = (JsonRequestClub) transformInput(incomingData, JsonRequestClub.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		Club data = clubDAO.obtener(request.getData().getId());
		
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

	@SuppressWarnings("resource")
	@POST
	@Override
	@Path("/Club/insertFile")
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
				int idClub = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/club_"
						+ idClub
						+ parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);
				Logger.getLogger(this.getClass().getSimpleName()).log( LOG_LEVEL,
						"Service uploadFile " + "(" + fileName + ") OK");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : "
						+ fileName).build();
	}

	@GET
	@Override
	@Path("/Club/downloadFile/{idUser}/{idFichero}")
	@Produces("*/*")
	public Response downloadFile(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadFile ==> " + idFichero);
		
		return downloadFile(
				idUsuario, 
				idFichero, 
				"club_",
				"jpg");
	}
}
