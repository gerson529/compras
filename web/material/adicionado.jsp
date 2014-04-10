
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="dominio.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Material</title>
</head>
<body>
<%Material f = (Material)request.getAttribute("func");%>


<h1>

O material <%=f.getNome() %> foi salvo com sucesso.
</h1>

<a href='material/index.jsp'>Voltar</a>
</body>
</html>