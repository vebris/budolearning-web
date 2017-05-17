package es.bris.budolearning.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-17T12:18:32.636+0200")
@StaticMetamodel(Recurso.class)
public class Recurso_ {
	public static volatile SingularAttribute<Recurso, Integer> id;
	public static volatile SingularAttribute<Recurso, String> nombre;
	public static volatile SingularAttribute<Recurso, TipoRecurso> tipo;
	public static volatile ListAttribute<Recurso, DisciplinaGrado> disciplinaGrados;
	public static volatile SingularAttribute<Recurso, Boolean> enPrograma;
	public static volatile ListAttribute<Recurso, Fichero> ficheros;
	public static volatile SingularAttribute<Club, Integer> numFicheros;
	public static volatile SingularAttribute<Club, Integer> numPdf;
	public static volatile SingularAttribute<Club, Integer> numVideos;
	
}
