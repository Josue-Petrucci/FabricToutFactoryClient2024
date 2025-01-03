<%@page import="be.petrucci.javabeans.Maintenance"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="be.petrucci.javabeans.Machine" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Maintenance</title>
</head>
<body>
	<h1>List of all maintenance</h1><br>
	<table border="1" style="border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
            	<th>Machine type</th>
                <th>Duration(Minute)</th>
                <th>Instruction</th>
                <th>Report</th>
                <th>Number of worker</th>
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
            	<td><%= m.getMachine().getType() %></td>
                <td><%= m.getDuration() %></td>
                <td><%= m.getInstructions() %></td>
                <td><%= m.getReport() %></td>
                <td><%= m.getWorkers().size() %></td>
                <td><%= m.getStatus() %></td>
                <td><a href="?id=<%= m.getId() %>">Detail</a></td>
            </tr>
            <% 
                    }
                } else { 
            %>
            <tr>
                <td colspan="2" style="text-align: center;">No Machines available</td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <br>
    <a href="Home"><button>Back</button></a>
</body>
</html>