/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet3con.Action;

import dao.impl.CompraDAO;
import dao.impl.CotacaoDAO;
import dao.impl.MaterialDAO;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet3con.ServletController.EncaminharDados;
import dominio.Compra;
import dominio.Cotacao;
import dominio.CotacaoMaterial;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class ActionCotacao  extends Action implements EncaminharDados{
    public void executa(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=ISO-8859-9");
        PrintWriter out = response.getWriter();
         
        try {
            String metodo = request.getParameter("metodo");      
             
            if (metodo.equals("adicionar")) {
                out.println(adicionar(request));                
            } else if (metodo.equals("edit")) {
                out.println(form(new Integer (request.getParameter("cotacao_id"))));                
            } else if (metodo.equals("atualizar")) {
                out.println(atualizar(request));                
            } else if (metodo.equals("excluir")) {
                out.println(excluir(new Integer (request.getParameter("cotacao_id")),request));                
            }
        } finally {
            out.close();
        }
    }
     
    //  Preecheer formulário  //////////////////////////////////////////////////
    private String adicionar (HttpServletRequest request) throws ParseException{
        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(false);
//        
//        //MaterialDAO dao = new MaterialDAO();
//        //Material material = dao.consultarPorId(new Integer(request.getParameter("material_id")));
//        
//        Set tmp_cotacoes = (Set<Cotacao>) session.getAttribute("compra_cotacoes");
//        tmp_cotacoes.add(new Cotacao(null , new Integer(request.getParameter("fornecedor_id")),null ));
//        session.setAttribute("compra_cotacoes", tmp_cotacoes);
        //HttpSession session = request.getSession(true);
        
        Cotacao cot = new Cotacao(new Compra(new Integer(request.getParameter("compra_id"))), new Integer(request.getParameter("fornecedor_id")),null );
        
        CotacaoDAO dao = new CotacaoDAO();
        if(dao.salvar(cot)){
            sb.append("<tr class='"+cot.getId()+"'>");
            sb.append("<td><a rel='"+cot.getId()+"' href='#' class='deleteCotacao'><i class='glyphicon glyphicon-trash'></i></a></td>");
            sb.append("<td><a rel='"+cot.getId() + "' href='#' class='editCotacao'>"+request.getParameter("fornecedor_id")+"</a></td>");
            sb.append("<td><span class='"+cot.getId() + "total'></span></td>");
            sb.append("</tr>");
        }else{
            sb.append("0");
        }
        
        //session.setAttribute("compra_cotacoes", tmp_cotacoes);
        
        
        //Convertendo data SQL para String
        // String dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(Compra.getDatanascimento());
//        sb.append("<tr class='"+(tmp_cotacoes.size()-1)+"'>");
//        sb.append("<td><a rel='"+(tmp_cotacoes.size()-1)+"' href='#' class='deleteCotacao'><i class='glyphicon glyphicon-trash'></i></a></td>");
//        sb.append("<td>"+request.getParameter("fornecedor_id")+"</td>");
//        sb.append("</tr>");
        return sb.toString();
    }
    
    private String form(Integer id) {
        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(true);
        
        MaterialDAO daom = new MaterialDAO();
        List materiais = daom.consultar(null);
        String options = "";
        for (Iterator iteratorM = materiais.iterator(); iteratorM.hasNext();) {
            Material material = (Material) iteratorM.next();
            options = options + "\n\t\t<option value='" + material.getId() + "'>" + material.getNome() + "</option>";
        }

        //Pesquisando Compra para preecher o form
        CotacaoDAO dao = new CotacaoDAO();
        Cotacao cotacao = dao.consultarPorId(id);
        Set<CotacaoMaterial> cotacao_materiais = cotacao.getCotacaoMaterials();
        
        //Salva os materias na session
        //Convertendo data SQL para String
        String dataString = "";
        
        sb.append("<form class='form-inline' id='frmCotacaoMaterial' method='post' action='servletController'>"
                + "\n\t<input type='hidden' name='encaminhar' value='ActionCotacaoMaterial'/>"
                + "\n\t<input type='hidden' name='metodo' value='adicionar'/>"
                + "\n\t<input type='hidden' name='cotacao_id' id='cotacao_id' value='"+id+"'/>"
                + "\n\t<select class=\"form-control\" name='material_id'>"
                + "\n\t\t<option value=\"\">[ Selecione um Material ]</option>"
                + options
                + "\n\t</select>"
                + "<input class=\"form-control\" type='text' name='valor_unitario' placeholder='valor_unitario'>"
                + "<input class='btn btn-info' type='submit' value='Inserir'>");
        sb.append("</form>");
        sb.append("<table class='table table=bordered table-striped'>");
        try {
            int i = 0;
            for (Iterator iterator2 = cotacao_materiais.iterator(); iterator2.hasNext();) {
                CotacaoMaterial cotacao_material = (CotacaoMaterial) iterator2.next();

                sb.append("<tr class='" + cotacao_material.getId() + "'>");
                sb.append("<td><a rel='" + cotacao_material.getId() + "' href='#' class='deleteCotacaoMaterial'><i class='glyphicon glyphicon-trash'></i></a></td>");
                sb.append("<td>" + cotacao_material.getMaterial().getNome() + "</td>");
                sb.append("<td><span class='"+cotacao.getId() + "valor_unitario' rel='"+ cotacao_material.getMaterial().getId() +"'>" + cotacao_material.getValorUnitario()+ "</span></td>");
                sb.append("</tr>");
                cotacao_material = null;
                i++;
            }
        } catch (Exception e) {
        }
        cotacao = null;
        return sb.toString();
    }
    
    //  Atualizar  /////////////////////////////////////////////////////////////
    private String atualizar (HttpServletRequest request) throws ParseException{
        StringBuilder sb = new StringBuilder();
         
        //Pegando os parametros do request
        Integer id = Integer.parseInt(request.getParameter("id_compra"));
        String nome = request.getParameter("nome");
        
        //Monta um objeto contato
        Compra compra = new Compra();
        compra.setId(id);//Id do Compra a ser alterado
        //compra.setNome(nome);
        //Compra.setDatanascimento(new java.sql.Date( new java.text.SimpleDateFormat("dd/mm/yyyy").parse(dataEmTexto).getTime()));
        //sb.append("Id: " + compra.getId() );
        
        CompraDAO dao = new CompraDAO();
        
        if (dao.alterar(compra)) {
            //Imprime messagem comfirmando atualização
            sb.append("<h1>O cadastro foi atualizado!</h1><br/>");
            sb.append("Pedido de Compra atualizado com sucesso<br/>");
            sb.append("<br/><br/><a href='../compra/index.jsp'>Voltar para Compras</a>");
        } else{
            //Exibe mensagem de erro.
            sb.append("Erro ao realizar a atualização!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
 
    //  Excluir  ///////////////////////////////////////////////////////////////
    private String excluir (Integer id,HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        
        Cotacao cot = new Cotacao();
        cot.setId(id);
        
        CotacaoDAO dao = new CotacaoDAO();
        try {
            if(dao.excluir(cot)){
                sb.append("1");
            }else{
                sb.append("0");
            }
        }catch(Exception e){
            sb.append("0");
        }
        
        //Convertendo data SQL para String
        // String dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(Compra.getDatanascimento());
        
        return sb.toString();
    }
//    private String excluir (Integer id,HttpServletRequest request){
//        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(false);
//        
//        
//        Set tmp_cotacoes = (Set<Cotacao>) session.getAttribute("compra_cotacoes");
//        try {
//            int i = 0;
//            for (Iterator iterator2 = tmp_cotacoes.iterator(); iterator2.hasNext();) {
//                Cotacao cotacao = (Cotacao) iterator2.next();
//                if(i == id){
//                    cotacao.setFornecedorId(null);
//                    cotacao.setCompra(null);
//                }                
//                i++;
//            }
//            session.setAttribute("compra_cotacoes", tmp_cotacoes);
//            sb.append("1");
//        }catch(Exception e){
//            sb.append("0");
//        }
//        
//        //Convertendo data SQL para String
//        // String dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(Compra.getDatanascimento());
//        
//        return sb.toString();
//    }
}
