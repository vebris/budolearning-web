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

import es.bris.budolearning.app.json.JsonRequestArticulo;
import es.bris.budolearning.app.json.JsonResponse;
import es.bris.budolearning.model.Articulo;
import es.bris.budolearning.util.MapUtil;

@Path("/")
public class ServiceArticulo extends ServiceAbstract{

	@POST
	@Path("/Articulo/list")
	@Override
	public JsonResponse list(InputStream incomingData) {
		JsonRequestArticulo request = (JsonRequestArticulo) transformInput(incomingData, JsonRequestArticulo.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".list " );
		
		List<Articulo> data = articuloDAO.obtenerTodosCorto(request.getUser());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Articulo/select")
	public JsonResponse select(InputStream incomingData) {
		JsonRequestArticulo request = (JsonRequestArticulo) transformInput(incomingData, JsonRequestArticulo.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".select ==> " + request.getData().getId());
		
		Articulo data = articuloDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Articulo/insert")
	public JsonResponse insert(InputStream incomingData) {
		JsonRequestArticulo request = (JsonRequestArticulo) transformInput(incomingData, JsonRequestArticulo.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".insert ==> " + request.getData());
		
		Articulo data = articuloDAO.anadir(request.getData());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Inserción correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Articulo/update")
	public JsonResponse update(InputStream incomingData) {
		JsonRequestArticulo request = (JsonRequestArticulo) transformInput(incomingData, JsonRequestArticulo.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".update ==> " + request.getData().getId());
		
		Articulo data = articuloDAO.obtener(request.getData().getId());
		if(request.getData().getTitulo() != null) {
			data.setTitulo(request.getData().getTitulo());
		}
		if(request.getData().getAutor() != null) {
			data.setAutor(request.getData().getAutor());
		}
		if(request.getData().getFecha() != null) {
			data.setFecha(request.getData().getFecha());
		}
		if(request.getData().getActivo() != null) {
			data.setActivo(request.getData().getActivo());
		}
		data = articuloDAO.modificar(data);
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		response.setSuccess(true);
		response.setMsg("Modificación correcta");
		response.setData(data);
		return response;
	}

	@POST
	@Override
	@Path("/Articulo/delete")
	public JsonResponse delete(InputStream incomingData) {
		JsonRequestArticulo request = (JsonRequestArticulo) transformInput(incomingData, JsonRequestArticulo.class);
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, "(" + String.format("%10d", request.getUser().getId()) + ")" + this.getClass().getSimpleName() + ".delete ==> " + request.getData().getId());
		
		Articulo data = articuloDAO.obtener(request.getData().getId());
		
		JsonResponse response = new JsonResponse();
		if(request != null && request.getUser() != null) response.setPuntos(puntosDAO.saldo(request.getUser().getId()));
		if(data != null) {
			articuloDAO.borrar(request.getData().getId());
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
	@Path("/Articulo/insertFile")
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
				int idArticulo = Integer.parseInt(parametros[2].substring(0,parametros[2].indexOf(".")));
				String requestedFile = Constants.FILE_PATH + "/articulo_"
						+ idArticulo
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
	@Path("/Articulo/downloadFile/{idUser}/{idFichero}")
	@Produces("*/*")
	public Response downloadFile(@PathParam("idUser") Integer idUsuario, @PathParam("idFichero") Integer idFichero) {
		Logger.getLogger(this.getClass().getSimpleName()).log(LOG_LEVEL, this.getClass().getSimpleName() + ".downloadFile ==> " + idFichero);
		
		return downloadFile(
				idUsuario, 
				idFichero, 
				"articulo_",
				"pdf");
	}

}
