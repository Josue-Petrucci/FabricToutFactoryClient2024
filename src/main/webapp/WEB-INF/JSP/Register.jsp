<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register a new user</title>
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp" />
</head>
<body>
	<h1>Please enter the new user informations!</h1>
	<div class="container">
		<form action="RegisterServlet" method="POST" class="mt-4">
			<div class="form-group">
				<label for="lastname">Lastname:</label> <input type="text"
					class="form-control" name="lastname" id="lastname" value=""
					size="20" title="Minimum 3 characters" required /> <span
					class="comment">Minimum 3 characters</span>
			</div>
			<div class="form-group">
				<label for="firstname">Firstname:</label> <input type="text"
					class="form-control" name="firstname" id="firstname" value=""
					size="20" title="Minimum 3 characters" required /> <span
					class="comment">Minimum 3 characters</span>
			</div>
			<div class="form-group">
				<label for="age">Age:</label> <input type="number"
					class="form-control" name="age" id="age" value="" min="18" required />
				<span class="comment">Minimum 18 years</span>
			</div>
			<div class="form-group">
				<label for="address">Address:</label> <input type="text"
					class="form-control" name="address" id="address" value=""
					title="Minimum 3 characters" required /> <span class="comment">Minimum
					3 characters</span>
			</div>
			<div class="form-group">
				<label for="matricule">Matricule :</label> <select
					class="form-control" name="matricule" id="matricule">
					<option value="Admin-">Administrator</option>
					<option value="MMana-">Maintenance manager</option>
					<option value="MWork-">Maintenance worker</option>
					<option value="PuEmp-">Purchase employee</option>
				</select>
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					class="form-control" name="password" id="password" value=""
					size="20" title="Minimum 5 characters" required /> <span
					class="comment">Password only in capital letters and minimum
					5 characters</span>
			</div>
			<div class="form-group text-center">
				<input type="submit" class="btn btn-primary" name="submit"
					id="submit" value="Submit" />
			</div>
		</form>
	</div>
</body>
</html>