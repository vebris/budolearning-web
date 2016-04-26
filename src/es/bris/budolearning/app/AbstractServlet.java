package es.bris.budolearning.app;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.bris.budolearning.dao.UsuarioDAOLocal;
import es.bris.budolearning.model.Usuario;

public abstract class AbstractServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected final Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());
	
	@EJB
	UsuarioDAOLocal usuarioDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		processRequest(request,  response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		processRequest(request,  response);
	}
	
	protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	
	public void forward (HttpServletRequest request, HttpServletResponse response, UsuarioDAOLocal usuarioDAO, String pagina)
			throws ServletException, IOException {
		
		String rutaPagina = "app/";
		Usuario usuario = getUsuario(request, usuarioDAO);
		if("ADMINISTRADOR".equalsIgnoreCase(usuario.getRol())) {
			rutaPagina += "admin";
		} else if("PROFESOR".equalsIgnoreCase(usuario.getRol())) {
			rutaPagina += "profesor";
		} else if("USER".equalsIgnoreCase(usuario.getRol())) {
			rutaPagina += "usuario";
		}
		
		LOGGER.finer("Reenvio " + rutaPagina+"/"+pagina);
		
		request.getRequestDispatcher(rutaPagina+"/"+pagina).forward(request, response);
	}
	
	public Usuario getUsuario(HttpServletRequest request, UsuarioDAOLocal usuarioDAO) {
		String nombreUsuario = request.getUserPrincipal().getName();
		List<Usuario> lista = usuarioDAO.buscarUsuarios(nombreUsuario);
		if(lista.size() > 0){
			request.setAttribute("usuarioLogado", lista.get(0));
			return lista.get(0);
		} else {
			return new Usuario();
		}
	}

}
