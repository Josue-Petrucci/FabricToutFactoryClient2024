<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="be.petrucci.javabeans.Maintenance" %>
<%@ page import="be.petrucci.javabeans.Maintenance" %>
<%@ page import="be.petrucci.javabeans.MaintenanceManager" %>
<%@ page import="be.petrucci.javabeans.MaintenanceWorker" %>
<%@ page import="be.petrucci.javabeans.Machine" %>
<%@ page import="be.petrucci.javabeans.Zone" %>
<%@ page import="java.util.List" %>

<%
    Maintenance m = (Maintenance) request.getAttribute("maintenance");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp"/>
<title>Detail of maintenance</title>
</head>
<body class="bg-light">
	
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	  <a class="navbar-brand" href="home">FabricTout</a>
	</nav>
	
	<div class="container mt-4">
    <h1 class="text-center mb-4">Maintenance Detail</h1>

    <%
        if (m != null) {
    %>

    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            General Information
        </div>
        <div class="card-body">
            <p><strong>ID:</strong> <%= m.getId() %></p>
            <p><strong>Date:</strong> <%= m.getDate() %></p>
            <p><strong>Duration:</strong> <%= m.getDuration() %> minutes</p>
            <p><strong>Instructions:</strong> <%= m.getInstructions() %></p>
            <p><strong>Report:</strong> <%= (m.getReport() != null ? m.getReport() : "No report available") %></p>
            <p><strong>Status:</strong> 
                <span class="badge badge-<%= m.getStatus().toString().equals("Completed") ? "success" : "warning" %>">
                    <%= m.getStatus() %>
                </span>
            </p>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header bg-info text-white">
            Manager Information
        </div>
        <div class="card-body">
            <%
                MaintenanceManager manager = m.getManager();
                if (manager != null) {
            %>
            <p><strong>Name:</strong> <%= manager.getFirstname() %> <%= manager.getLastname() %></p>
            <p><strong>Age:</strong> <%= manager.getAge() %></p>
            <p><strong>Address:</strong> <%= manager.getAddress() %></p>
            <p><strong>Matricule:</strong> <%= manager.getMatricule() %></p>
            <%
                } else {
            %>
            <p class="text-muted">No manager assigned.</p>
            <% } %>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header bg-warning text-dark">
            Assigned Workers
        </div>
        <div class="card-body">
            <%
                List<MaintenanceWorker> workers = m.getWorkers();
                if (workers != null && !workers.isEmpty()) {
            %>
            <ul class="list-group">
                <% for (MaintenanceWorker worker : workers) { %>
                <li class="list-group-item">
                    <strong>Name:</strong> <%= worker.getFirstname() %> <%= worker.getLastname() %>,
                    <strong>Age:</strong> <%= worker.getAge() %>,
                    <strong>Address:</strong> <%= worker.getAddress() %>,
                    <strong>Matricule:</strong> <%= worker.getMatricule() %>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <p class="text-muted">No workers assigned.</p>
            <% } %>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header bg-secondary text-white">
            Machine Details
        </div>
        <div class="card-body">
            <%
                Machine machine = m.getMachine();
                if (machine != null) {
            %>
            <p><strong>ID:</strong> <%= machine.getId() %></p>
            <p><strong>Type:</strong> <%= machine.getType() %></p>
            <p><strong>Size:</strong> <%= machine.getSize() %></p>
            <p><strong>Status:</strong> <%= machine.getStatus() %></p>

            <h5 class="mt-3">Zones</h5>
            <%
                List<Zone> zones = machine.getZones();
                if (zones != null && !zones.isEmpty()) {
            %>
            <ul class="list-group">
                <% for (Zone zone : zones) { %>
                <li class="list-group-item">
                    <strong>Zone Letter:</strong> <%= zone.getZoneLetter() %>,
                    <strong>Danger Level:</strong> <%= zone.getDangerLevel() %>
                </li>
                <% } %>
            </ul>
            <% } else { %>
            <p class="text-muted">No zones assigned.</p>
            <% } %>
            <% } else { %>
            <p class="text-muted">No machine assigned.</p>
            <% } %>
        </div>
    </div>

    <%
        } else {
    %>
    <div class="alert alert-danger" role="alert">
        No maintenance details available.
    </div>
    <% } %>

    <div class="text-center mt-4">
    	<a href="SeeAllMaintenance" class="btn btn-primary" style="width: 100%;">Back</a>
	</div>
</div>
</body>
</html>