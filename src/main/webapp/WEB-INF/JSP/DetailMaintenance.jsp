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
<meta charset="ISO-8859-1">
<title>Detail of maintenance</title>
</head>
<body>
	<h1>Maintenance detail</h1>
	
	<% if (m != null) { %>
        <h2>General Information</h2>
        <p><strong>ID:</strong> <%= m.getId() %></p>
        <p><strong>Date:</strong> <%= m.getDate() %></p>
        <p><strong>Duration:</strong> <%= m.getDuration() %> minutes</p>
        <p><strong>Instructions:</strong> <%= m.getInstructions() %></p>
        <p><strong>Report:</strong> <%= (m.getReport() != null ? m.getReport() : "No report available") %></p>
        <p><strong>Status:</strong> <%= m.getStatus() %></p>

        <h2>Manager</h2>
        <% MaintenanceManager manager = m.getManager(); %>
        <% if (manager != null) { %>
            <p><strong>Name:</strong> <%= manager.getFirstname() %> <%= manager.getLastname() %></p>
            <p><strong>Age:</strong> <%= manager.getAge() %></p>
            <p><strong>Address:</strong> <%= manager.getAddress() %></p>
            <p><strong>Matricule:</strong> <%= manager.getMatricule() %></p>
        <% } else { %>
            <p>No manager assigned.</p>
        <% } %>

        <h2>Workers</h2>
        <% List<MaintenanceWorker> workers = m.getWorkers(); %>
        <% if (workers != null && !workers.isEmpty()) { %>
            <ul>
                <% for (MaintenanceWorker worker : workers) { %>
                    <li>
                        <strong>Name:</strong> <%= worker.getFirstname() %> <%= worker.getLastname() %>,
                        <strong>Age:</strong> <%= worker.getAge() %>,
                        <strong>Address:</strong> <%= worker.getAddress() %>,
                        <strong>Matricule:</strong> <%= worker.getMatricule() %>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <p>No workers assigned.</p>
        <% } %>

        <h2>Machine</h2>
        <% Machine machine = m.getMachine(); %>
        <% if (machine != null) { %>
            <p><strong>ID:</strong> <%= machine.getId() %></p>
            <p><strong>Type:</strong> <%= machine.getType() %></p>
            <p><strong>Size:</strong> <%= machine.getSize() %></p>
            <p><strong>Status:</strong> <%= machine.getStatus() %></p>
            <h3>Zones</h3>
            <% List<Zone> zones = machine.getZones(); %>
            <% if (zones != null && !zones.isEmpty()) { %>
                <ul>
                    <% for (Zone zone : zones) { %>
                        <li>
                            <strong>Zone Letter:</strong> <%= zone.getZoneLetter() %>,
                            <strong>Danger Level:</strong> <%= zone.getDangerLevel() %>
                        </li>
                    <% } %>
                </ul>
            <% } else { %>
                <p>No zones assigned.</p>
            <% } %>
        <% } else { %>
            <p>No machine assigned.</p>
        <% } %>

    <% } else { %>
        <p>No maintenance details available.</p>
    <% } %>
    
    <a href="SeeAllMaintenance"><button>Back</button></a>
    <a href="home"><button>Home</button></a>

</body>
</html>