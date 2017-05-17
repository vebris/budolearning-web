package es.bris.budolearning.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.ws.rs.PathParam;

import es.bris.budolearning.model.Android;
import es.bris.budolearning.model.AndroidUsuario;
import es.bris.budolearning.model.AndroidUsuario.AndroidDisciplina;
import es.bris.budolearning.model.AndroidUsuario.AndroidFichero;
import es.bris.budolearning.model.AndroidUsuario.AndroidGrado;
import es.bris.budolearning.model.AndroidUsuario.AndroidRecurso;
import es.bris.budolearning.model.Club;
import es.bris.budolearning.model.Disciplina;
import es.bris.budolearning.model.DisciplinaGrado;
import es.bris.budolearning.model.EstadisticaVideosUsuario;
import es.bris.budolearning.model.Fichero;
import es.bris.budolearning.model.Grado;
import es.bris.budolearning.model.Puntos;
import es.bris.budolearning.model.Recurso;
import es.bris.budolearning.model.Usuario;

/**
 * Session Bean implementation class VistaUsuarioDAO
 */
@Stateless
@LocalBean
public class VistaUsuarioDAO extends GenericDAO implements VistaUsuarioDAOLocal {

	private static final long serialVersionUID = 1L;

	@Override
	public List<DisciplinaGrado> buscarDisciplinasGradoUsuario(@PathParam("idUsuario") int idUsuario) {
    	Usuario usuario = getEntityManager().find(Usuario.class, idUsuario);
    	if(usuario != null){
    		List<DisciplinaGrado> grados = usuario.getDisciplinaGrados();
    		return grados;
    	} else {
    		return new ArrayList<DisciplinaGrado>();
    	}
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<Recurso> buscarRecursosUsuarioDisciplinaGrado(@PathParam("idUsuario") int idUsuario, @PathParam("idDisciplina") int idDisciplina, @PathParam("idGrado") int idGrado) {
		Query query =  getEntityManager().createQuery("SELECT distinct  r FROM Recurso r INNER JOIN r.disciplinaGrados dg WHERE dg.id in ("
				+ "select distinct dg2.id FROM Usuario u INNER JOIN u.disciplinaGrados dg2 WHERE dg2.disciplina.id = :idDisciplina and dg2.grado.id = :idGrado and u.id=:idUsuario"
				+ ")");
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
		
		List<Recurso> recursos = query.getResultList();
		
		return recursos;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Recurso> buscarRecursosUsuarioDisciplina(@PathParam("idUsuario")int idUsuario, @PathParam("idDisciplina") int idDisciplina) {
		Query query =  getEntityManager().createQuery("SELECT r FROM Recurso r INNER JOIN r.disciplinaGrados dg WHERE dg.id in ("
				+ "select distinct dg2.id FROM Usuario u INNER JOIN u.disciplinaGrados dg2 WHERE dg2.disciplina.id = :idDisciplina and u.id=:idUsuario"
				+ ") order by dg.disciplina.id asc, r.enPrograma desc, dg.grado.id desc, r.id asc");
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idDisciplina", idDisciplina);
		List<Recurso> recursos = query.getResultList();
		return recursos;
	}

	@Override
	public List<DisciplinaGrado> buscarDisciplinasGradoRecurso(@PathParam("idDisciplina")int idDisciplina, @PathParam("idRecurso") int idRecurso) {
		Recurso recurso = getEntityManager().find(Recurso.class, idRecurso);
    	if(recurso != null){
    		List<DisciplinaGrado> grados = recurso.getDisciplinaGrados();
    		List<DisciplinaGrado> gr1 = new ArrayList<DisciplinaGrado>();
    		for(DisciplinaGrado gr: grados){
    			if (idDisciplina == 0 || gr.getDisciplina().getId() == idDisciplina){
    				gr1.add(gr);
    			}
    		}
    		return gr1;
    	} else {
    		return new ArrayList<DisciplinaGrado>();
    	}
	}

    @Override
	public int buscarGradoMaximoDisciplina(@PathParam("idUsuario") int idUsuario, @PathParam("idDisciplina") int idDisciplina) {
		Query query =  getEntityManager().createQuery(
				"select max (dg2.id) FROM Usuario u INNER JOIN u.disciplinaGrados dg2 WHERE dg2.disciplina.id = :idDisciplina and u.id=:idUsuario"
				);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idDisciplina", idDisciplina);
		return (Integer) query.getSingleResult();
	}

    
    /*
     * Metodos para los servicios REST
     * 
     */
	@SuppressWarnings("unchecked")
	public Usuario login(String login, String pass) {
		Query query = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.login = :login and u.password=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);
		List<Usuario> usuarios = query.getResultList();
		if(usuarios.size() > 0) {
			return usuarios.get(0);
		} else {  
			return new Usuario();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Disciplina> disciplinasUsuario(Integer idUser) {
		String sql = 
				"select distinct(d) "
				+ " from Usuario u INNER JOIN u.disciplinaGrados dg INNER JOIN dg.disciplina d"
				+ " where "
				+ "		u.id = :idUsuario "
				+ " order by d.id asc";
		Query query =  getEntityManager().createQuery(sql);
		query.setParameter("idUsuario", idUser);
		return (List<Disciplina>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Grado> gradosDisciplinaUsuario(Integer idUser, Integer idDisciplina) {
		String sql = 
				"select distinct(g) "
				+ " from Usuario u INNER JOIN u.disciplinaGrados udg ,"
				+ "		DisciplinaGrado dg INNER JOIN dg.grado g"
				+ " 	"
				+ " where "
				+ "		dg.grado.id <= udg.grado.id " // + (CASE WHEN u.rol = 'ADMINISTRADOR' THEN 8 ELSE CASE WHEN u.rol = 'PROFESOR' THEN 2 ELSE 1 END END)" 
				+ " 	and udg.disciplina.id = dg.disciplina.id"
				+ " 	and udg.disciplina.id = :idDisciplina"
				+ "		and u.id = :idUsuario"
				+ "     and dg.grado.id>1" // Quitar cituron blanco
				+ " order by g.id desc";
		Query query =  getEntityManager().createQuery(sql);
		query.setParameter("idUsuario", idUser);
		query.setParameter("idDisciplina", idDisciplina);
		return (List<Grado>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Recurso> todosRecursos (Integer idDisciplina, Integer idGrado) {
		String sql = "select distinct(r) "
				+ " from Recurso r INNER JOIN r.disciplinaGrados rdg "
				+ " where "
				+ "		rdg.grado.id = :idGrado "
				+ "		and rdg.disciplina.id= :idDisciplina"
				;
		Query query =  getEntityManager().createQuery(sql);
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
		return (List<Recurso>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Recurso> recursosGradoDisciplinaUsuario(Integer idUser, Integer idDisciplina, Integer idGrado) {
		
		String sql = 
				"select distinct(r) "
				+ " from Usuario u INNER JOIN u.disciplinaGrados udg ,"
				+ "		DisciplinaGrado dg INNER JOIN dg.grado g,"
				+ "		Recurso r INNER JOIN r.disciplinaGrados rdg "
				+ " where "
				+ "		dg.id = rdg.id"
				+ "		and dg.grado.id <= udg.grado.id " // + (CASE WHEN u.rol = 'ADMINISTRADOR' THEN 8 ELSE CASE WHEN u.rol = 'PROFESOR' THEN 2 ELSE 1 END END)"
				+ " 	and udg.disciplina.id = dg.disciplina.id "
				+ " 	and udg.disciplina.id = :idDisciplina "
				+ "		and u.id = :idUsuario "
				+ "		and rdg.disciplina.id=:idDisciplina "
				+ "		and rdg.grado.id=:idGrado "
				+ ""
				+ " order by r.tipo.id asc, r.id asc";
		Query query =  getEntityManager().createQuery(sql);
		query.setParameter("idUsuario", idUser);
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
		return (List<Recurso>) query.getResultList();
		
		
		/*
		Query query =  getEntityManager().createQuery("SELECT r FROM Recurso r INNER JOIN r.disciplinaGrados dg WHERE dg.id in ("
				+ "select distinct dg2.id FROM Usuario u INNER JOIN u.disciplinaGrados dg2 WHERE dg2.disciplina.id = :idDisciplina and u.id=:idUsuario and dg2.grado.id=:idGrado"
				+ ") order by dg.disciplina.id asc, r.enPrograma desc, dg.grado.id desc, r.id asc");
		query.setParameter("idUsuario", idUser);
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
		return (List<Recurso>) query.getResultList();
		*/
	}

	@SuppressWarnings("unchecked")
	public List<Fichero> ficherosRecursoUsuario(Integer idRecurso, Integer idUsuario, Integer version) {
		String sql = "SELECT DISTINCT new es.bris.budolearning.model.Fichero"
				+ "	( f.id, f.descripcion, f.nombreFichero, f.extension, f.fecha, f.recurso, f.activo, f.tamano, "
				+ "   (select count(l.id) from LogDownloadFile l where l.fichero.id=f.id and l.usuario.id=:idUsuario), "
				+ "   f.coste,"
				+ "	  f.segundos,"
				+ "	  f.propio"
				+ " ) from Fichero f "
				+ "WHERE f.recurso.id = :idRecurso and f.activo=true "; 
		if(version < 9 ) sql+="and f.extension not in ('pdf')";
		Query query =  getEntityManager().createQuery(sql);
		query.setParameter("idRecurso", idRecurso);
		query.setParameter("idUsuario", idUsuario);
		List<Fichero> lista = (List<Fichero>) query.getResultList();
		for(Fichero f:lista){
			if(ficheroSinCoste(f.getId(), idUsuario)){
				f.setCoste(0);
			} else {
				int multiplicador = 1000;
				
				for(DisciplinaGrado dg:f.getRecurso().getDisciplinaGrados()){
					int grado = selectGradoDisciplinaUsuario(dg.getDisciplina().getId(), idUsuario);
					if(multiplicador > dg.getGrado().getId() - grado) {
						multiplicador = dg.getGrado().getId() - grado;
					}
					
				}
				
				f.setCoste(f.getCoste()*multiplicador);
			}
		}
		return lista;
	}
	
	public int selectGradoDisciplinaUsuario(Integer idDisciplina, Integer idUsuario) {
		String sql = "SELECT max(g.id) FROM Usuario u inner join u.disciplinaGrados dg inner join dg.grado g where u.id=:idUsuario and dg.disciplina.id=:idDisciplina";
		Query query =  getEntityManager().createQuery(sql);
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idUsuario", idUsuario);
		int max_grado = 0;
		try{ 
			max_grado = (int) query.getSingleResult();
		} catch(Exception e) {
			
		}
		return max_grado;
	}
	
	public boolean ficheroSinCoste(Integer idUsuario, Integer idFichero) {
		boolean ficheroSinCoste = false;
		Query query = getEntityManager()
				.createQuery("SELECT distinct(f) FROM Usuario u join u.disciplinaGrados dg1, Recurso r join r.disciplinaGrados dg2, Fichero f "
						+ " WHERE "
						+ "		u.id = :idUsuario "
						+ "		and dg1.id=dg2.id "
						+ "		and r.id=f.recurso.id "
						+ "		and f.id=:idFichero"
						+ "");
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idFichero", idFichero);
		ficheroSinCoste = query.getResultList().size() > 0;
		
		if(!ficheroSinCoste){
			Query query2 = getEntityManager()
					.createQuery("SELECT distinct(p) FROM Puntos p"
							+ " WHERE "
							+ "		p.idUsuario = :idUsuario "
							+ "		and p.idFichero = :idFichero"
							+ "");
			query2.setParameter("idUsuario", idUsuario);
			query2.setParameter("idFichero", idFichero);
			ficheroSinCoste |= query2.getResultList().size() > 0;
		}
		
		if(!ficheroSinCoste){
			Query query3 = getEntityManager()
					.createQuery("SELECT distinct(ve) FROM VideoEspecial ve, Usuario u"
							+ " WHERE "
							+ "		ve.fichero.id = :idFichero"
							+ "		and u.id=:idUsuario"
							+ "		and ("
							+ "			ve.usuario.id = :idUsuario "
							+ "			or "
							+ "			((u.entrena.id = ve.club.id or u.profesor.id = ve.club.id) and ve.usuario is null)"
							+ "		)"
							+ "");
			query3.setParameter("idUsuario", idUsuario);
			query3.setParameter("idFichero", idFichero);
			ficheroSinCoste |= query3.getResultList().size() > 0;
		}
		
		return ficheroSinCoste;
	}
	
	@SuppressWarnings("unchecked")
	public List<Fichero> ficherosRecursoUsuarioTodos(Integer idRecurso, Integer idUsuario) {
		Query query =  getEntityManager().createQuery("Select distinct new es.bris.budolearning.model.Fichero(f.id, f.descripcion, f.nombreFichero, f.extension, f.fecha, f.recurso, f.activo, f.tamano, (select count(l.id) from LogDownloadFile l where l.fichero.id=f.id and l.usuario.id=:idUsuario), f.coste, f.segundos, f.propio) from Fichero f WHERE f.recurso.id = :idRecurso");
		query.setParameter("idRecurso", idRecurso);
		query.setParameter("idUsuario", idUsuario);
		List<Fichero> lista = (List<Fichero>) query.getResultList();
		for(Fichero f:lista){
			if(ficheroSinCoste(f.getId(), idUsuario)){
				f.setCoste(0);
			} else {
				f.setCoste(f.getCoste()* multiplicador(idUsuario, f.getRecurso().getDisciplinaGrados()));
			}
		}
		return lista;
	}
	
	public int multiplicador (Integer idUsuario, List<DisciplinaGrado> list){
		int multiplicador = 1000;
		for(DisciplinaGrado dg:list){
			int grado = selectGradoDisciplinaUsuario(dg.getDisciplina().getId(), idUsuario);
			if(multiplicador > dg.getGrado().getId() - grado) multiplicador = dg.getGrado().getId() - grado;
		}
		return multiplicador-1;
		
	}
	
	public AndroidUsuario loginAndroid (String login, String pass, Integer versionAndroid){
		Usuario u = login(login, pass);
		if(versionAndroid > 0) u.setVersionAndroid(versionAndroid);
		return transformAndroidUsuario(u, versionAndroid);
	}
	
	public AndroidUsuario transformAndroidUsuario(Usuario u, int versionAndroid) {
		AndroidUsuario usuario = new AndroidUsuario();
		if(u.getId() > 0){
			usuario = new AndroidUsuario(u);
			
			if(versionAndroid < 9){
				List<AndroidDisciplina> disciplinas = usuario.toListAndroidDisciplina(disciplinasUsuario(u.getId()));
				usuario.setDisciplinas(disciplinas);
				for(AndroidDisciplina ad: usuario.getDisciplinas()){
					List<AndroidGrado> grados = usuario.toListAndroidGrado(gradosDisciplinaUsuario(u.getId(), ad.getId()));
					ad.setGrados(grados);
					for(AndroidGrado ag: ad.getGrados()){
						List<AndroidRecurso> recursos = usuario.toListAndroidRecursos(recursosGradoDisciplinaUsuario(u.getId(), ad.getId(),ag.getId()));
						Collections.sort(recursos, new Comparator<AndroidUsuario.AndroidRecurso>() {
							@Override
							public int compare(AndroidRecurso o1, AndroidRecurso o2) {
								if(o1.isEnPrograma() && !o2.isEnPrograma()) 
									return -1;
								else if(!o1.isEnPrograma() && o2.isEnPrograma())
									return 1;
								else
									return o1.getId() - o2.getId();
							}
						});
						ag.setRecursos(recursos);
						for(AndroidRecurso ar: ag.getRecursos()){
							if(!"ADMINISTRADOR".equalsIgnoreCase(u.getRol())){
								ar.setFicheros(usuario.toListAndroidFicheros(ficherosRecursoUsuario(ar.getId(), u.getId(), versionAndroid)));
							} else {
								ar.setFicheros(usuario.toListAndroidFicheros(ficherosRecursoUsuarioTodos(ar.getId(), u.getId())));
							}
						}
					}
				}
			} else {
				List<AndroidDisciplina> disciplinas = usuario.toListAndroidDisciplina(disciplinasUsuario(u.getId()));
				usuario.setDisciplinas(disciplinas);
				for(AndroidDisciplina ad: usuario.getDisciplinas()){
					List<AndroidGrado> grados = usuario.toListAndroidGrado(todosGrados(ad.getId()));
					ad.setGrados(grados);
					for(AndroidGrado ag: ad.getGrados()){
						List<AndroidRecurso> recursos = usuario.toListAndroidRecursos(todosRecursos(ad.getId(),ag.getId()));
						Collections.sort(recursos, new Comparator<AndroidUsuario.AndroidRecurso>() {
							@Override
							public int compare(AndroidRecurso o1, AndroidRecurso o2) {
								if(o1.isEnPrograma() && !o2.isEnPrograma()) 
									return -1;
								else if(!o1.isEnPrograma() && o2.isEnPrograma())
									return 1;
								else
									return o1.getId() - o2.getId();
							}
						});
						ag.setRecursos(recursos);
						for(AndroidRecurso ar: ag.getRecursos()){
							if(!"ADMINISTRADOR".equalsIgnoreCase(u.getRol())){
								ar.setFicheros(usuario.toListAndroidFicheros(ficherosRecursoUsuario(ar.getId(), u.getId(), versionAndroid)));
							} else {
								ar.setFicheros(usuario.toListAndroidFicheros(ficherosRecursoUsuarioTodos(ar.getId(), u.getId())));
							}
						}
					}
				}
			}
		}
		return usuario;
	}

	public AndroidUsuario getUsuario (int idUsuario){
		AndroidUsuario usuario = null;
		Usuario u = getEntityManager().find(Usuario.class, idUsuario);
		if(u.getId() > 0){
			usuario = new AndroidUsuario(u);
			List<AndroidDisciplina> disciplinas = usuario.toListAndroidDisciplina(disciplinasUsuario(u.getId()));
			usuario.setDisciplinas(disciplinas);
			for(AndroidDisciplina ad: usuario.getDisciplinas()){
				List<AndroidGrado> grados = usuario.toListAndroidGrado(gradosDisciplinaUsuario(u.getId(), ad.getId()));
				ad.setGrados(grados);
				for(AndroidGrado ag: ad.getGrados()){
					List<AndroidRecurso> recursos = usuario.toListAndroidRecursos(recursosGradoDisciplinaUsuario(u.getId(), ad.getId(),ag.getId()));
					ag.setRecursos(recursos);
					for(AndroidRecurso ar: ag.getRecursos()){
						List<AndroidFichero> ficheros = usuario.toListAndroidFicheros(ficherosRecursoUsuario(ar.getId(), u.getId(), 0));
						ar.setFicheros(ficheros);
					}
				}
			}
		}
		return usuario;
	}
	
	public Android obtenerUltimaVersion(){
		return obtenerUltimaVersion("budolearning");
	}
	
	@SuppressWarnings("unchecked")
	public Android obtenerUltimaVersion(String aplicacion){
		Query query = getEntityManager().createQuery("select v FROM Android v where v.aplicacion=:aplicacion order by id desc");
		query.setParameter("aplicacion", aplicacion);
		List<Android> lista = query.getResultList();
		if (lista.isEmpty())
			return new Android();
		else 
			return lista.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Club> clubes() {
		Query query = getEntityManager().createQuery("select v FROM Club v order by id asc");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<AndroidUsuario> getAlumnos(Integer idUsuario, Integer idClub) {
		Query query = getEntityManager().createQuery("select u FROM Usuario u where u.entrena.id=:idClub order by id asc");
		query.setParameter("idClub", idClub);
		List<Usuario> usuarios = query.getResultList();
		List<AndroidUsuario> resultado = new ArrayList<AndroidUsuario>();
		for(Usuario u:usuarios){
			AndroidUsuario usuario = new AndroidUsuario(u);
			List<AndroidDisciplina> disciplinas = usuario.toListAndroidDisciplina(disciplinasUsuario(u.getId()));
			usuario.setDisciplinas(disciplinas);
			for(AndroidDisciplina ad: usuario.getDisciplinas()){
				List<AndroidGrado> grados = usuario.toListAndroidGrado(gradosDisciplinaUsuario(u.getId(), ad.getId()));
				ad.setGrados(grados);
				for(AndroidGrado ag: ad.getGrados()){
					List<AndroidRecurso> recursos = usuario.toListAndroidRecursos(recursosGradoDisciplinaUsuario(u.getId(), ad.getId(),ag.getId()));
					ag.setRecursos(recursos);
					/*
					for(AndroidRecurso ar: ag.getRecursos()){
						List<AndroidFichero> ficheros = usuario.toListAndroidFicheros(ficherosRecursoUsuario(ar.getId()));
						ar.setFicheros(ficheros);
					}
					*/
				}
			}
			
			Query query2 = getEntityManager().createQuery("FROM Puntos p where p.idUsuario=:idUsuario");
			query2.setParameter("idUsuario", u.getId());
			int puntos = 0;
			for(Object p:query2.getResultList()){
				puntos += ((Puntos)p).getPuntos();
			}
		    usuario.setPuntos(puntos);
			resultado.add(usuario);
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public AndroidUsuario getAlumnoSubirGrado(Integer idUsuario, Integer idAlumno, Integer idDisciplina, Integer idGrado) {
		Usuario usuario = getEntityManager().find(Usuario.class, idAlumno);
		Query query = getEntityManager().createQuery("select dg FROM DisciplinaGrado dg where dg.disciplina.id=:idDisciplina and dg.grado.id>:idGrado order by dg.grado.id asc");
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("idGrado", idGrado);
		List<DisciplinaGrado> ldg = query.getResultList();
		DisciplinaGrado dg = null;
		if(ldg != null && ldg.size() > 0) dg = ldg.get(0);
		if(dg != null){
			usuario.getDisciplinaGrados().add(dg);
			getEntityManager().merge(usuario);
			return transformAndroidUsuario(usuario,0);		
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<EstadisticaVideosUsuario> getEstadisticas(Integer idUsuario) {
		Query query = getEntityManager().createQuery("select new es.bris.budolearning.model.EstadisticaVideosUsuario ("
				+ " l.fichero.id, l.fichero.descripcion, count(l.descargado), sum(l.descargado), max(l.fecha)"
				+ " ) "
				+ " from LogDownloadFile l "
				+ " where l.usuario.id=:idUsuario"
				+ "	and l.fecha between :fecha1 and :fecha2 "
				+ " group by l.fichero.id, l.fichero.descripcion "
				+ " order by count(l.descargado) desc");
		query.setParameter("idUsuario", idUsuario);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DATE, 1);
		query.setParameter("fecha2", c.getTime());
		c.add(Calendar.MONTH, -3);
		query.setParameter("fecha1", c.getTime());
		
		return (List<EstadisticaVideosUsuario>) query.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	private List<Grado> todosGrados(Integer idDisciplina) {
		Query query =  getEntityManager().createQuery(
				" select distinct(g) "
				+ " FROM DisciplinaGrado dg "
				+ "	INNER JOIN dg.grado g "
				+ " where dg.disciplina.id=:idDisciplina"
				+ " order by g.id desc"
		);
		query.setParameter("idDisciplina", idDisciplina);
		return (List<Grado>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AndroidDisciplina> obtenerTodo(){
		Query query = getEntityManager().createQuery("SELECT d FROM Disciplina d");
		AndroidUsuario usuario = new AndroidUsuario();
		List<AndroidDisciplina> disciplinas = usuario.toListAndroidDisciplina((List<Disciplina>) query.getResultList());
		usuario.setDisciplinas(disciplinas);
		for(AndroidDisciplina ad: usuario.getDisciplinas()){
			List<AndroidGrado> grados = usuario.toListAndroidGrado(todosGrados(ad.getId()));
			ad.setGrados(grados);
			for(AndroidGrado ag: ad.getGrados()){
				List<AndroidRecurso> recursos = usuario.toListAndroidRecursos(todosRecursos(ad.getId(),ag.getId()));
				Collections.sort(recursos, new Comparator<AndroidUsuario.AndroidRecurso>() {
					@Override
					public int compare(AndroidRecurso o1, AndroidRecurso o2) {
						if(o1.isEnPrograma() && !o2.isEnPrograma()) 
							return -1;
						else if(!o1.isEnPrograma() && o2.isEnPrograma())
							return 1;
						else
							return o1.getId() - o2.getId();
					}
				});
				ag.setRecursos(recursos);
				for(AndroidRecurso ar: ag.getRecursos()){
					ar.setFicheros(usuario.toListAndroidFicheros(ficherosRecursoUsuarioTodos(ar.getId(), 1)));
				}
			}
		}
		return disciplinas;
		
	}
}
