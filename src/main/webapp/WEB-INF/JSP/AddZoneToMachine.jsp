<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="be.petrucci.javabeans.Zone"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Choose zones where the machine will be</title>
</head>
<body>
	<h1>Choose zones where the machine will be : don't forget to
		choose zones from the same site and factory!</h1>

	<form action="/FabricToutFactoryClient2024/AddZoneToMachineServlet"
		method="POST">
		<table border="1" style="border-collapse: collapse; width: 100%;">
			<thead>
				<tr>
					<th>Select</th>
					<th>Zone Details</th>
				</tr>
			</thead>
			<tbody>
				<%
				ArrayList<Zone> zones = (ArrayList<Zone>) request.getAttribute("Zones");
				if (zones != null && !zones.isEmpty()) {
					for (Zone zone : zones) {
				%>
				<tr>
					<td><input type="checkbox" name="selectedZoneIds"
						value="<%=zone.getId()%>"></td>
					<td><%=zone.toString()%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="2" style="text-align: center;">No zones available</td>
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
