
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="dominio.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<% Categoria categoria = (Categoria) request.getAttribute("categoria");%>
		


	</h2>
	<h4>Formulário para atualização de dados<br/>
        nDeve-se preencher todos os campos.</h4>  
	<form method='post' action='ServletCategoria'> 
		<!--<input type='hidden' name='encaminhar' value='ActionCategoria'/>-->
		<input type='hidden' name='cmd' value='ATUALIZAR'/>
		Cod. Categoria: <input type='text' name='categoria_id' value='<%=categoria.getId()%>' readonly='readonly' /> <br/>
		Nome: <input type='text' name='nome' value='<%=categoria.getNome()%>' /> <br/>
		

		<input type='submit' value='Atualizar'/>
	</form>
	<br/><a href='../categoria/index.jsp'>Cancelar</a>
</body>
</html>