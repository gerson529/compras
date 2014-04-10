<%-- 
    Document   : index
    Created on : 09/02/2014, 14:21:19
    Author     : Gerson
--%>

<%@page import= "dominio.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "dao.impl.CategoriaDAO"%>
<%@page import = "dao.impl.MaterialDAO"%>
<%@page import = "dominio.Material"%>
<%@page import = "java.util.List"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compras :: Material</title>
    </head>
    <body>
        <a href="../index.jsp">Home</a>
        <br/>
        <h1>Material</h1>
         
         
        <form method="post" action="../ServletMaterial">
            <input type="hidden" name="encaminhar" value="ActionMaterial"/>
            <input type="hidden" name="metodo" value="cadastrar"/>
            Nome: <input type="text" name="nome" value="" required="required"/> <br/>
            Cateogira: <select name="categoria_id" required>
                <option value=''>[ Selecione uma Categoria]</option>
            <%
                
                try{
                    
                    
                    CategoriaDAO daoc = new CategoriaDAO();
                    List<Categoria>listac = daoc.consultar(null);
                    for(Categoria Categoria : listac){
                        %>
                         <option value="<%=Categoria.getId()%>"><%=Categoria.getNome()%></option>
                        <%
                    }
                }catch(Exception e){
                    out.print("Erro "+e);
                }
            %>
            </select><br>
            <input type="submit" value="Cadastrar Material"/><br/>
            <input type="reset" value="Limpar campos"/>
        </form>
        <br/>
        <br/>
        <h2>Lista de Materials</h2>
        <table width="100%" border="1">
            <tr>
                <td>Código Material</td>
                <td>Nome</td>
                <td>Categoria</td>
                <td>Atualizar</td>
                <td>Excluir</td>
            </tr>
            <%
                try{
                    String dtString = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    MaterialDAO dao = new MaterialDAO();
                    List<Material>lista = dao.consultar(null);
                    for(Material Material : lista){
            %>
            <tr>
                <td><%= Material.getId() %></td>
                <td><%= Material.getNome() %></td>
                <td>
                <%
                    try{
                        out.print( Material.getCategoria().getNome()); 
                    }catch(Exception e){
                        out.print("");
                    }
                %>
                </td>
                <!--<td><%
                       //try{
                       //    dtString = null;
                       //    dtString = sdf.format(Material.getDatanascimento());
                       //    out.println(dtString);
                       //}catch(NullPointerException e){
                       //    out.print("Value " + e.getMessage());
                       //}
                         %></td>-->
                <!--<td><% out.print("<a href=servletController?encaminhar=ActionMaterial&metodo=preencherForm&id_material="+Material.getId()+" style='text-decoration:none'><input type='button' value='Atualizar'/></a>"); %></td>-->
						 <!--<td><% out.print("<a href=servletController?encaminhar=ActionMaterial&metodo=excluir&id_material="+Material.getId()+" style='text-decoration:none'><input type='button' value='Excluir'/></a>"); %></td>-->
                <td><% out.print("<a href='../ServletMaterial?cmd=EDITAR&id="+Material.getId()+"' style='text-decoration:none'><input type='button' value='Atualizar'/></a>"); %></td>
				<td><% out.print("<a href='../ServletMaterial?cmd=EXCLUIR&id="+Material.getId()+"' style='text-decoration:none'><input type='button' value='Excluir'/></a>"); %></td>
                
                
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