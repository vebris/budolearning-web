package es.bris.budolearning.dao;

import java.util.List;

import javax.ejb.Local;

import es.bris.budolearning.model.EstadisticaMensual;
import es.bris.budolearning.model.EstadisticaUsuario;
import es.bris.budolearning.model.LogDownloadFile;

@Local
public interface LogDownloadFileDAOLocal {

	public abstract List<LogDownloadFile> buscarTodosLogs();

	LogDownloadFile anadir(LogDownloadFile elemento);

	LogDownloadFile modificar(LogDownloadFile elemento);

	void borrar(int id);

	LogDownloadFile obtener(int id);

	public abstract List<EstadisticaMensual> porMeses();
	public abstract List<EstadisticaUsuario> porUsuarioTotal();
	public abstract List<EstadisticaUsuario> porUsuarioMes();

	public abstract List<EstadisticaUsuario> porVideosTotal();
	public abstract List<EstadisticaUsuario> porVideosMes();

}