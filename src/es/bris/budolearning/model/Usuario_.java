package es.bris.budolearning.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-29T12:42:49.728+0200")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> login;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, String> rol;
	public static volatile SingularAttribute<Usuario, Boolean> activo;
	public static volatile SingularAttribute<Usuario, String> nombre;
	public static volatile SingularAttribute<Usuario, String> apellido1;
	public static volatile SingularAttribute<Usuario, String> apellido2;
	public static volatile SingularAttribute<Usuario, String> dni;
	public static volatile SingularAttribute<Usuario, String> direccion;
	public static volatile SingularAttribute<Usuario, String> localidad;
	public static volatile SingularAttribute<Usuario, String> mail;
	public static volatile SingularAttribute<Usuario, String> telefono;
	public static volatile ListAttribute<Usuario, DisciplinaGrado> disciplinaGrados;
	public static volatile SingularAttribute<Usuario, Club> profesor;
	public static volatile SingularAttribute<Usuario, Club> entrena;
	public static volatile SingularAttribute<Usuario, Integer> versionAndroid;
	public static volatile SingularAttribute<Usuario, Boolean> verPDF;
}
