<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/application.js"></script>
<title>Sorting Application</title>
</head>
<body>
	<h1>Sorting Application</h1>
	<hr>
	<form id="sortingForm">
		<div class="form">
			<table>
				<tr>
					<td>Previously Generated Numbers:</td>
					<td id="prevGenTd"></td>
				</tr>
				<tr>
					<td>Previously Sorted Numbers:</td>
					<td id="prevSortedTd"></td>
				</tr>
				<tr>
					<td>Enter 50 Numbers between 1 to 100 :</td>
					<!-- <td><input type="button" value="Generate Random Numbers"
						id="generateRandom"></td> -->
					<td><input id="txtInput" style="width:800px;"></td>	
				</tr>
			</table>
			<div id="divGeneratedNumbers" style="width: 800px;word-wrap:break-word;"></div>
			<div style="width: 800px;" align="center"><input type="button" id="btnSort" value="Sort Numbers"></div>
		</div>
	</form>
</body>
</html>