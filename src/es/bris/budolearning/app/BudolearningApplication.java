package es.bris.budolearning.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class BudolearningApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public BudolearningApplication() {
		singletons.add(new VistaUsuarioService());
		
		singletons.add(new ServiceArticulo());
		singletons.add(new ServiceCurso());
		singletons.add(new ServiceClub());
		
		singletons.add(new ServiceFichero());
		singletons.add(new ServiceRecurso());
		singletons.add(new ServiceGrado());
		singletons.add(new ServiceDisciplina());
		
		singletons.add(new ServiceUsuario());
		singletons.add(new ServiceUtiles());
		
		singletons.add(new ServiceVideoEspecial());
	}
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	@Override
    public Set<Class<?>> getClasses() {
         return empty;
    }
}
