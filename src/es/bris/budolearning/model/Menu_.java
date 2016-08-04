package es.bris.budolearning.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-09T11:51:01.184+0200")
@StaticMetamodel(Menu.class)
public class Menu_ {
	public static volatile SingularAttribute<Menu, Integer> id;
	public static volatile SingularAttribute<Menu, String> titulo;
	public static volatile SingularAttribute<Menu, Menu> parent;
	public static volatile SingularAttribute<Menu, Pagina> page;
	public static volatile SingularAttribute<Menu, Boolean> logado;
	
}
