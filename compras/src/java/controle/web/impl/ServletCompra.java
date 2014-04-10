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
import dominio.Compra;
import dominio.Cotacao;
import dominio.EntidadeDominio;
import dominio.Material;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class ServletMaterial
 */
public class ServletCompra extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCompra() {
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
			Material compra = new Material();
			IFachada fachada = new Fachada();
			List<EntidadeDominio> materiais = fachada.consultar(compra);
			request.setAttribute("materiais", materiais);
			request.getRequestDispatcher("/compra/adicionado.jsp").
					forward(request, response);
//		} else if (cmd.equals(OPERACAO.ADICIONAR.toString())) {
//			PadraoSalarial padrao = new PadraoSalarial();
//			IFachada fachada = new Fachada();
//			List<EntidadeDominio> padroes = fachada.consultar(padrao);
//			request.setAttribute("materiais", materiais);
//			request.getRequestDispatcher("/compra/adicionado.jsp").
//					forward(request, response);
		} else if (cmd.equals(OPERACAO.EXCLUIR.toString())) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			IFachada fachada = new Fachada();
			
			
			String msg = fachada.excluir(new Compra(id));
			
			request.setAttribute("compra", new Compra(id));
			request.getRequestDispatcher("/compra/excluido.jsp").
					forward(request, response);
		} else if (cmd.equals(OPERACAO.EDITAR.toString())) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			Fachada fachada = new Fachada();
			List materiais = fachada.consultar( new Material() );
			
			String fornecedores = "\n\t\t<option value='1'>Fornecedor 1</option>\n\t\t<option value='2'>Fornecedor 2</option>\n\t\t<option value='3'>Forencedor 3</option>";
			
			//Fachada fachada = new Fachada();
			Compra compra = (Compra) fachada.consultar(new Compra(id)).get(0);
						
			
			request.setAttribute("compra", compra);
			request.setAttribute("materiais", materiais);
			request.setAttribute("fornecedores", fornecedores);
			request.getRequestDispatcher("/compra/edit.jsp").
					forward(request, response);
		} else if (cmd.equals("FINALIZAR")) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			Fachada fachada = new Fachada();
			List materiais = fachada.consultar( new Material() );
			
			String fornecedores = "\n\t\t<option value='1'>Fornecedor 1</option>\n\t\t<option value='2'>Fornecedor 2</option>\n\t\t<option value='3'>Forencedor 3</option>";
			
			//Fachada fachada = new Fachada();
			Compra compra = (Compra) fachada.consultar(new Compra(id)).get(0);
						
			
			request.setAttribute("compra", compra);
			request.setAttribute("materiais", materiais);
			request.setAttribute("fornecedores", fornecedores);
			request.getRequestDispatcher("/compra/finalizar.jsp").
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
				//String nome = request.getParameter("nome");
				Integer id = Integer.parseInt(request.getParameter("compra_id"));
				String dataEmTexto = request.getParameter("data");
				
				Compra compra = new Compra();
				compra.setId(id);
				compra.setStatus(new Short("1"));
				try {
					compra.setDataPrazo(new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto).getTime()));
				} catch (ParseException ex) {
					Logger.getLogger(ServletCompra.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				IFachada fachada = new Fachada();
				String msg = fachada.alterar(compra);

				if (msg == null) {
					request.setAttribute("func", compra);
					request.getRequestDispatcher("/compra/atualizado.jsp").
							forward(request, response);
				} else {
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/compra/erro.jsp").
							forward(request, response);
				}

		}else if (cmd.equals("APROVAR")) {
				//String nome = request.getParameter("nome");
				Integer id = Integer.parseInt(request.getParameter("compra_id"));
				Integer cotacao_id = Integer.parseInt(request.getParameter("cotacao"));
				String nota_fiscal = request.getParameter("nota_fiscal").toString();
				
				Compra compra1 = new Compra();
				compra1.setId(id);
				
				
				IFachada fachada = new Fachada();
				
				Compra compra = (Compra) fachada.consultar(compra1).get(0);
				compra.setStatus(new Short("2"));
				compra.setNotaFiscal(nota_fiscal);
				
				Cotacao tmp_cot=new Cotacao();
				for (Iterator iterator2 = compra.getCotacaos().iterator(); iterator2.hasNext();) {
						 Cotacao cotacao = (Cotacao) iterator2.next();
						 if(cotacao.getId()==cotacao_id){
							 cotacao.setSelecionado(true);
							 tmp_cot=cotacao;
							 break;
						 }
				}
				
				String msg = fachada.alterar(compra);
				
				if (msg == null) {
					IFachada fachada2 = new Fachada();
					msg = fachada2.alterar(tmp_cot);
					if (msg == null) {
						request.setAttribute("func", compra);
						request.getRequestDispatcher("/compra/finalizado.jsp").
								forward(request, response);
					}else{
						request.setAttribute("msg", msg);
						request.getRequestDispatcher("/compra/erro.jsp").
								forward(request, response);
					}
				} else {
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/compra/erro.jsp").
							forward(request, response);
				}

		}else{		
		
			//GRAVAR
			Compra compra = new Compra();
			compra.setStatus(new Short("1"));

			IFachada fachada = new Fachada();
			String msg = fachada.salvar(compra);

			if (msg == null) {
				request.setAttribute("func", compra);
				request.getRequestDispatcher("/compra/adicionado.jsp").
						forward(request, response);
			} else {
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/compra/erro.jsp").
						forward(request, response);
			}
		}
	}

}
