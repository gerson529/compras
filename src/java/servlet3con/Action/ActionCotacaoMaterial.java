/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet3con.Action;

import dao.impl.CompraDAO;
import dao.impl.CotacaoMaterialDAO;
import dao.impl.MaterialDAO;
import java.io.PrintWriter;
import java.text.ParseException;

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
public class ActionCotacaoMaterial extends Action implements EncaminharDados {

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
        
        MaterialDAO dao = new MaterialDAO();
        Material material = dao.consultarPorId(new Integer(request.getParameter("material_id")));
        
        
        Cotacao cotacao = new Cotacao();
        cotacao.setId(new Integer(request.getParameter("cotacao_id")));
        
        
        CotacaoMaterial cot = new CotacaoMaterial(cotacao, material,new Float(request.getParameter("valor_unitario")),null,null);
        
        
        CotacaoMaterialDAO daocor = new CotacaoMaterialDAO();
        if(daocor.salvar(cot)){
            sb.append("<tr class='"+cot.getId()+"'>");
            sb.append("<td><a rel='"+cot.getId()+"' href='#' class='deleteCotacaoMaterial'><i class='glyphicon glyphicon-trash'></i></a></td>");
            sb.append("<td>" + material.getNome() + "</td>");
            sb.append("<td><span class='"+cotacao.getId() + "valor_unitario' rel='"+ material.getId() +"'>" + request.getParameter("valor_unitario") + "</span></td>");
            sb.append("</tr>");
        }else{
            sb.append("");
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
        
        CotacaoMaterial cot = new CotacaoMaterial();
        cot.setId(id);
        
        CotacaoMaterialDAO dao = new CotacaoMaterialDAO();
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
}
