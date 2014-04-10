/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet3con.Action;

import dao.impl.CompraDAO;
import dao.impl.CompraMaterialDAO;
import dao.impl.MaterialDAO;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet3con.ServletController.EncaminharDados;
import dominio.Compra;
import dominio.CompraMaterial;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class ActionCompraMaterial extends Action implements EncaminharDados {

    public void executa(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=ISO-8859-9");
        PrintWriter out = response.getWriter();

        try {
            String metodo = request.getParameter("metodo");

            if (metodo.equals("adicionar")) {
                out.println(adicionar(request));
            } else if (metodo.equals("atualizar")) {
                out.println(atualizar(request));
            } else if (metodo.equals("excluir")) {
                out.println(excluir(new Integer(request.getParameter("indice")), request));
            }
        } finally {
            out.close();
        }
    }

    //  Preecheer formulário  //////////////////////////////////////////////////
    private String adicionar(HttpServletRequest request) throws ParseException {
        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(true);
//
        MaterialDAO dao = new MaterialDAO();
        Material material = dao.consultarPorId(new Integer(request.getParameter("material_id")));
//
//        Set tmp_materiais = (Set<CompraMaterial>) session.getAttribute("compra_materiais");
//        tmp_materiais.add(new CompraMaterial(material, new Float(request.getParameter("quantidade"))));
//        session.setAttribute("compra_materiais", tmp_materiais);
//        
//        //Convertendo data SQL para String
//        // String dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(Compra.getDatanascimento());
//        sb.append("<tr class='"+(tmp_materiais.size()-1)+"'>");
//        sb.append("<td><a rel='"+(tmp_materiais.size()-1)+"' href='#' class='deleteCompraMaterial'><i class='glyphicon glyphicon-trash'></i></a></td>");
//        sb.append("<td>" + material.getNome() + "</td>");
//        sb.append("<td>" + request.getParameter("quantidade") + "</td>");
//        sb.append("</tr>");
        CompraMaterial cot = new CompraMaterial(material, new Compra(new Integer(request.getParameter("compra_id"))),new Float(request.getParameter("quantidade")));
       
        CompraMaterialDAO daocm = new CompraMaterialDAO();
        if(daocm.salvar(cot)){
            sb.append("<tr class='"+cot.getId()+"'>");
            sb.append("<td><a rel='"+cot.getId()+"' href='#' class='deleteCotacao'><i class='glyphicon glyphicon-trash'></i></a></td>");
           sb.append("<td>" + material.getNome() + "</td>");
            sb.append("<td class='"+material.getId()+"quantidade'>" + request.getParameter("quantidade") + "</td>");
            sb.append("</tr>");
        }else{
            sb.append("0");
        }
        return sb.toString();
    }

    //  Atualizar  /////////////////////////////////////////////////////////////
    private String atualizar(HttpServletRequest request) throws ParseException {
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
        } else {
            //Exibe mensagem de erro.
            sb.append("Erro ao realizar a atualização!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
    
    
    //  Excluir  ///////////////////////////////////////////////////////////////
    private String excluir (Integer id,HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        
        CompraMaterial cot = new CompraMaterial();
        cot.setId(id);
        
        CompraMaterialDAO dao = new CompraMaterialDAO();
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
    //  Excluir  ///////////////////////////////////////////////////////////////
//    private String excluir(Integer id, HttpServletRequest request) {
//        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(false);
//
//        Set compras_materiais = (Set<CompraMaterial>) session.getAttribute("compra_materiais");
//        try {
//            int i = 0;
//            for (Iterator iterator2 = compras_materiais.iterator(); iterator2.hasNext();) {
//                CompraMaterial compra_material = (CompraMaterial) iterator2.next();
//                if (i == id) {
//                    compra_material.setMaterial(null);
//                    compra_material.setCompra(null);
//                }
//                i++;
//            }
//            session.setAttribute("compra_materiais", compras_materiais);
//            sb.append("1");
//        } catch (Exception e) {
//            sb.append("0");
//        }
//        return sb.toString();
//    }
}
