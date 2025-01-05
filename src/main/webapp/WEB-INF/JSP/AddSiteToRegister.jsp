<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="be.petrucci.javabeans.Site"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Choose the site for the user registration</title>
</head>
<body>

	<%
	if (request.getAttribute("fail") != null) {
	%>
	<div class="alert alert-danger">
		<p><%=request.getAttribute("fail")%></p>
	</div>
	<%
	}
	%>

	<h1>Choose the site for the user registration</h1>

	<form action="/FabricToutFactoryClient2024/AddSiteToRegisterServlet"
		method="post">
		<h2>Site</h2>
		<table border="1" style="border-collapse: collapse; width: 100%;">
			<thead>
				<tr>
					<th>Select</th>
					<th>Site Details</th>
				</tr>
			</thead>
			<tbody>
				<%
				ArrayList<Site> siteList = (ArrayList<Site>) request.getAttribute("Sites");
				if (siteList != null && !siteList.isEmpty()) {
					for (Site site : siteList) {
				%>
				<tr>
					<td><input type="radio" name="selectedSiteId"
						value="<%=site.getId()%>"></td>
					<td><%=site.toString()%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="2" style="text-align: center;">No Site available</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<br> <input type="submit" class="btn btn-primary" name="submit"
			id="submit" value="Next" />
	</form>
</body>
</html>