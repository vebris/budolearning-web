package es.bris.budolearning.app.json;


import java.util.List;

import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.LogDownloadFile;
import es.bris.budolearning.model.Usuario;

public class JsonRequestUsuario extends JsonRequest{

	private Usuario data;
	private Club club;
	private Disciplina disciplina;
	private Grado grado;
	private List<LogDownloadFile> visualizaciones;
	
	public Usuario getData() {
		return data;
	}
	public void setData(Usuario data) {
		this.data = data;
	}
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	public Grado getGrado() {
		return grado;
	}
	public void setGrado(Grado grado) {
		this.grado = grado;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public List<LogDownloadFile> getVisualizaciones() {
		return visualizaciones;
	}
	public void setVisualizaciones(List<LogDownloadFile> visualizaciones) {
		this.visualizaciones = visualizaciones;
	}
	
}
