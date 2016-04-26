package es.bris.budolearning.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import es.bris.budolearning.app.json.JsonRequestRecurso;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.Recurso;
import es.bris.budolearning.model.TipoRecurso;

@Path("/")
public class ServiceRecurso extends ServiceAbstract{

	@POST
	@Path("/Recurso/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestRecurso request = (JsonRequestRecurso) transformInput(incomingData, JsonRequestRecurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".list ==> " + request.getDisciplina().getId() + " " + request.getGrado().getId());
		
		List<Recurso> data = recursoDAO.buscarRecursosDisciplinaGrado(request.getDisciplina().getId(), request.getGrado().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Recurso/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestRecurso request = (JsonRequestRecurso) transformInput(incomingData, JsonRequestRecurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + request.getData().getId());
		
		Recurso data = recursoDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Recurso/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestRecurso request = (JsonRequestRecurso) transformInput(incomingData, JsonRequestRecurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData().getNombre());
		
		Recurso recurso = request.getData();
		TipoRecurso tr = tipoRecursoDAO.buscarTipoRecurso(request.getData().getTipo().getNombre());
		recurso.setTipo(tr);
		DisciplinaGrado dg = disciplinaGradoDAO.obtenerDisciplinaGrado(request.getDisciplina().getId(), request.getGrado().getId());
		if(recurso.getDisciplinaGrados() == null) recurso.setDisciplinaGrados(new ArrayList<DisciplinaGrado>());
		recurso.getDisciplinaGrados().add(dg);
		
		Recurso data = recursoDAO.anadir(recurso);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Recurso/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestRecurso request = (JsonRequestRecurso) transformInput(incomingData, JsonRequestRecurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL,"(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Recurso data = recursoDAO.obtener(request.getData().getId());
		data.setNombre(request.getData().getNombre());
		data.setEnPrograma(request.getData().isEnPrograma());
		if(request.getData().getTipo() != null) {
			TipoRecurso tr = tipoRecursoDAO.buscarTipoRecurso(request.getData().getTipo().getNombre());
			data.setTipo(tr);
		}
		data = recursoDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Recurso/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestRecurso request = (JsonRequestRecurso) transformInput(incomingData, JsonRequestRecurso.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		Recurso data = recursoDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(data != null) {
			recursoDAO.borrar(request.getData().getId());
			response.setSuccess(true);
			response.setMsg("Borrado correcto");
		} else {
			response.setSuccess(false);
			response.setMsg("Error al borrar");
		}
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		return response;
	}

	@POST
	@Override
	@Path("/Recurso/insertFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response insertFile(MultipartFormDataInput input) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".insertFile ==> ");
		return Response.status(200).entity("uploadFile is called, NOT SUPPORTED: ").build();
	}

}
