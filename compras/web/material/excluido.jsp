
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
		<%Material material = (Material) request.getAttribute("material");%>
		<h1>O Material foi deletado!</h1><br/>
		Material deletado com sucesso</br>
		<br/><br/><a href='material/index.jsp'>Voltar</a>

	</body>
</html>