<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to the Fabric'Tout website</title>
</head>
<body>
	<h1>Welcome to the Fabric'Tout website</h1>
	<%if (request.getAttribute("success") != null) {%>
        <div class="alert alert-success">
       <p><%= request.getAttribute("success")%></p> 
       </div>
    <% } %>
    	<%
        if (request.getAttribute("fail") != null) {%>
        <div class="alert alert-danger">
       <p><%= request.getAttribute("fail")%></p> 
       </div>
    <% } %>
	<% 
        if (session.getAttribute("user") == null) { 
    %>
        <button onclick="window.location='/FabricToutFactoryClient2024/LoginServlet'">Login</button>
    <% } else { %>
    	<%@ include file="LogoutPartialView.jsp" %>
    <% } %>
</body>
</html>