<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="be.petrucci.javabeans.Maintenance" %>
<%@ page import="be.petrucci.javabeans.MaintenanceStatus" %>
<%@ page import="be.petrucci.javabeans.MaintenanceManager" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp"/>
<title>Maintenance List</title>
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="home">FabricTout</a>
</nav>

<div class="container mt-5">
    <div class="text-center mb-4">
        <h1 class="text-primary">Maintenance List</h1>
    </div>

    <%
        MaintenanceManager manager = (MaintenanceManager) request.getAttribute("manager");
        if (manager != null) {
            ArrayList<Maintenance> maintenanceList = manager.getMaintenance();
    %>

    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Manager Information</h5>
        </div>
        <div class="card-body">
            <p><strong>Name:</strong> <%= manager.getFirstname() + " " + manager.getLastname() %></p>
            <p><strong>Matricule:</strong> <%= manager.getMatricule() %></p>
            <p><strong>Address:</strong> <%= manager.getAddress() %></p>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-success text-white">
            <h5 class="mb-0">Associated Maintenances</h5>
        </div>
        <div class="card-body">
            <table class="table table-striped table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>Duration (min)</th>
                        <th>Instructions</th>
                        <th>Report</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (maintenanceList != null && !maintenanceList.isEmpty()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                            for (Maintenance maintenance : maintenanceList) {
                    %>
                                <tr>
                                    <td><%= maintenance.getId() %></td>
                                    <td><%= sdf.format(maintenance.getDate()) %></td>
                                    <td><%= maintenance.getDuration() %></td>
                                    <td><%= maintenance.getInstructions() %></td>
                                    <td><%= maintenance.getReport() != null ? maintenance.getReport() : "N/A" %></td>
                                    <td>
                                        <span class="badge badge-<%= maintenance.getStatus() == MaintenanceStatus.Completed ? "success" : "warning" %>">
                                            <%= maintenance.getStatus() %>
                                        </span>
                                    </td>
                                    <td>
                                        <a href="DeleteMaintenanceServlet?id=<%= maintenance.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this maintenance?');">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                    <%
                            }
                        } else {
                    %>
                            <tr>
                                <td colspan="7" class="text-center text-muted">No associated maintenance found.</td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <%
        } else {
    %>
        <div class="alert alert-danger text-center mt-4" role="alert">
            <h4>No manager found!</h4>
            <p>Please ensure the manager ID is valid or try again later.</p>
        </div>
    <%
        }
    %>
</div>
</body>
</html>
