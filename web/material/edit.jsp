
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="dominio.Categoria"%>
<%@page import="dominio.Material"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<% Material material = (Material) request.getAttribute("material");%>
		<% List<Categoria> categorias = (List) request.getAttribute("categorias");%>
		<%
			String options = "",selected="";
			for (Iterator iteratorM = categorias.iterator(); iteratorM.hasNext();) {
				Categoria cat = (Categoria) iteratorM.next();
				//selected=(material.getCategoria().getId().equals(cat.getId()) )?" selected " : "";
				options = options + "\n\t\t<option value='" + cat.getId() + "' "+selected+" >" + cat.getNome() + "</option>";
			}
		%>


	</h2>
	<h4>Formulário para atualização de dados<br/>
        nDeve-se preencher todos os campos.</h4>  
	<form method='post' action='ServletMaterial'> 
		<!--<input type='hidden' name='encaminhar' value='ActionMaterial'/>-->
		<input type='hidden' name='cmd' value='ATUALIZAR'/>
		Cod. Material: <input type='text' name='material_id' value='<%=material.getId()%>' readonly='readonly' /> <br/>
		Nome: <input type='text' name='nome' value='<%=material.getNome()%>' /> <br/>
		Categoria: <select name='categoria_id' required>
			<option value=''>[ Selecione uma Categoria]</option>
			<%=options%>
		</select><br>

		<input type='submit' value='Atualizar'/>
	</form>
	<br/><a href='material/index.jsp'>Cancelar</a>
</body>
</html>