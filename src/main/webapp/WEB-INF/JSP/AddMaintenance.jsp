<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="be.petrucci.javabeansviews.ListMachineWorker, java.util.List, be.petrucci.javabeans.Machine, be.petrucci.javabeans.MaintenanceWorker" %>
<%
	ListMachineWorker model = (ListMachineWorker)request.getAttribute("model");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Maintenance</title>
</head>
<body>

	<% 
	    List<String> errors = (List<String>) request.getAttribute("fail");
	    if (errors != null && !errors.isEmpty()) {
	        for (String error : errors) {
	%>
	            <p style="color: red;"><%= error %></p>
	<%
	        }
	    }
	%>

	<h1>Create a maintenance</h1>
	
	<form action="/FabricToutFactoryClient2024/AddMaintenanceServlet" method="post">
	    <label for="date">Date :</label>
	    <input type="date" name="date" id="date"><br>
	
	    <label for="duration">Duration :</label>
	    <input type="number" name="duration" id="duration"><br>
	
	    <label for="instruction">Instruction :</label>
	    <textarea name="instruction" id="instruction"></textarea><br>
	    
	    <input type="submit" value="Ajouter Maintenance">
	</form>
	

</body>
</html>