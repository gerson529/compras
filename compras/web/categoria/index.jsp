<%-- 
    Document   : index
    Created on : 09/02/2014, 14:21:19
    Author     : Gerson
--%>

<%@page import="dominio.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "dao.impl.CategoriaDAO"%>
<%@page import = "java.util.List"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compras :: Categoria</title>
    </head>
    <body>
        <a href="../index.jsp">Home</a>
        <br/>
        <h1>Categoria</h1>
         
         
        <form method="post" action="../ServletCategoria">
            <input type="hidden" name="encaminhar" value="ActionCategoria"/>
            <input type="hidden" name="cmd" value="SALVAR"/>
            Nome: <input type="text" name="nome" value="" required="required"/> <br/>
            
            <input type="submit" value="Cadastrar Categoria"/><br/>
            <input type="reset" value="Limpar campos"/>
        </form>
        <br/>
        <br/>
        <h2>Lista de Categorias</h2>
        <table width="100%" border="1">
            <tr>
                <td>Código Categoria</td>
                <td>Nome</td>
                <td>Atualizar</td>
                <td>Excluir</td>
            </tr>
            <%
                try{
                    String dtString = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    CategoriaDAO dao = new CategoriaDAO();
                    List<Categoria>lista = dao.consultar(null);
                    for(Categoria Categoria : lista){
            %>
            <tr>
                <td><%= Categoria.getId() %></td>
                <td><%= Categoria.getNome() %></td>
                
                <!--<td><%
                       //try{
                       //    dtString = null;
                       //    dtString = sdf.format(Categoria.getDatanascimento());
                       //    out.println(dtString);
                       //}catch(NullPointerException e){
                       //    out.print("Value " + e.getMessage());
                       //}
                         %></td>-->
<!--                <td><% out.print("<a href=servletController?encaminhar=ActionCategoria&metodo=preencherForm&id_categoria="+Categoria.getId()+" style='text-decoration:none'><input type='button' value='Atualizar'/></a>"); %></td>
                <td><% out.print("<a href=servletController?encaminhar=ActionCategoria&metodo=excluir&id_categoria="+Categoria.getId()+" style='text-decoration:none'><input type='button' value='Excluir'/></a>"); %></td>-->
                <td><% out.print("<a href='../ServletCategoria?cmd=EDITAR&id="+Categoria.getId()+"' style='text-decoration:none'><input type='button' value='Atualizar'/></a>"); %></td>
				<td><% out.print("<a href='../ServletCategoria?cmd=EXCLUIR&id="+Categoria.getId()+"' style='text-decoration:none'><input type='button' value='Excluir'/></a>"); %></td>
                
            </tr>
            <%
                    }
                }catch(Exception e){
                    out.print("Erro "+e);
                }
            %>
        </table>
    </body>
</html>