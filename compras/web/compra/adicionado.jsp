
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="dominio.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Categoria</title>
</head>
<body>
	
<%Compra f = (Compra)request.getAttribute("func");%>
<script>location.href='ServletCompra?cmd=EDITAR&id=<%=f.getId()%>'</script>

<h1>

A compra <%=f.getId()%> foi salva com sucesso.
</h1>

<a href='compra/index.jsp'>Voltar</a>
</body>
</html>