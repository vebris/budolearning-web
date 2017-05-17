package es.bris.budolearning.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-09T11:51:01.184+0200")
@StaticMetamodel(Parrafo.class)
public class Parrafo_ {
	public static volatile SingularAttribute<Parrafo, Integer> id;
	public static volatile SingularAttribute<Parrafo, String> titulo;
	public static volatile SingularAttribute<Parrafo, String> texto;
	public static volatile SingularAttribute<Parrafo, Pagina> pagina;
	public static volatile SingularAttribute<Parrafo, Integer> orden;
	public static volatile SingularAttribute<Parrafo, Boolean> imagen;
}
