<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="be.petrucci.javabeans.Machine"%>
<%@ page import="be.petrucci.javabeans.MaintenanceWorker"%>
<%@ page import="be.petrucci.javabeans.MaintenanceManager"%>

<%
MaintenanceManager manager = (MaintenanceManager) request.getAttribute("manager");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp" />
<title>Choose the machine and the worker for the maintenance</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary w-100">
		<div class="container-fluid">
			<a class="navbar-brand" href="home">FabricTout</a>
		</div>
	</nav>

	<div class="container mt-4">

		<%
		if (request.getAttribute("fail") != null) {
		%>
		<div class="alert alert-danger text-center">
			<%=request.getAttribute("fail")%>
		</div>
		<%
		}
		%>

		<h1 class="text-center mb-4">Choose the Machine and Workers for
			Maintenance</h1>

		<form
			action="/FabricToutFactoryClient2024/AddMachineWorkersToMaintenanceServlet"
			method="post">

			<h2 class="mt-4">Machine</h2>
			<div class="table-responsive">
				<table
					class="table table-bordered table-striped table-hover text-center">
					<thead class="thead-dark">
						<tr>
							<th>Select</th>
							<th>Machine ID</th>
							<th>Type</th>
							<th>Size</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<%
						ArrayList<Machine> machines = (ArrayList<Machine>) request.getAttribute("Machines");
						if (machines != null && !machines.isEmpty()) {
							for (Machine m : machines) {
								if (m.getSite().getId() == manager.getSite().getId()) {
						%>
						<tr>
							<td><input type="radio" name="selectedMachineId"
								value="<%=m.getId()%>"></td>
							<td><%=m.getId()%></td>
							<td><%=m.getType()%></td>
							<td><%=m.getSize()%></td>
							<td><%=m.getStatus()%></td>
						</tr>
						<%
						}
						}
						} else {
						%>
						<tr>
							<td colspan="5" class="text-center">No machines available</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>

			<h2 class="mt-4">Workers</h2>
			<div class="table-responsive">
				<table
					class="table table-bordered table-striped table-hover text-center">
					<thead class="thead-dark">
						<tr>
							<th>Select</th>
							<th>Worker ID</th>
							<th>Name</th>
							<th>Matricule</th>
						</tr>
					</thead>
					<tbody>
						<%
						ArrayList<MaintenanceWorker> workers = (ArrayList<MaintenanceWorker>) request.getAttribute("Workers");
						if (workers != null && !workers.isEmpty()) {
							for (MaintenanceWorker w : workers) {
								if (w.getSite().getId() == manager.getSite().getId()) {
						%>
						<tr>
							<td><input type="checkbox" name="selectedWorkerIds"
								value="<%=w.getId()%>"></td>
							<td><%=w.getId()%></td>
							<td><%=w.getLastname() + " " + w.getFirstname()%></td>
							<td><%=w.getMatricule()%></td>
						</tr>
						<%
						}
						}
						} else {
						%>
						<tr>
							<td colspan="4" class="text-center">No workers available</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>

			<div class="d-flex justify-content-center mt-4">
				<input type="submit" class="btn btn-primary btn-lg w-100 mx-2"
					name="submit" id="submit" value="Next"> <a href="home"
					class="btn btn-secondary btn-lg w-100 mx-2">Cancel</a>
			</div>
		</form>
	</div>
</body>
</html>