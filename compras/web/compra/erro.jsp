
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% String f = (String)request.getAttribute("msg");%>


<h1>
    N�o foi possivel cadastrar a compra!</h1>
<br>
<h2>
<%=f%>
</h2>
<br/><a href='compra/index.jsp'>Voltar</a>
</body>
</html>