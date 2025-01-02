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
	    List<String> errors = (List<String>) request.getAttribute("errors");
	    if (errors != null && !errors.isEmpty()) {
	        for (String error : errors) {
	%>
	            <p style="color: red;"><%= error %></p>
	<%
	        }
	    }
	%>

	<h1>Create a maintenance</h1>
	
	<form action="addmaintenance" method="post">
    <label for="machine">Machine :</label>
    <select name="machineId" id="machine">
        <% for (Machine m : model.getListMachine()) { %>
            <option value="<%= m.getId() %>"><%= m.getId() + ": " + m.getType() %></option>
        <% } %>
    </select><br>
    
    <label for="workers">Workers :</label>
    <select name="workerIds" id="workers" multiple>
        <% for (MaintenanceWorker w : model.getListWorker()) { %>
            <option value="<%= w.getId() %>"><%= w.getLastname() + " " + w.getFirstname() %></option>
        <% } %>
    </select><br>

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