package es.bris.budolearning.app;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import es.bris.budolearning.app.json.JsonRequestMenu;
import es.bris.budolearning.app.json.JsonRequestPagina;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Menu;
import es.bris.budolearning.model.Pagina;
import es.bris.budolearning.model.Parrafo;

@Path("/")
public class ServiceGeneral extends ServiceAbstract{

	@POST
	@Path("/Menu/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestMenu request = (JsonRequestMenu) transformInput(incomingData, JsonRequestMenu.class);
		List<Menu> data = menuDAO.buscarTodos();
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Pagina/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestPagina request = (JsonRequestPagina) transformInput(incomingData, JsonRequestPagina.class);
		
		Pagina data = paginaDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}
	
	@POST
	@Path("/Pagina/list")
	public JsonResponse listParent(InputStream incomingData) {
		JsonRequestPagina request = (JsonRequestPagina) transformInput(incomingData, JsonRequestPagina.class);
		
		List<Pagina> data = paginaDAO.listParent(request.getData());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}
	
	@POST
	@Path("/Pagina/contenido")
	public JsonResponse contenido(InputStream incomingData) {
		JsonRequestPagina request = (JsonRequestPagina) transformInput(incomingData, JsonRequestPagina.class);
		
		List<Parrafo> data = parrafoDAO.buscarTodos(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Menu/insert")
	public JsonResponse insert(InputStream incomingData) {
		return null;
	}

	@POST
	@Override
	@Path("/Menu/update")
	public JsonResponse update(InputStream incomingData) {
		return null;
	}

	@POST
	@Override
	@Path("/Menu/delete")
	public JsonResponse delete(InputStream incomingData) {
		return null;
	}

	@POST
	@Override
	@Path("/Menu/insertFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response insertFile(MultipartFormDataInput input) {
		return null;
	}
	
	@GET
	@Path("/Pagina/contenido/downloadFile/{idFichero}")
	@Produces("*/*")
	public Response downloadFile(@PathParam("idFichero") Integer idFichero) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadFile ==> " + idFichero);
		return downloadFile(
				0, 
				idFichero, 
				"parr_",
				"jpg");
	}

}
