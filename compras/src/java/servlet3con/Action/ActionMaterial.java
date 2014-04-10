/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet3con.Action;

import dao.impl.CategoriaDAO;
import dao.impl.MaterialDAO;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet3con.ServletController.EncaminharDados;
import dominio.Categoria;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class ActionMaterial extends Action implements EncaminharDados{
    public void executa(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=ISO-8859-9");
        PrintWriter out = response.getWriter();
         
        try {
            String metodo = request.getParameter("metodo");      
             
            if (metodo.equals("cadastrar")) {
                out.println(cadastrar(request));                
            } else if (metodo.equals("preencherForm")) {
                out.println(preencherForm(new Integer (request.getParameter("id_material"))));                
            } else if (metodo.equals("atualizar")) {
                out.println(atualizar(request));                
            } else if (metodo.equals("excluir")) {
                out.println(excluir(new Integer (request.getParameter("id_material"))));                
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
        Material material = new Material();
        material.setNome(nome);
        material.setCategoria(new Categoria(Integer.parseInt(request.getParameter("categoria_id"))));
        //Material.setDatanascimento(new java.sql.Date( new java.text.SimpleDateFormat("dd/mm/yyyy").parse(dataEmTexto).getTime()));
 
        //Grave nessa conexão!!!
        MaterialDAO dao = new MaterialDAO();
        if (dao.salvar(material)) {
            //Imprime messagem comfirmando o cadastro
            sb.append("<h1>O cadastro foi concluido!</h1></br>");
            sb.append("Material ").append(material.getNome()).append(" adicionado com sucesso<br>");
            sb.append("<br/><br/><a href='../material/index.jsp'>Voltar para CRUD Hibernate + JSP + Servlet 'controladora'</a>");
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
         
        
        // Carrega todos as categorias disponiveis
        CategoriaDAO daoc = new CategoriaDAO();
        List materiais = daoc.consultar(null);
        String options = "";
        for (Iterator iteratorM = materiais.iterator(); iteratorM.hasNext();) {
            Categoria cat = (Categoria) iteratorM.next();
            options = options + "\n\t\t<option value='" + cat.getId() + "'>" + cat.getNome() + "</option>";
        }
        
        //Pesquisando Material para preecher o form
        MaterialDAO dao = new MaterialDAO();
        Material material = dao.consultarPorId(id);
        
        //Convertendo data SQL para String
       // String dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(Material.getDatanascimento());
        sb.append("<h4>Formulário para atualização de dados<br/>");
        sb.append("\nDeve-se preencher todos os campos.</h4>");  //'\n' e '\t' Para formatar a saída do código em html,
        sb.append("\n<form method='post' action='servletController'>"  //'\n' e '\t' para conferir de um CTRL+U na pagina do formulário de atualização.
                    + "\n\t<input type='hidden' name='encaminhar' value='ActionMaterial'/>"
                    + "\n\t<input type='hidden' name='metodo' value='atualizar'/>"
                    + "\n\tCod. Material: <input type='text' name='id_material' value='"+material.getId()+"' readonly='readonly' /> <br/>"
                    + "\n\tNome: <input type='text' name='nome' value='"+material.getNome()+"' /> <br/>"
                    + "\n\tCateogira: <select name='categoria_id' required>"
                    + "\n\t\t<option value=''>[ Selecione uma Categoria]</option>");
                   

        try{

             String selected;
            //for(Categoria Categoria : listac){
                //selected=(material.getCategoria().getId()==Categoria.getId())?" selected " : "";
            sb.append(options);

            //}
        }catch(Exception e){
            
        }
                   
        sb.append("</select><br>"
                    //+ "\n\tData de nascimento: <input type='text' name='data' value='"+dataString+"' />dd/mm/yyyy (Formado da data) <br/>"
                    + "\n\t<input type='submit' value='Atualizar'/>"
                    + "\n</form>");
        sb.append("\n<br/>\n<a href='../material/index.jsp'>Cancelar</a>");
        return sb.toString();
    }
     
    //  Atualizar  /////////////////////////////////////////////////////////////
    private String atualizar (HttpServletRequest request) throws ParseException{
        StringBuilder sb = new StringBuilder();
         
        //Pegando os parametros do request
        Integer id = Integer.parseInt(request.getParameter("id_material"));
        String nome = request.getParameter("nome");
        
        //Monta um objeto contato
        Material material = new Material();
        material.setId(id);//Id do Material a ser alterado
        material.setNome(nome);
        material.setCategoria(new Categoria(Integer.parseInt(request.getParameter("categoria_id"))));
        //Material.setDatanascimento(new java.sql.Date( new java.text.SimpleDateFormat("dd/mm/yyyy").parse(dataEmTexto).getTime()));
        //sb.append("Id: " + material.getId() );
        
        MaterialDAO dao = new MaterialDAO();
        
        if (dao.alterar(material)) {
            //Imprime messagem comfirmando atualização
            sb.append("<h1>O cadastro foi atualizado!</h1><br/>");
            sb.append("Material " + material.getNome() + " atualizado com sucesso<br/>");
            sb.append("<br/><br/><a href='../material/index.jsp'>Voltar para CRUD Hibernate + JSP + Servlet 'mapeamento coletivo'</a>");
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
 
        //Busca o Material e monta vo
        MaterialDAO dao = new MaterialDAO();
        Material material = (Material)dao.consultar( new Material(id) ).get(0);
        //Material material =  new Material(id);
        
        
        MaterialDAO dao2 = new MaterialDAO();
        //Envia vo do Material para o método exclir
        if (dao2.excluir(material)) {
            //Imprime messagem comfirmando exclusão
            sb.append("<h1>O Material foi deletado!</h1><br/>");
            sb.append("Material " + material.getNome() + " deletado com sucesso</br>");
            sb.append("<br/><br/><a href='../material/index.jsp'>Voltar para CRUD Hibernate + JSP + Servlet 'mapeamento coletivo'</a>");
        } else{
            //Exibe mensagem de erro.
            sb.append("Erro ao deletar Material!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
}
