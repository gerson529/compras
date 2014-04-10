package controle.web.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.IFachada;
import controle.impl.Fachada;
import controle.web.util.OPERACAO;
import dao.impl.CategoriaDAO;
import dominio.Categoria;
import dominio.EntidadeDominio;
import dominio.Material;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Servlet implementation class ServletMaterial
 */
public class ServletMaterial extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletMaterial() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String cmd = request.getParameter("cmd");
		if (cmd == null) {
			cmd = "LISTAR";
		}
		if (cmd.equals(OPERACAO.LISTAR.toString())) {
			Material material = new Material();
			IFachada fachada = new Fachada();
			List<EntidadeDominio> materiais = fachada.consultar(material);
			request.setAttribute("materiais", materiais);
			request.getRequestDispatcher("/material/adicionado.jsp").
					forward(request, response);
//		} else if (cmd.equals(OPERACAO.ADICIONAR.toString())) {
//			PadraoSalarial padrao = new PadraoSalarial();
//			IFachada fachada = new Fachada();
//			List<EntidadeDominio> padroes = fachada.consultar(padrao);
//			request.setAttribute("materiais", materiais);
//			request.getRequestDispatcher("/material/adicionado.jsp").
//					forward(request, response);
		} else if (cmd.equals(OPERACAO.EXCLUIR.toString())) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			IFachada fachada = new Fachada();
			
			
			String msg = fachada.excluir(new Material(id));
			
			request.setAttribute("material", new Material(id));
			request.getRequestDispatcher("/material/excluido.jsp").
					forward(request, response);
		} else if (cmd.equals(OPERACAO.EDITAR.toString())) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			
			Fachada fachada = new Fachada();
			
			Material obj = (Material) fachada.consultar( new Material(id) ).get(0);
			
			//CategoriaDAO daoc = new CategoriaDAO();
			//List categorias = daoc.consultar(null);
			List categorias = fachada.consultar(new Categoria());
			
			request.setAttribute("material", obj);
			request.setAttribute("categorias", categorias);
			request.getRequestDispatcher("/material/edit.jsp").
					forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		if(cmd == null)
			cmd = "GRAVAR";
		if (cmd.equals(OPERACAO.ATUALIZAR.toString())) {
				String nome = request.getParameter("nome");
				Integer material_id = Integer.parseInt(request.getParameter("material_id"));
				Integer categoria_id = Integer.parseInt(request.getParameter("categoria_id"));

				Material material = new Material();
				material.setId(material_id);
				material.setNome(nome);
				material.setCategoria(new Categoria(categoria_id));

				IFachada fachada = new Fachada();
				String msg = fachada.alterar(material);

				if (msg == null) {
					request.setAttribute("func", material);
					request.getRequestDispatcher("/material/adicionado.jsp").
							forward(request, response);
				} else {
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/material/erro.jsp").
							forward(request, response);
				}

		}else{		
		
			//GRAVAR
			String nome = request.getParameter("nome");
			Integer categoria_id = Integer.parseInt(request.getParameter("categoria_id"));

			Material material = new Material();
			material.setNome(nome);
			material.setCategoria(new Categoria(categoria_id));

			IFachada fachada = new Fachada();
			String msg = fachada.salvar(material);

			if (msg == null) {
				request.setAttribute("func", material);
				request.getRequestDispatcher("/material/adicionado.jsp").
						forward(request, response);
			} else {
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/material/erro.jsp").
						forward(request, response);
			}
		}
	}

}
