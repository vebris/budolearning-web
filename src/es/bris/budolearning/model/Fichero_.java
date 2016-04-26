package es.bris.budolearning.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-22T11:10:22.694+0100")
@StaticMetamodel(Fichero.class)
public class Fichero_ {
	public static volatile SingularAttribute<Fichero, Integer> id;
	public static volatile SingularAttribute<Fichero, String> descripcion;
	public static volatile SingularAttribute<Fichero, String> nombreFichero;
	public static volatile SingularAttribute<Fichero, String> extension;
	public static volatile SingularAttribute<Fichero, Date> fecha;
	public static volatile SingularAttribute<Fichero, Date> fechaModificacion;
	public static volatile SingularAttribute<Fichero, Recurso> recurso;
	//public static volatile SingularAttribute<Fichero, byte[]> fichero;
	public static volatile SingularAttribute<Fichero, Boolean> activo;
	public static volatile SingularAttribute<Fichero, Usuario> usuario;
	public static volatile SingularAttribute<Fichero, Long> tamano;
	public static volatile SingularAttribute<Fichero, Integer> coste;
}
