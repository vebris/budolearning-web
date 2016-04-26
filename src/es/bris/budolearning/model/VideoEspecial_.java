package es.bris.budolearning.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-09T11:51:01.184+0200")
@StaticMetamodel(Android.class)
public class VideoEspecial_ {
	public static volatile SingularAttribute<Android, String> version;
	public static volatile SingularAttribute<Android, Integer> id;
	public static volatile SingularAttribute<Android, Fichero> fichero;
	public static volatile SingularAttribute<Android, Club> club;
	public static volatile SingularAttribute<Android, Usuario> usuario;
	public static volatile SingularAttribute<Android, Date> inicio;
	public static volatile SingularAttribute<Android, Date> fin;
}
