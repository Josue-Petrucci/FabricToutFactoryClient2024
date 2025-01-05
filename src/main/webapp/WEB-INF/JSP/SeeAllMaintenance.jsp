<%@page import="be.petrucci.javabeans.Maintenance"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="be.petrucci.javabeans.Machine"%>
<%@ page import="be.petrucci.javabeans.MaintenanceStatus"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp" />
<title>All Maintenance</title>
</head>
<body class="bg-light">

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<a class="navbar-brand" href="home">FabricTout</a>
	</nav>

	<div class="container mt-5">
		<div class="text-center mb-4">
			<h1 class="text-primary">List of All Maintenance</h1>
		</div>

		<div class="card shadow-sm">
			<div class="card-header bg-success text-white">
				<h5 class="mb-0">Maintenance Details</h5>
			</div>
			<div class="card-body">
				<table class="table table-striped table-hover">
					<thead class="thead-dark">
						<tr>
							<th>Machine Type</th>
							<th>Duration (Minutes)</th>
							<th>Instruction</th>
							<th>Report</th>
							<th>Number of Workers</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<%
						ArrayList<Maintenance> maintenances = (ArrayList<Maintenance>) request.getAttribute("Maintenances");
						if (maintenances != null && !maintenances.isEmpty()) {
							for (Maintenance m : maintenances) {
						%>
						<tr>
							<td><%=m.getMachine().getType()%></td>
							<td><%=m.getDuration()%></td>
							<td><%=m.getInstructions()%></td>
							<td><%=m.getReport() != null ? m.getReport() : "N/A"%></td>
							<td><%=m.getWorkers().size()%></td>
							<td><span
								class="badge badge-<%=m.getStatus() == MaintenanceStatus.Completed ? "success" : "warning"%>">
									<%=m.getStatus()%>
							</span></td>
							<td><a href="DetailMaintenanceServlet?id=<%=m.getId()%>"
								class="btn btn-sm btn-primary"> Details </a></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="7" class="text-center text-muted">No
								Maintenance Available</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>