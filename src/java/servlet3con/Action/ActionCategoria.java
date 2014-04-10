/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet3con.Action;

import dao.impl.CategoriaDAO;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet3con.ServletController.EncaminharDados;
import dominio.Categoria;

/**
 *
 * @author Gerson
 */
public class ActionCategoria extends Action implements EncaminharDados{
    public void executa(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=ISO-8859-9");
        PrintWriter out = response.getWriter();
         
        try {
            String metodo = request.getParameter("metodo");      
             
            if (metodo.equals("cadastrar")) {
                out.println(cadastrar(request));                
            } else if (metodo.equals("preencherForm")) {
                out.println(preencherForm(new Integer (request.getParameter("id_categoria"))));                
            } else if (metodo.equals("atualizar")) {
                out.println(atualizar(request));                
            } else if (metodo.equals("excluir")) {
                out.println(excluir(new Integer (request.getParameter("id_categoria"))));                
            }
        } finally {
            out.close();
        }
    }
     
    //  Cadastrar  /////////////////////////////////////////////////////////////
    private String cadastrar (HttpServletRequest request) throws ParseException{
        StringBuilder sb = new StringBuilder();
         
        //Pegando os parametros do request
        String nome = request.getParameter("nome");
//        String email = request.getParameter("email");
//        Long telefone = Long.parseLong(request.getParameter("telefone"));
//        String dataEmTexto = request.getParameter("data");
 
        //Monta um objeto contato
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        //Categoria.setDatanascimento(new java.sql.Date( new java.text.SimpleDateFormat("dd/mm/yyyy").parse(dataEmTexto).getTime()));
 
        //Grave nessa conexão!!!
        CategoriaDAO dao = new CategoriaDAO();
        if (dao.salvar(categoria)) {
            //Imprime messagem comfirmando o cadastro
            sb.append("<h1>O cadastro foi concluido!</h1></br>");
            sb.append("Categoria ").append(categoria.getNome()).append(" adicionado com sucesso<br>");
            sb.append("<br/><br/><a href='../categoria/index.jsp'>Voltar para CRUD Hibernate + JSP + Servlet 'controladora'</a>");
        } else{
            //Exibe mensagem de erro.
            sb.append("Erro ao realizar cadastro!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
     
    //  Preecheer formulário  //////////////////////////////////////////////////
    private String preencherForm (Integer id){
        StringBuilder sb = new StringBuilder();
         
        
             
        
        //Pesquisando Categoria para preecher o form
        CategoriaDAO dao = new CategoriaDAO();
        Categoria categoria = dao.consultarPorId(id);
        
        //Convertendo data SQL para String
       // String dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(Categoria.getDatanascimento());
        sb.append("<h4>Formulário para atualização de dados<br/>");
        sb.append("\nDeve-se preencher todos os campos.</h4>");  //'\n' e '\t' Para formatar a saída do código em html,
        sb.append("\n<form method='post' action='servletController'>"  //'\n' e '\t' para conferir de um CTRL+U na pagina do formulário de atualização.
                    + "\n\t<input type='hidden' name='encaminhar' value='ActionCategoria'/>"
                    + "\n\t<input type='hidden' name='metodo' value='atualizar'/>"
                    + "\n\tCod. Categoria: <input type='text' name='id_categoria' value='"+categoria.getId()+"' readonly='readonly' /> <br/>"
                    + "\n\tNome: <input type='text' name='nome' value='"+categoria.getNome()+"' /> <br/>"
                   
                   
                    //+ "\n\tData de nascimento: <input type='text' name='data' value='"+dataString+"' />dd/mm/yyyy (Formado da data) <br/>"
                    + "\n\t<input type='submit' value='Atualizar'/>"
                    + "\n</form>");
        sb.append("\n<br/>\n<a href='../categoria/index.jsp'>Cancelar</a>");
        return sb.toString();
    }
     
    //  Atualizar  /////////////////////////////////////////////////////////////
    private String atualizar (HttpServletRequest request) throws ParseException{
        StringBuilder sb = new StringBuilder();
         
        //Pegando os parametros do request
        Integer id = Integer.parseInt(request.getParameter("id_categoria"));
        String nome = request.getParameter("nome");
        
        //Monta um objeto contato
        Categoria categoria = new Categoria();
        categoria.setId(id);//Id do Categoria a ser alterado
        categoria.setNome(nome);
        //Categoria.setDatanascimento(new java.sql.Date( new java.text.SimpleDateFormat("dd/mm/yyyy").parse(dataEmTexto).getTime()));
        //sb.append("Id: " + categoria.getId() );
        
        CategoriaDAO dao = new CategoriaDAO();
        
        if (dao.alterar(categoria)) {
            //Imprime messagem comfirmando atualização
            sb.append("<h1>O cadastro foi atualizado!</h1><br/>");
            sb.append("Categoria " + categoria.getNome() + " atualizado com sucesso<br/>");
            sb.append("<br/><br/><a href='../categoria/index.jsp'>Voltar para CRUD Hibernate + JSP + Servlet 'mapeamento coletivo'</a>");
        } else{
            //Exibe mensagem de erro.
            sb.append("Erro ao realizar a atualização!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
 
    //  Excluir  ///////////////////////////////////////////////////////////////
    private String excluir (Integer id){
        StringBuilder sb = new StringBuilder();
 
        //Busca o Categoria e monta vo
        CategoriaDAO dao = new CategoriaDAO();
        Categoria categoria = (Categoria)dao.consultar(new Categoria(id)).get(0);
        
        CategoriaDAO dao2 = new CategoriaDAO();
        //Envia vo do Categoria para o método exclir
        if (dao2.excluir(categoria)) {
            //Imprime messagem comfirmando exclusão
            sb.append("<h1>O Categoria foi deletado!</h1><br/>");
            sb.append("Categoria " + categoria.getNome() + " deletado com sucesso</br>");
            sb.append("<br/><br/><a href='../categoria/index.jsp'>Voltar para CRUD Hibernate + JSP + Servlet 'mapeamento coletivo'</a>");
        } else{
            //Exibe mensagem de erro.
            sb.append("Erro ao deletar Categoria!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
}
