<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="be.petrucci.javabeans.Maintenance"%>

<%
Maintenance m = (Maintenance) request.getAttribute("maintenance");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Complete maintenance</title>
</head>
<body>
	<%
	String error = (String) request.getAttribute("fail");
	if (error != null) {
	%>
	<p style="color: red;"><%=error%></p>
	<%
	}
	%>
	<h1>Please write your report!</h1>
	<div class="container">
		<form action="CompleteMaintenanceServlet?id=<%=m.getId()%>"
			method="POST" class="mt-4">
			<div class="form-group">
				<label for="report">Report:</label>
				<textarea class="form-control" name="report" id="size" required><%=m.getReport() == null ? "" : m.getReport()%></textarea>
			</div>
			<div class="form-group text-center">
				<input type="submit" class="btn btn-primary" name="submit"
					id="submit" value="Submit" />
			</div>
		</form>
	</div>
</body>
</html>