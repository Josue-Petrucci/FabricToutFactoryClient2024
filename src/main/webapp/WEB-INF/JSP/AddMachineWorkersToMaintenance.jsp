<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="be.petrucci.javabeans.Machine" %>
<%@ page import="be.petrucci.javabeans.MaintenanceWorker" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Choose the machine and the worker for the maintenance</title>
</head>
<body>

	<% if (request.getAttribute("fail") != null) {%>
        <div class="alert alert-danger">
       <p><%= request.getAttribute("fail")%></p> 
       </div>
    <% } %>

	<h1>Choose the machine and the worker for the maintenance</h1>
	
	<form action="/FabricToutFactoryClient2024/AddMachineWorkersToMaintenanceServlet" method="post">
	<h2>Machine</h2>
		<table border="1" style="border-collapse: collapse; width: 100%;">
            <thead>
                <tr>
                    <th>Select</th>
                    <th>Zone Details</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    ArrayList<Machine> machines = (ArrayList<Machine>) request.getAttribute("Machines");
                    if (machines != null && !machines.isEmpty()) {
                        for (Machine m : machines) { 
                %>
                <tr>
                    <td><input type="radio" name="selectedMachineId" value="<%= m.getId() %>"></td>
                    <td><%= m.toString() %></td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="2" style="text-align: center;">No machines available</td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <br>
        <h2>Worker</h2>
        <table border="1" style="border-collapse: collapse; width: 100%;">
            <thead>
                <tr>
                    <th>Select</th>
                    <th>Zone Details</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    ArrayList<MaintenanceWorker> workers = (ArrayList<MaintenanceWorker>) request.getAttribute("Workers");
                    if (workers != null && !workers.isEmpty()) {
                        for (MaintenanceWorker w : workers) {
                %>
                <tr>
                    <td><input type="checkbox" name="selectedWorkerIds" value="<%= w.getId() %>"></td>
                    <td><%= w.toString() %></td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="2" style="text-align: center;">No machines available</td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <br>
        <input type="submit" class="btn btn-primary" name="submit" id="submit" value="Next" />
	</form>
</body>
</html>