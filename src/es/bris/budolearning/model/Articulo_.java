package es.bris.budolearning.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-09T11:51:01.184+0200")
@StaticMetamodel(Android.class)
public class Articulo_ {
	public static volatile SingularAttribute<Android, Integer> id;
	public static volatile SingularAttribute<Android, Boolean> activo;
	public static volatile SingularAttribute<Android, Date> fecha;
	public static volatile SingularAttribute<Android, String> titulo;
	public static volatile SingularAttribute<Android, String> autor;
	public static volatile SingularAttribute<Android, String> texto;
	public static volatile SingularAttribute<Android, Boolean> visibleUsuarios;
}
