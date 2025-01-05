<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="be.petrucci.javabeansviews.ListMachineWorker, java.util.List, be.petrucci.javabeans.Machine, be.petrucci.javabeans.MaintenanceWorker"%>
<%
ListMachineWorker model = (ListMachineWorker) request.getAttribute("model");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp" />
<title>Add Maintenance</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary w-100">
		<div class="container-fluid">
			<a class="navbar-brand" href="home">FabricTout</a>
		</div>
	</nav>

	<div class="container mt-4">

		<%
		List<String> errors = (List<String>) request.getAttribute("fail");
		if (errors != null && !errors.isEmpty()) {
		%>
		<div class="alert alert-danger">
			<ul>
				<%
				for (String error : errors) {
				%>
				<li><%=error%></li>
				<%
				}
				%>
			</ul>
		</div>
		<%
		}
		%>

		<h1 class="text-center mb-4">Create a Maintenance</h1>

		<form action="/FabricToutFactoryClient2024/AddMaintenanceServlet"
			method="post" class="p-4 border rounded shadow-sm bg-light">

			<div class="form-group">
				<label for="date" class="font-weight-bold">Date:</label> <input
					type="date" name="date" id="date" class="form-control" required>
			</div>

			<div class="form-group">
				<label for="duration" class="font-weight-bold">Duration
					(minutes):</label> <input type="number" name="duration" id="duration"
					class="form-control" min="1" required>
			</div>

			<div class="form-group">
				<label for="instruction" class="font-weight-bold">Instructions:</label>
				<textarea name="instruction" id="instruction" rows="4"
					class="form-control" required></textarea>
			</div>

			<div class="d-flex justify-content-center mt-4">
				<input type="submit" class="btn btn-primary btn-lg w-25 mx-2"
					value="Add Maintenance"> <a href="home"
					class="btn btn-secondary btn-lg w-25 mx-2">Cancel</a>
			</div>
		</form>
	</div>
</body>
</html>