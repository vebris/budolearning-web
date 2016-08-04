package es.bris.budolearning.app;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import es.bris.budolearning.app.json.JsonRequest;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.dao.ArticuloDAO;
import es.bris.budolearning.dao.ClubDAO;
import es.bris.budolearning.dao.CursoDAO;
import es.bris.budolearning.dao.DisciplinaDAO;
import es.bris.budolearning.dao.DisciplinaGradoDAO;
import es.bris.budolearning.dao.FicheroDAO;
import es.bris.budolearning.dao.GradoDAO;
import es.bris.budolearning.dao.LogDownloadFileDAO;
import es.bris.budolearning.dao.MenuDAO;
import es.bris.budolearning.dao.PaginaDAO;
import es.bris.budolearning.dao.ParrafoDAO;
import es.bris.budolearning.dao.PuntosDAO;
import es.bris.budolearning.dao.RecursoDAO;
import es.bris.budolearning.dao.TipoRecursoDAO;
import es.bris.budolearning.dao.UsuarioDAO;
import es.bris.budolearning.dao.VideoEspecialDAO;
import es.bris.budolearning.dao.VistaUsuarioDAO;
import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.LogDownloadFile;
import es.bris.budolearning.model.Usuario;
import es.bris.budolearning.util.MailEJB;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceAbstract {
	
	protected Level LOG_LEVEL = Level.WARNING;
	@EJB
	protected static DisciplinaDAO disciplinaDAO;
	@EJB
	protected static DisciplinaGradoDAO disciplinaGradoDAO;
	@EJB
	protected static GradoDAO gradoDAO;
	@EJB
	protected static RecursoDAO recursoDAO;
	@EJB
	protected static FicheroDAO ficheroDAO;
	@EJB
	protected static ArticuloDAO articuloDAO;
	@EJB
	protected static ClubDAO clubDAO;
	@EJB
	protected static CursoDAO cursoDAO;
	@EJB
	protected static UsuarioDAO usuarioDAO;
	@EJB
	protected static LogDownloadFileDAO logDownloadFileDAO;
	@EJB
	protected static VistaUsuarioDAO vistaUsuarioDAO;
	@EJB
	protected static MailEJB mailEJB;
	@EJB
	protected static VideoEspecialDAO videoEspecialDAO;
	@EJB
	protected static PuntosDAO puntosDAO;
	@EJB
	protected static TipoRecursoDAO tipoRecursoDAO;
	@EJB
	protected static MenuDAO menuDAO;
	@EJB
	protected static PaginaDAO paginaDAO;
	@EJB
	protected static ParrafoDAO parrafoDAO;
	
	public JsonResponse list(InputStream requestBodyStream) {return null;}
	public JsonResponse select(InputStream requestBodyStream){return null;}
	public JsonResponse insert(InputStream requestBodyStream){return null;}
	public JsonResponse update(InputStream requestBodyStream){return null;}
	public JsonResponse delete(InputStream requestBodyStream){return null;}
	public Response insertFile(MultipartFormDataInput input){return null;}
	public Response downloadFile(Integer idUsuario, Integer idFichero){return null;}
	
	protected Response downloadFile(int idUser, int idFile, String tipo, String extension){
		String requestedFile;
		if("mp4".equalsIgnoreCase(extension)){
			requestedFile = Constants.VIDEO_PATH + "/"+tipo+idFile+"."+extension;
		} else {
			requestedFile = Constants.FILE_PATH + "/"+tipo+idFile+"."+extension;
		} 
		//
		File downloadFile = new File(requestedFile);
		
		if(downloadFile.exists()){
			ResponseBuilder response = Response.ok((Object) downloadFile);
			response.header("Content-Disposition", "attachment; filename=\""+idFile+".bl\"");
			if("mp4".equalsIgnoreCase(extension)){
				response.type("video/mp4");
				
				Fichero fichero;
				if(!downloadFile.exists()){
					fichero = ficheroDAO.obtener(idFile);
				}else{
					fichero = new Fichero();
					fichero.setId(idFile);
				}
				
				LogDownloadFile ldf = new LogDownloadFile();
				ldf.setFichero(fichero);
				Usuario usuario = new Usuario();
				usuario.setId(idUser);
				ldf.setUsuario(usuario);
				ldf.setFecha(new java.util.Date());
				ldf.setExtension(fichero.getExtension() + " Download");
				ldf.setDescargado(downloadFile.length());
				logDownloadFileDAO.anadir(ldf);
				
			} else if("pdf".equalsIgnoreCase(extension)){
				response.type("application/pdf");
			} else if("jpg".equalsIgnoreCase(extension)){
				response.type("image/" + extension);
			}
			
			return response.build();
		} else {
			if("jpg".equalsIgnoreCase(extension)){
				downloadFile = new File(Constants.FILE_PATH + "/0."+extension);
				ResponseBuilder response = Response.ok((Object) downloadFile);
				response.header("Content-Disposition", "attachment; filename=\""+idFile+".bl\"");
				response.type("image/" + extension);
				return response.build();
			}
			return null;
		}
		
	}

	public JsonRequest transformInput(InputStream requestBodyStream, Class<?> cls){
		try {
			JsonSerializer<Date> ser = new JsonSerializer<Date>() {
			  @Override
			  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			    return src == null ? null : new JsonPrimitive(src.getTime());
			  }
			};

			JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
			  @Override
			  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			    return json == null ? null : new Date(json.getAsLong());
			  }
			};

			Gson gson = new GsonBuilder()
			   .registerTypeAdapter(Date.class, ser)
			   .registerTypeAdapter(Date.class, deser).create();
			return (JsonRequest) gson.fromJson(new InputStreamReader(requestBodyStream, "UTF-8"), cls);
		} catch (JsonSyntaxException | JsonIOException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
