<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="/WEB-INF/JSP/Bootstrap.jsp"/>
</head>
<body>
	<div class="container mt-4">
	    <form action="LogoutServlet" method="GET">
	        <input type="submit" class="btn btn-danger" value="Logout">
	    </form>
	</div>
</body>
</html>