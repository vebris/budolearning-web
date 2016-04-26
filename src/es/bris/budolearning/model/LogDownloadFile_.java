package es.bris.budolearning.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-01T16:43:57.138+0100")
@StaticMetamodel(LogDownloadFile.class)
public class LogDownloadFile_ {
	public static volatile SingularAttribute<LogDownloadFile, Integer> id;
	public static volatile SingularAttribute<LogDownloadFile, Fichero> fichero;
	public static volatile SingularAttribute<LogDownloadFile, Usuario> usuario;
	public static volatile SingularAttribute<LogDownloadFile, Date> fecha;
	public static volatile SingularAttribute<LogDownloadFile, Long> descargado;
	public static volatile SingularAttribute<LogDownloadFile, String> extension;
}
