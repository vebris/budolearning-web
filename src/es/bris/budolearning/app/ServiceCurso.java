package es.bris.budolearning.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
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

import es.bris.budolearning.app.json.JsonRequestCurso;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Curso;
import es.bris.budolearning.util.MapUtil;

@Path("/")
public class ServiceCurso extends ServiceAbstract{

	@POST
	@Path("/Curso/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestCurso request = (JsonRequestCurso) transformInput(incomingData, JsonRequestCurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + (request != null && request.getUser() != null?String.format("%10d",request.getUser().getId()):"----------") + ")" + this.getClass().getSimpleName() + ".list ");
		
		List<Curso> data = cursoDAO.buscarCursos(
				Calendar.getInstance().get(Calendar.MONTH), 
				Calendar.getInstance().get(Calendar.YEAR));
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Curso/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestCurso request = (JsonRequestCurso) transformInput(incomingData, JsonRequestCurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + request.getData().getId());
		
		Curso data = cursoDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Curso/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestCurso request = (JsonRequestCurso) transformInput(incomingData, JsonRequestCurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		
		Curso data = cursoDAO.anadir(request.getData());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Curso/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestCurso request = (JsonRequestCurso) transformInput(incomingData, JsonRequestCurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Curso data = cursoDAO.obtener(request.getData().getId());
		if(request.getData().getNombre() != null) {
			data.setNombre(request.getData().getNombre());
		}
		if(request.getData().getDescripcion() != null) {
			data.setDescripcion(request.getData().getDescripcion());
		}
		if(request.getData().getDireccion() != null) {
			data.setDireccion(request.getData().getDireccion());
		}
		if(request.getData().getInicio() != null) {
			data.setInicio(request.getData().getInicio());
		}
		if(request.getData().getFin() != null) {
			data.setFin(request.getData().getFin());
		}
		if(request.getData().getPrecios() != null) {
			data.setPrecios(request.getData().getPrecios());
		}
		if(request.getData().getProfesor() != null) {
			data.setProfesor(request.getData().getProfesor());
		}
		if(request.getData().getDisciplina() != null) {
			data.setDisciplina(request.getData().getDisciplina());
		}
		data = cursoDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Curso/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestCurso request = (JsonRequestCurso) transformInput(incomingData, JsonRequestCurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		Curso data = cursoDAO.obtener(request.getData().getId());
		
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
	@Path("/Curso/insertFile")
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
				int idCurso = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/cartel_"
						+ idCurso
						+ parametros[2].substring(parametros[2].indexOf("."));
				FileOutputStream outputStream = new FileOutputStream(requestedFile);
				outputStream.write(bytes, 0, bytes.length);
				Logger.getLogger(this.getClass().getSimpleName()).log( LOG_LEVEL,
						"Service uploadFile " + "(" + fileName + ") OK");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}
	
	@GET
	@Override
	@Path("/Curso/downloadFile/{idUser}/{idFichero}")
	@Produces("*/*")
	public Response downloadFile(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadFile ==> " + idFichero);
		
		return downloadFile(
				idUsuario, 
				idFichero, 
				"cartel_",
				"jpg");
	}

}
