<%-- 
    Document   : index
    Created on : 09/02/2014, 14:21:19
    Author     : Gerson
--%>

<%@page import="servlet3con.Action.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "dao.impl.CompraDAO"%>
<%@page import = "dominio.Compra"%>
<%@page import = "java.util.List"%>
<%
    Action a = new Action();
    out.print(a.getHeader());

%>

<p class="lead">
    <a href="../index.jsp">Home</a> :: <a href="./index.jsp">Compras</a>
</p>
<form method="post" action="../ServletCompra">
    <input type="hidden" name="encaminhar" value="ActionCompra"/>
    <input type="hidden" name="metodo" value="cadastrar"/>
    <input type="hidden" name="cmd" value="GRAVAR"/>
    <input type="submit" class="btn btn-primary" value="Abrir Pedido de Compra"/><br/>
</form>
<br/>
<br/>
<h2>Lista de Compras</h2>
<table class="table" width="100%" border="1">
    <tr>
        <th>Código Compra</th>
        <th>Prazo de Compra</th>
        <th>Status</th>
        <th>&nbsp;</th>
    </tr>
    <%                try {
            String dtString = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            CompraDAO dao = new CompraDAO();
            List<Compra> lista = dao.consultar(null);
            for (Compra compra : lista) {
    %>
    <tr>
        <td><%= compra.getId()%></td>
        <td><%
            try {
                dtString = null;
                dtString = sdf.format(compra.getDataPrazo());
                out.println(dtString);
            } catch (NullPointerException e) {
                out.print("Value " + e.getMessage());
            }
            %>
        </td>
        <td><%= (compra.getStatus()==1? "Pendente":"Finalizado")%></td>

        <td>
            
                <% if(compra.getStatus()==1)out.print("<a class='btn btn-success' href='../ServletCompra?cmd=FINALIZAR&id=" + compra.getId() + "' style='text-decoration:none'>"
                        + "Finalizar</a>"); %>
                
                <% if(compra.getStatus()==1)out.print("<a class='btn btn-primary' href='../ServletCompra?cmd=EDITAR&id=" + compra.getId() + "' style='text-decoration:none'>"
                        + "Atualizar</a>"); %>
                <% if(compra.getStatus()==1)out.print("<a class='btn btn-default' href='../ServletCompra?cmd=EXCLUIR&id=" + compra.getId() + "' style='text-decoration:none'>"
                        + "Excluir</a>"); %>
               
            
        </td>

    </tr>
    <%
            }
        } catch (Exception e) {
            out.print("Erro " + e);
        }
    %>
</table>
<%
    out.print(a.getFooter());

%>