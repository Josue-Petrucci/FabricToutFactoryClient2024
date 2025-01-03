<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="be.petrucci.javabeans.Machine" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>See all current machines in zones</title>
</head>
<body>
	<h1>This is the current list of all machines</h1>
    <table border="1" style="border-collapse: collapse; width: 100%;">
        <thead>
            <tr>
                <th>Machines</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% 
                ArrayList<Machine> machines = (ArrayList<Machine>) request.getAttribute("Machines");
                if (machines != null && !machines.isEmpty()) {
                    for (Machine machine : machines) { 
            %>
            <tr>
                <td><%= machine.toString() %></td>
                <td><a href="DeleteMachineServlet?id=<%= machine.getId() %>">Delete</a></td>
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
</body>
</html>