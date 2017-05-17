package es.bris.budolearning.app;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import es.bris.budolearning.app.json.JsonRequestFichero;
import es.bris.budolearning.app.json.JsonRequestVideoEspecial;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.VideoEspecial;

@Path("/")
public class ServiceVideoEspecial extends ServiceAbstract{

	@POST
	@Path("/VideoEspecial/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestVideoEspecial request = (JsonRequestVideoEspecial) transformInput(incomingData, JsonRequestVideoEspecial.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".list ==> " + request.getUser().getId());
		
		List<VideoEspecial> data = videoEspecialDAO.buscarVideos(request.getClub(), request.getDisciplina(), request.getGrado(), request.getUser());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/VideoEspecial/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestVideoEspecial request = (JsonRequestVideoEspecial) transformInput(incomingData, JsonRequestVideoEspecial.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + request.getData().getId());
		
		VideoEspecial data = videoEspecialDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/VideoEspecial/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestVideoEspecial request = (JsonRequestVideoEspecial) transformInput(incomingData, JsonRequestVideoEspecial.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		
		VideoEspecial data = videoEspecialDAO.anadir(request.getData());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/VideoEspecial/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestVideoEspecial request = (JsonRequestVideoEspecial) transformInput(incomingData, JsonRequestVideoEspecial.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		VideoEspecial data = videoEspecialDAO.obtener(request.getData().getId());
		data.setClub(clubDAO.obtener(request.getData().getClub().getId()));
		data.setFichero(ficheroDAO.obtener(request.getData().getFichero().getId()));
		data.setInicio(request.getData().getInicio());
		data.setFin(request.getData().getFin());
		if(request.getData().getUsuario() != null) {
			data.setUsuario(usuarioDAO.obtener(request.getData().getUsuario().getId()));
		} else { 
			data.setUsuario(null);
		}
		
		videoEspecialDAO.modificar(data);
		
		
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/VideoEspecial/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestFichero request = (JsonRequestFichero) transformInput(incomingData, JsonRequestFichero.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		VideoEspecial data = videoEspecialDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		if(data != null) {
			videoEspecialDAO.borrar(request.getData().getId());
			response.setSuccess(true);
			response.setMsg("Borrado correcto");
		} else {
			response.setSuccess(false);
			response.setMsg("Error al borrar");
		}
		return response;
	}
	
	
}
