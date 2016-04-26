package es.bris.budolearning.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-26T09:08:36.563+0200")
@StaticMetamodel(Disciplina.class)
public class Disciplina_ {
	public static volatile SingularAttribute<Disciplina, Integer> id;
	public static volatile SingularAttribute<Disciplina, String> descripcion;
	public static volatile SingularAttribute<Disciplina, String> nombre;
	public static volatile ListAttribute<Disciplina, DisciplinaGrado> disciplinaGrados;
}
