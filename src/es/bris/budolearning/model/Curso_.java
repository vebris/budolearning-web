package es.bris.budolearning.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-10T16:01:16.590+0100")
@StaticMetamodel(Curso.class)
public class Curso_ {
	public static volatile SingularAttribute<Curso, Integer> id;
	public static volatile SingularAttribute<Curso, Disciplina> disciplina;
	public static volatile SingularAttribute<Curso, String> nombre;
	public static volatile SingularAttribute<Curso, String> direccion;
	public static volatile SingularAttribute<Curso, String> descripcion;
	public static volatile SingularAttribute<Curso, String> precios;
	public static volatile SingularAttribute<Curso, String> profesor;
	public static volatile SingularAttribute<Curso, Date> inicio;
	public static volatile SingularAttribute<Curso, Date> fin;
	//public static volatile SingularAttribute<Curso, byte[]> cartel;
}
