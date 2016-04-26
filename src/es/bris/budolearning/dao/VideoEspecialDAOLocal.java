package es.bris.budolearning.dao;

import javax.ejb.Local;

import es.bris.budolearning.model.VideoEspecial;

@Local
public interface VideoEspecialDAOLocal {

	public VideoEspecial anadir(VideoEspecial elemento) ;

	public VideoEspecial modificar(VideoEspecial elemento) ;

	public boolean borrar(int id) ;

	public VideoEspecial obtener(int id) ;

}
