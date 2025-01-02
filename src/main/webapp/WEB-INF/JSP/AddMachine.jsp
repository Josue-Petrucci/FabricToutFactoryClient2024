<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Purchasing a new machine</title>
<jsp:include page="/WEB-INF/JSP/Bootstrap.jsp"/>
</head>
<body>
	<h1>Please enter the kind of machine you want to buy!</h1>
    <div class="container">
        <form action="AddMachineServlet" method="POST" class="mt-4">
            <div class="form-group">
                <label for="type">Type :</label>
                <select class="form-control" name="type" id="type">
                    <option value="Fabrication">Fabrication</option>
                    <option value="Assembly">Assembly</option>
                    <option value="Sorting">Sorting</option>
                </select>
            </div>
            <div class="form-group">
			    <label for="size">Size:</label>
			    <input type="number" class="form-control" name="size" id="size" value="" step="0.01" min="1" required />
			</div>
            <div class="form-group text-center">
                <input type="submit" class="btn btn-primary" name="submit" id="submit" value="Submit" />
            </div>
        </form>
    </div>
</body>
</html>