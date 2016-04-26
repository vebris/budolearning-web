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

import es.bris.budolearning.app.json.JsonRequestDisciplina;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.Usuario;
import es.bris.budolearning.util.MapUtil;

@Path("/")
public class ServiceDisciplina extends ServiceAbstract{

	@POST
	@Path("/Disciplina/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestDisciplina request = (JsonRequestDisciplina) transformInput(incomingData, JsonRequestDisciplina.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".list ==> " + request.getUser().getId());
		
		Usuario usuario = usuarioDAO.obtener(request.getUser().getId());
		List<Disciplina> data = null;
		if(usuario.getRol().equalsIgnoreCase("ADMINISTRADOR")) {
			data = disciplinaDAO.listarTodasDisciplinas();
		} else {
			data = disciplinaDAO.disciplinasUsuario(request.getUser().getId());
		}
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Disciplina/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestDisciplina request = (JsonRequestDisciplina) transformInput(incomingData, JsonRequestDisciplina.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + request.getData().getId());
		
		Disciplina data = disciplinaDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Disciplina/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestDisciplina request = (JsonRequestDisciplina) transformInput(incomingData, JsonRequestDisciplina.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		
		Disciplina data = disciplinaDAO.anadir(request.getData());
		
		List<Grado> grados = gradoDAO.buscarTodosGrados();
		for(Grado g:grados){
			if(g.getId() < 20){
				DisciplinaGrado dg = new DisciplinaGrado();
				dg.setDisciplina(data);
				dg.setGrado(g);
				disciplinaGradoDAO.anadir(dg);
			}
		}
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Disciplina/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestDisciplina request = (JsonRequestDisciplina) transformInput(incomingData, JsonRequestDisciplina.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Disciplina data = disciplinaDAO.obtener(request.getData().getId());
		if(request.getData().getNombre() != null) {
			data.setNombre(request.getData().getNombre());
		}
		if(request.getData().getDescripcion() != null) {
			data.setDescripcion(request.getData().getDescripcion());
		}
		data = disciplinaDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Disciplina/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestDisciplina request = (JsonRequestDisciplina) transformInput(incomingData, JsonRequestDisciplina.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		Disciplina data = disciplinaDAO.obtener(request.getData().getId());
		
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
	@Path("/Disciplina/insertFile")
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
				int idDisciplina = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/disciplina_"
						+ idDisciplina
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
	@Path("/Disciplina/downloadFile/{idUser}/{idFichero}")
	@Produces("*/*")
    public Response downloadFile(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero) {
		return downloadFile(
				idUsuario, 
				idFichero, 
				"disciplina_",
				"jpg");
		
	}

}
