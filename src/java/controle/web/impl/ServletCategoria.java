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
import dominio.Categoria;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Servlet implementation class ServletCategoria
 */
public class ServletCategoria extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCategoria() {
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
			Categoria categoria = new Categoria();
			IFachada fachada = new Fachada();
			List<EntidadeDominio> categorias = fachada.consultar(categoria);
			request.setAttribute("categorias", categorias);
			request.getRequestDispatcher("/categoria/adicionado.jsp").
					forward(request, response);
//		} else if (cmd.equals(OPERACAO.ADICIONAR.toString())) {
//			PadraoSalarial padrao = new PadraoSalarial();
//			IFachada fachada = new Fachada();
//			List<EntidadeDominio> padroes = fachada.consultar(padrao);
//			request.setAttribute("categorias", categorias);
//			request.getRequestDispatcher("/categoria/adicionado.jsp").
//					forward(request, response);
		} else if (cmd.equals(OPERACAO.EXCLUIR.toString())) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			IFachada fachada = new Fachada();			
			String msg = fachada.excluir(new Categoria(id));
			
			request.setAttribute("categoria", new Categoria(id));
			request.getRequestDispatcher("/categoria/excluido.jsp").
					forward(request, response);
		} else if (cmd.equals(OPERACAO.EDITAR.toString())) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			Fachada fachada = new Fachada();
			Categoria obj = (Categoria) fachada.consultar( new Categoria(id) ).get(0);
			
			CategoriaDAO daoc = new CategoriaDAO();
			List categorias = daoc.consultar(null);
			
			
			request.setAttribute("categoria", obj);
			request.setAttribute("categorias", categorias);
			request.getRequestDispatcher("/categoria/edit.jsp").
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
				Integer categoria_id = Integer.parseInt(request.getParameter("categoria_id"));

				Categoria categoria = new Categoria();
				categoria.setId(categoria_id);
				categoria.setNome(nome);

				IFachada fachada = new Fachada();
				String msg = fachada.alterar(categoria);

				if (msg == null) {
					request.setAttribute("func", categoria);
					request.getRequestDispatcher("/categoria/adicionado.jsp").
							forward(request, response);
				} else {
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/categoria/erro.jsp").
							forward(request, response);
				}

		}else{		
		
			//GRAVAR
			String nome = request.getParameter("nome");
			//Integer categoria_id = Integer.parseInt(request.getParameter("categoria_id"));

			Categoria categoria = new Categoria();
			categoria.setNome(nome);

			IFachada fachada = new Fachada();
			String msg = fachada.salvar(categoria);

			if (msg == null) {
				request.setAttribute("func", categoria);
				request.getRequestDispatcher("/categoria/adicionado.jsp").
						forward(request, response);
			} else {
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/categoria/erro.jsp").
						forward(request, response);
			}
		}
	}

}
