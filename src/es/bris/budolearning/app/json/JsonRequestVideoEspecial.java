package es.bris.budolearning.app.json;


import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.VideoEspecial;

public class JsonRequestVideoEspecial extends JsonRequest{

	private VideoEspecial data;
	private Club club;
	private Disciplina disciplina;
	private Grado grado;
	
	public VideoEspecial getData() {
		return data;
	}
	public void setData(VideoEspecial data) {
		this.data = data;
	}
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public Grado getGrado() {
		return grado;
	}
	public void setGrado(Grado grado) {
		this.grado = grado;
	}
	
	
}
