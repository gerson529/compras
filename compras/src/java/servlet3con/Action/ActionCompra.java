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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet3con.ServletController.EncaminharDados;
import dominio.Compra;
import dominio.CompraMaterial;
import dominio.Cotacao;
import dominio.CotacaoMaterial;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class ActionCompra extends Action implements EncaminharDados {

    public void executa(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=ISO-8859-9");
        PrintWriter out = response.getWriter();

        try {
            String metodo = request.getParameter("metodo");
            out.println(this.getHeader());
            if (metodo.equals("cadastrar")) {
                out.println(cadastrar(request));
            } else if (metodo.equals("preencherForm")) {
                out.println(preencherForm(new Integer(request.getParameter("id_compra")), request));
            } else if (metodo.equals("atualizar")) {
                out.println(atualizar(request));
            } else if (metodo.equals("excluir")) {
                out.println(excluir(new Integer(request.getParameter("id_compra"))));
            } else if (metodo.equals("finalizarForm")) {
                out.println(finalizarForm(new Integer(request.getParameter("id_compra")), request));
            } else if (metodo.equals("finalizar")) {
                out.println(finalizar( request));
            }
        } finally {
            out.println(this.getFooter());
            out.close();
        }
    }

    //  Cadastrar  /////////////////////////////////////////////////////////////
    private String cadastrar(HttpServletRequest request) throws ParseException {
        StringBuilder sb = new StringBuilder();

        //Pegando os parametros do request
        String nome = request.getParameter("nome");
//        String email = request.getParameter("email");
//        Long telefone = Long.parseLong(request.getParameter("telefone"));
//        String dataEmTexto = request.getParameter("data");

        //Monta um objeto contato
        Compra compra = new Compra();
        compra.setStatus(new Short("1"));
        //Compra.setDatanascimento(new java.sql.Date( new java.text.SimpleDateFormat("dd/mm/yyyy").parse(dataEmTexto).getTime()));

        //Grave nessa conexão!!!
        CompraDAO dao = new CompraDAO();
        if (dao.salvar(compra)) {
            //Imprime messagem comfirmando o cadastro
            //sb.append("<p class='lead'>O cadastro foi concluido!</h1></br>");
            //sb.append("Compra ").append(compra.getNome()).append(" adicionado com sucesso<br>");
            //sb.append("Pedido de Compra ").append(" adicionado com sucesso<br>");
            //sb.append("<br/><br/><a href='../compra/index.jsp'>Voltar para Compras</a>");
            sb.append("<script>location.href='servletController?encaminhar=ActionCompra&metodo=preencherForm&id_compra=" + compra.getId() + "'</script>");
        } else {
            //Exibe mensagem de erro.
            sb.append("Erro ao realizar cadastro!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }

    //  Preecheer formulário  //////////////////////////////////////////////////
    private String preencherForm(Integer id, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(true);
        //HttpSession session = request.getSession(true);

        MaterialDAO daom = new MaterialDAO();
        List materiais = daom.consultar(null);
        String options = "";
        for (Iterator iteratorM = materiais.iterator(); iteratorM.hasNext();) {
            Material material = (Material) iteratorM.next();
            options = options + "\n\t\t<option value='" + material.getId() + "'>" + material.getNome() + "</option>";
        }

        String options_fornecedores = "\n\t\t<option value='1'>Fornecedor 1</option>\n\t\t<option value='2'>Fornecedor 2</option>\n\t\t<option value='3'>Forencedor 3</option>";

        //Pesquisando Compra para preecher o form
        CompraDAO dao = new CompraDAO();
        Compra compra = dao.consultarPorId(id);
        Set<CompraMaterial> compras_materiais = compra.getCompraMaterials();
        Set<Cotacao> compra_cotacoes = compra.getCotacaos();
        //session.setAttribute("compra_cotacoes", compra_cotacoes );

        //Salva os materias na session
        //Convertendo data SQL para String
        String dataString = "";
        if (compra.getDataPrazo() != null) {
            dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(compra.getDataPrazo());
        }
        sb.append("<script type=\"text/javascript\" src=\"../js/jmask.js\"></script>");
        sb.append("<script type=\"text/javascript\" src=\"../js/compras_form.js\"></script>");
        sb.append("<p class=\"lead\">\n"
                + "    <a href=\"../index.jsp\">Home</a> :: <a href=\"index.jsp\">Compras</a> :: Cadastro\n"
                + "</p>");

        sb.append("\n<form role=\"form\" id='frmCompra'  method='post' action='servletController'>" //'\n' e '\t' para conferir de um CTRL+U na pagina do formulário de atualização.
                + "\n\t<input type='hidden' name='encaminhar' value='ActionCompra'/>"
                + "\n\t<input type='hidden' name='metodo' value='atualizar'/>"
                + "\n\tCod. Compra: <input class=\"form-control\" type='text' id='id_compra' name='id_compra' value='" + compra.getId() + "' readonly='readonly' /> <input type='submit' value='s' style='width:1px;height:1px;float:right;border:#fff;'><br/>"
                //+ "\n\tNome: <input type='text' name='nome' value='"+compra.getNome()+"' /> <br/>"
                + "\n\tPrazo de Compra: <input class=\"form-control\" type='text' id='compra_data' name='data' value='" + dataString + "' /><br/>"
                + "</form>");
        sb.append("<div class='row'>");
        sb.append("<div id='materiais'  class='col-md-6'><h4>Itens/Materiais</h4>"
                + "<form class='form-inline' id='frmCompraMaterial' method='post' action='servletController'>"
                + "\n\t<input  type='hidden' name='encaminhar' value='ActionCompraMaterial'/>"
                + "\n\t<input  type='hidden' name='metodo' value='adicionar'/>"
                + "\n\t<select class=\"form-control\" name='material_id'>"
                + "\n\t\t<option value=\"\">[ Selecione um Material ]</option>"
                + options
                + "\n\t</select>"
                + "<input class=\"form-control\" type='text' name='quantidade'>"
                + "<input class='btn btn-info' type='submit' value='Inserir'>");
        sb.append("</form>");
        sb.append("<table class='table table=bordered table-striped'>");
        try {
            //session.setAttribute("compra_materiais", compras_materiais);
            int i = 0;
            for (Iterator iterator2 = compras_materiais.iterator(); iterator2.hasNext();) {
                CompraMaterial compra_material = (CompraMaterial) iterator2.next();

                sb.append("<tr class='" + compra_material.getId() + "'>");
                sb.append("<td><a rel='" + compra_material.getId() + "' href='#' class='deleteCompraMaterial'><i class='glyphicon glyphicon-trash'></i></a></td>");
                sb.append("<td>" + compra_material.getMaterial().getNome() + "</td>");
                sb.append("<td class='"+compra_material.getMaterial().getId()+"quantidade'>" + compra_material.getQuantidade() + "</td>");
                sb.append("</tr>");
                i++;
            }
        } catch (Exception e) {
        }
        sb.append("</table></div>");
        sb.append("<div id='cotacoes' class='col-md-6 well'><h4>Cotações</h4>"
                + "<form class='form-inline' id='frmCotacao' method='post' action='servletController'>"
                + "\n\t<input type='hidden' name='encaminhar' value='ActionCotacao'/>"
                + "\n\t<input type='hidden' name='metodo' value='adicionar'/>"
                + "\n\t<select class=\"form-control\" name='fornecedor_id'>"
                + "\n\t\t<option value=\"\">[ Selecione um Fornecedor ]</option>"
                + options_fornecedores
                + "\n\t</select>"
                + "<input class='btn btn-success' type='submit' value='Inserir'>");
        sb.append("</form>");
        sb.append("<table class='table table-bordered table-striped'>");
        try {
            //session.setAttribute("compra_cotacoes", compra_cotacoes);
            int i = 0;
            Float total;
            for (Iterator iterator2 = compra_cotacoes.iterator(); iterator2.hasNext();) {
                 Cotacao cotacao = (Cotacao) iterator2.next();
                total = 0f;
                String hiddens = "";
                for (Iterator tota = cotacao.getCotacaoMaterials().iterator(); tota.hasNext();) {
                    CotacaoMaterial cotacao_material = (CotacaoMaterial) tota.next();
                    hiddens+="<input type='hidden' rel='"+cotacao_material.getMaterial().getId()+"' class='hidden_cotmat' value='"+cotacao_material.getValorUnitario()+"'>";
                }
                sb.append("<tr class='" + cotacao.getId() + "'>");
                sb.append("<td><a rel='" + cotacao.getId() + "' href='#' class='deleteCotacao'><i class='glyphicon glyphicon-trash'></i></a></td>");
                sb.append("<td><a rel='" + cotacao.getId() + "' href='#' class='editCotacao'>" + cotacao.getFornecedorId() + "</a></td>");
                sb.append("<td><span class='" + cotacao.getId() + "total'>"+total+"</span>"+hiddens+"</td>");
                sb.append("</tr>");
                i++;
            }
        } catch (Exception e) {
        }
        sb.append("</table></div>");
        sb.append("<div class='row'>");
        sb.append("\n\t<input class='btn btn-large btn-block btn-primary' type='button' id='frmSalvar' value='Atualizar'/>"
                + "\n</form>");
        sb.append("\n<br/>\n<a class='btn btn-default' href='../compra/index.jsp'>Voltar</a>"
                + "<div class=\"modal fade\" id=\"modalCotacao\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalCotacaoLabel\" aria-hidden=\"true\">\n" +
                    "  <div class=\"modal-dialog\">\n" +
                    "    <div class=\"modal-content\">\n" +
                    "      <div class=\"modal-header\">\n" +
                    "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n" +
                    "        <h4 class=\"modal-title\" id=\"modalCotacaoLabel\">Modal title</h4>\n" +
                    "      </div>\n" +
                    "      <div class=\"modal-body\">\n" +
                    "        ...\n" +
                    "      </div>\n" +
                    "      <div class=\"modal-footer\">\n" +
                    "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
                   
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>");
        return sb.toString();
    }

    //  Atualizar  /////////////////////////////////////////////////////////////
    private String atualizar(HttpServletRequest request) throws ParseException {
        StringBuilder sb = new StringBuilder();
        //HttpSession session = request.getSession(false);

        //Pegando os parametros do request
        Integer id = Integer.parseInt(request.getParameter("id_compra"));
        //String nome = request.getParameter("nome");
        String dataEmTexto = request.getParameter("data");
        //Monta um objeto contato

        Compra compra = new Compra();
        compra.setId(id);
        compra.setStatus(new Short("1"));
        //compra.setNome(nome);

        compra.setDataPrazo(new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto).getTime()));
        //sb.append("Id: " + compra.getId() );
        //sb.append("Data: " + compra.getDataPrazo() + request.getParameter("data"));

//        Set tmp_materiais = (Set<CompraMaterial>) session.getAttribute("compra_materiais");
//        try {
//            for (Iterator iterator2 = tmp_materiais.iterator(); iterator2.hasNext();) {
//                CompraMaterial compra_material = (CompraMaterial) iterator2.next();
//                if (compra_material.getMaterial() != null) {
//                    compra_material.setCompra(compra);
//                }
//            }
//            compra.setCompraMaterials(tmp_materiais);
//        } catch (Exception e) {
//            e.getCause();
//        }
//        Set tmp_cotacoes = (Set<Cotacao>) session.getAttribute("compra_cotacoes");
//        try {
//            for (Iterator iteratorCO = tmp_cotacoes.iterator(); iteratorCO.hasNext();) {
//                Cotacao compra_cotacao = (Cotacao) iteratorCO.next();
//                if (compra_cotacao.getFornecedorId() != null) {
//                    compra_cotacao.setCompra(compra);
//                }
//            }
//            compra.setCotacaos(tmp_cotacoes);
//        } catch (Exception e) {
//            e.getCause();
//        }

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
    private String excluir(Integer id) {
        StringBuilder sb = new StringBuilder();

        //Busca o Compra e monta vo
        CompraDAO dao = new CompraDAO();
        Compra compra = dao.consultarPorId(id);
        
        CompraDAO dao2 = new CompraDAO();
        
        //Envia vo do Compra para o método exclir
        if (dao2.excluir(compra)) {
            //Imprime messagem comfirmando exclusão
            sb.append("<h1>O Pedido de Compra foi deletado!</h1><br/>");
            sb.append("Pedido de Compra deletado com sucesso</br>");
            sb.append("<br/><br/><a href='../compra/index.jsp'>Voltar para Compras</a>");
        } else {
            //Exibe mensagem de erro.
            sb.append("Erro ao deletar Compra!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
    
    
    private String finalizarForm(Integer id, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
//        HttpSession session = request.getSession(true);
        //HttpSession session = request.getSession(true);

        MaterialDAO daom = new MaterialDAO();
        List materiais = daom.consultar(null);
        String options = "";
        for (Iterator iteratorM = materiais.iterator(); iteratorM.hasNext();) {
            Material material = (Material) iteratorM.next();
            options = options + "\n\t\t<option value='" + material.getId() + "'>" + material.getNome() + "</option>";
        }

        String options_fornecedores = "\n\t\t<option value='1'>Fornecedor 1</option>\n\t\t<option value='2'>Fornecedor 2</option>\n\t\t<option value='3'>Forencedor 3</option>";

        //Pesquisando Compra para preecher o form
        CompraDAO dao = new CompraDAO();
        Compra compra = dao.consultarPorId(id);
        Set<CompraMaterial> compras_materiais = compra.getCompraMaterials();
        Set<Cotacao> compra_cotacoes = compra.getCotacaos();
        //session.setAttribute("compra_cotacoes", compra_cotacoes );

        //Salva os materias na session
        //Convertendo data SQL para String
        String dataString = "";
        if (compra.getDataPrazo() != null) {
            dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(compra.getDataPrazo());
        }
        //sb.append("<script type=\"text/javascript\" src=\"../js/jmask.js\"></script>");
        //sb.append("<script type=\"text/javascript\" src=\"../js/compras_form.js\"></script>");
        sb.append("<p class=\"lead\">\n"
                + "    <a href=\"../index.jsp\">Home</a> :: <a href=\"index.jsp\">Compras</a> :: Cadastro\n"
                + "</p>");

        sb.append("\n<form role=\"form\" id='frmCompra'  method='post' action='servletController'>" //'\n' e '\t' para conferir de um CTRL+U na pagina do formulário de atualização.
                + "\n\t<input type='hidden' name='encaminhar' value='ActionCompra'/>"
                + "\n\t<input type='hidden' name='metodo' value='finalizar'/>"
                + "\n\tCod. Compra: <input class=\"form-control span2\" type='text' id='id_compra' name='id_compra' value='" + compra.getId() + "' readonly='readonly' />"
                //+ "\n\tNome: <input type='text' name='nome' value='"+compra.getNome()+"' /> <br/>"
                + "\n\tPrazo de Compra: <input class=\"form-control\" type='text' id='compra_data' name='data' value='" + dataString + "' readonly='readonly' /><br/>"
                );
        sb.append("<div class='row'>");
        sb.append("<div id='materiais'  class='col-md-6'><h4>Itens/Materiais</h4>");
               
        sb.append("<table class='table table-bordered table-striped'>");
        try {
            //session.setAttribute("compra_materiais", compras_materiais);
            int i = 0;
            for (Iterator iterator2 = compras_materiais.iterator(); iterator2.hasNext();) {
                CompraMaterial compra_material = (CompraMaterial) iterator2.next();

                sb.append("<tr class='" + compra_material.getId() + "'>");
                //sb.append("<td><a rel='" + compra_material.getId() + "' href='#' class='deleteCompraMaterial'><i class='glyphicon glyphicon-trash'></i></a></td>");
                sb.append("<td>" + compra_material.getMaterial().getNome() + "</td>");
                sb.append("<td class='"+compra_material.getMaterial().getId()+"quantidade'>" + compra_material.getQuantidade() + "</td>");
                sb.append("</tr>");
                i++;
            }
        } catch (Exception e) {
        }
        sb.append("</table></div>");
        
        sb.append("<div id='cotacoes' class='col-md-6 well'><h4>Cotações</h4>");
        sb.append("<small>Selecione a cotação desejada:</small>");
                
        sb.append("<table class='table table-bordered table-striped'>");
        try {
            //session.setAttribute("compra_cotacoes", compra_cotacoes);
            int i = 0;
            Float total;
            for (Iterator iterator2 = compra_cotacoes.iterator(); iterator2.hasNext();) {
                 Cotacao cotacao = (Cotacao) iterator2.next();
                total = 0f;
                String hiddens = "";
                
                sb.append("<tr class='" + cotacao.getId() + "'>");
                //sb.append("<td><a rel='" + cotacao.getId() + "' href='#' class='deleteCotacao'><i class='glyphicon glyphicon-trash'></i></a></td>");
                sb.append("<td><input required='required' type='radio' name='cotacao' value='" + cotacao.getId() + "' />");
                sb.append("<td><a rel='" + cotacao.getId() + "' href='#' class='editCotacao'>" + cotacao.getFornecedorId() + "</a></td>");
                sb.append("<td><span class='" + cotacao.getId() + "total'>"+total+"</span>"+hiddens+"</td>");
                sb.append("<td>");
                sb.append("<table>");
                for (Iterator tota = cotacao.getCotacaoMaterials().iterator(); tota.hasNext();) {
                    CotacaoMaterial cotacao_material = (CotacaoMaterial) tota.next();
                    sb.append("<tr>");
                    sb.append("<td>");
                    sb.append(cotacao_material.getMaterial().getNome()+" "+cotacao_material.getValorUnitario());
                    sb.append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
                sb.append("</td>");
                sb.append("</tr>");
                i++;
            }
        } catch (Exception e) {
        }
        sb.append("</table>"
                + "\n\tNota Fiscal: <input class=\"form-control\" required='required' type='text' id='nota_fiscal' name='nota_fiscal' value='" + "' /><br/>"
                + "</div>");
        sb.append("<div class='row'>");
        sb.append("\n\t<input class='btn btn-large btn-block btn-primary' type='submit' id='frmSalvar' value='FINALIZAR'/>"
                + "\n</form>");
        sb.append("\n<br/>\n<a class='btn btn-default' href='../compra/index.jsp'>Voltar</a>"
                + "<div class=\"modal fade\" id=\"modalCotacao\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalCotacaoLabel\" aria-hidden=\"true\">\n" +
                    "  <div class=\"modal-dialog\">\n" +
                    "    <div class=\"modal-content\">\n" +
                    "      <div class=\"modal-header\">\n" +
                    "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n" +
                    "        <h4 class=\"modal-title\" id=\"modalCotacaoLabel\">Modal title</h4>\n" +
                    "      </div>\n" +
                    "      <div class=\"modal-body\">\n" +
                    "        ...\n" +
                    "      </div>\n" +
                    "      <div class=\"modal-footer\">\n" +
                    "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
                   
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>");
        return sb.toString();
    }
    
    //  Atualizar  /////////////////////////////////////////////////////////////
    private String finalizar(HttpServletRequest request) throws ParseException {
        StringBuilder sb = new StringBuilder();
        //HttpSession session = request.getSession(false);
        CompraDAO dao = new CompraDAO();
        //Pegando os parametros do request
        Integer id = Integer.parseInt(request.getParameter("id_compra"));
        Integer cotacao_id = Integer.parseInt(request.getParameter("cotacao"));
        //String nome = request.getParameter("nome");
        //String dataEmTexto = request.getParameter("data");
        //Monta um objeto contato

        Compra compra = dao.consultarPorId(id);
        compra.setStatus(new Short("2"));
        compra.setNotaFiscal(request.getParameter("nota_fiscal"));
        //compra.setNome(nome);

        //compra.setDataPrazo(new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto).getTime()));
        //sb.append("Id: " + compra.getId() );
        //sb.append("Data: " + compra.getDataPrazo() + request.getParameter("data"));
        Cotacao tmp_cot=new Cotacao();
        for (Iterator iterator2 = compra.getCotacaos().iterator(); iterator2.hasNext();) {
                 Cotacao cotacao = (Cotacao) iterator2.next();
                 if(cotacao.getId()==cotacao_id){
                     cotacao.setSelecionado(true);
                     tmp_cot=cotacao;
                     break;
                 }
        }
        //Cotacao cot = new Cotacao(Integer.parseInt(request.getParameter("cotacao")));
        //cot.setSelecionado(true);
        //Set tmp_cot = (Set<Cotacao>) new HashSet(1);
        //tmp_cot.add(cot);
        //compra.setCotacaos(tmp_cot);
        
        

        if (dao.alterar(compra)) {
            CotacaoDAO daoc = new CotacaoDAO();
            if(daoc.alterar(tmp_cot)){            
//Imprime messagem comfirmando atualização
            
            sb.append("<h1>Compra finalizada com sucesso!</h1><br/>");
            sb.append("Pedido de Compra atualizado com sucesso<br/>");
            sb.append("<br/><br/><a href='../compra/index.jsp'>Voltar para Compras</a>");
            }else{
                //Exibe mensagem de erro.
                sb.append("Erro ao realizar a atualização!<br/>");
                sb.append("Tente novamente");
            }
        } else {
            //Exibe mensagem de erro.
            sb.append("Erro ao realizar a atualização!<br/>");
            sb.append("Tente novamente");
        }
        return sb.toString();
    }
}
