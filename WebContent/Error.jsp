<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Error</title>
</head>
<body>
		<h1>Joke Management</h1>
		<h3>
			<a href="newJoke">Post a Joke</a>  &nbsp;&nbsp;&nbsp; 
		</h3>
		<h3>	
			<a href="listJoke">List Latest 20 Jokes</a>
		</h3>
		<h3>	
			<a href="listFavoriteFriend">List All Favorite Friends</a>
		</h3>
		<h3>	
			<a href="listFavoriteJoke">List All Favorite Jokes</a>
		</h3>
		<h3>	
			<a href="searchJokeByTagForm">Search Jokes By Tag</a>
		</h3>
	<center>
		<h1>Error</h1>
		
		<h2><%
		    try {
		    	out.println(exception.getMessage());
		    } catch (NullPointerException e) {
		        out.println("<p>Exception catched " + e.getMessage() + "</p>");
		    }
			%> <br />
		</h2>
	</center>
</body>
</html>