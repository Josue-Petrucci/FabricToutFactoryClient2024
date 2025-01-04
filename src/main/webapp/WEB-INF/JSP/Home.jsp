<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="be.petrucci.javabeans.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to the Fabric'Tout website</title>
</head>
<body>
	<h1>Welcome to the Fabric'Tout website</h1>
	<%if (request.getAttribute("success") != null) {%>
        <div class="alert alert-success">
       <p><%= request.getAttribute("success")%></p> 
       </div>
    <% } %>
    	<%
        if (request.getAttribute("fail") != null) {%>
        <div class="alert alert-danger">
       <p><%= request.getAttribute("fail")%></p> 
       </div>
    <% } %>
	<%
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	%>
        <button onclick="window.location='/FabricToutFactoryClient2024/LoginServlet'">Login</button>
		<%
		    } else {
		    	if(user.isRole("Admin")){
				%>
				    <button onclick="window.location='/FabricToutFactoryClient2024/AddSiteToRegisterServlet'">Register a new user</button>
				    <br>
				<%
				}
        		if(user.isRole("Admin") || user.isRole("PuEmp")){
				%>
			        <button onclick="window.location='/FabricToutFactoryClient2024/AddZoneToMachineServlet'">Purchase new machine</button>
			        <br>
			        <button onclick="window.location='/FabricToutFactoryClient2024/SeeAllMachinesServlet'">See all Machines</button>
			        <br>
				<%
				}
	        	if(user.isRole("Admin") || user.isRole("MMana")){
        		%>
        			<button onclick="window.location='/FabricToutFactoryClient2024/AddMachineWorkersToMaintenanceServlet'">Create a maintenance</button>
        			<br>
        			<button onclick="window.location='/FabricToutFactoryClient2024/SeeAllMaintenanceServlet'">See all maintenance</button>
        			<br>
       			<%
				}
				if(user.isRole("Admin") || user.isRole("MWork")){
				%>
					<button onclick="window.location='/FabricToutFactoryClient2024/SeeAllWorkerWorkInProgressServlet'">See all maintenance</button>
					<br>
				<%
				}
			%>
       		<%@ include file="LogoutPartialView.jsp" %>
		    <%
	    	}
		%>
</body>
</html>