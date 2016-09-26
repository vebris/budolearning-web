package es.bris.budolearning.app;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import es.bris.budolearning.app.json.JsonRequestPuntos;
import es.bris.budolearning.app.json.JsonRequestUsuario;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Android;
import es.bris.budolearning.model.EstadisticaVideosUsuario;
import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.LogDownloadFile;
import es.bris.budolearning.model.PuntosCompletos;

@Path("/")
public class ServiceUtiles extends ServiceAbstract{

	@POST
	@Path("/Utiles/estadisticas")
	public JsonResponse  estadisticas(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".estadisticas ==> " + request.getData().getId());
		
		List<EstadisticaVideosUsuario> data = vistaUsuarioDAO.getEstadisticas(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
		
	}
	
	@POST
    @Path("/Utiles/visualizaciones")
	public JsonResponse cargarVisualizaciones(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".visualizaciones ==> " + request.getVisualizaciones());
		
		for(LogDownloadFile ldf: request.getVisualizaciones()){
			logDownloadFileDAO.anadir(ldf);
			
			if(ldf.getCoste() != 0){
				Fichero fichero = ficheroDAO.obtener(ldf.getFichero().getId());
				if ("pdf".equalsIgnoreCase(fichero.getExtension())){
					puntosDAO.anadir(request.getUser().getId(),new Date(),Constants.TIPO_PUNTOS_PDF,Constants.CANTIDAD_PUNTOS_PDF, ldf.getFichero().getId());
				} else if ("mp4".equalsIgnoreCase(fichero.getExtension())){
					puntosDAO.anadir(request.getUser().getId(),new Date(),Constants.TIPO_PUNTOS_VIDEO,Constants.CANTIDAD_PUNTOS_VIDEO, ldf.getFichero().getId());
				}
			}
			
		}
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		return response;
	}
	/**
	 * @param version
	 * @return
	 */
	@POST
	@Path("/Utiles/checkVersion")
	public JsonResponse checkVersion (InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(----------)" + this.getClass().getSimpleName() + ".checkVersion ==> " + request.getVersion());
		Android ultimaVersion = vistaUsuarioDAO.obtenerUltimaVersion();
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(request.getVersion() < ultimaVersion.getNumVersion());
		if(request.getVersion() < ultimaVersion.getNumVersion()){
			response.setMsg("Existe una versión más actual que la instalada (" + ultimaVersion.getVersion() + ")");
		}
		return response;
	}
	@POST
    @Path("/Utiles/publicidad")
	public JsonResponse publicidad(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".visualizaciones ==> " + request.getVisualizaciones());
		
		puntosDAO.anadir(request.getUser().getId(),new Date(),Constants.TIPO_PUNTOS_PUBLI,Constants.CANTIDAD_PUNTOS_PUBLI, null);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		return response;
	}
	
	@POST
    @Path("/Utiles/bonus")
	public JsonResponse bonus(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".visualizaciones ==> " + request.getVisualizaciones());
		
		puntosDAO.anadir(request.getData().getId(),new Date(),Constants.TIPO_PUNTOS_BONUS,Constants.CANTIDAD_PUNTOS_BONUS, null);
		List<PuntosCompletos> data = puntosDAO.getPuntos(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getData().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}
	
	@POST
    @Path("/Utiles/borrarPuntos")
	public JsonResponse borrarPuntos(InputStream incomingData){
		JsonRequestPuntos request = (JsonRequestPuntos) transformInput(incomingData, JsonRequestPuntos.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".borrarPuntos ==> " + request.getData().getId());
		
		puntosDAO.borrar(request.getPuntos().getId());
		List<PuntosCompletos> data = puntosDAO.getPuntos(request.getData().getId());

		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getData().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}
	
	@POST
	@Path("/Utiles/listadoPuntos")
	public JsonResponse listadoPuntos(InputStream incomingData){
		JsonRequestUsuario request = (JsonRequestUsuario) transformInput(incomingData, JsonRequestUsuario.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".listadoPuntos ==> " + request.getData().getId());
		
		List<PuntosCompletos> data = puntosDAO.getPuntos(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getData().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
		
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
	@GET
	@Path("/UtilesService/descargarUltimaVersion/")
	@Produces("*/*")
	public Response downloadUltimaVersion() {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadUltimaVersion");
		
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
