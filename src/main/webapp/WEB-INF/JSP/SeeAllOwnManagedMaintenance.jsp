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
<title>Maintenance List</title>
</head>
<body>
    <h1>Maintenance List</h1>

    <%
        MaintenanceManager manager = (MaintenanceManager) request.getAttribute("manager");

        if (manager != null) {
            ArrayList<Maintenance> maintenanceList = manager.getMaintenance();
    %>

    <h2>Manager Information</h2>
    <p><strong>Name:</strong> <%= manager.getFirstname() + " " + manager.getLastname() %></p>
    <p><strong>Matricule:</strong> <%= manager.getMatricule() %></p>
    <p><strong>Address:</strong> <%= manager.getAddress() %></p>

    <h2>Associated Maintenances</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Duration (minutes)</th>
                <th>Instructions</th>
                <th>Report</th>
                <th>Status</th>
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
                            <td><%= maintenance.getStatus() %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="6">No associated maintenance found</td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <%
        } else {
    %>
        <h3>No manager found.</h3>
    <%
        }
    %>
</body>
</html>
