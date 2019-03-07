<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Joke Website Application</title>
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
	
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h2>List of All Favorite Jokes</h2>
			</caption>
			<tr>
<!-- 				<th>JokeID</th>
 -->				<!-- <th>JokeID</th> -->
				<th>Title</th>
<!-- 				<th>Description</th>
				<th>CreatedDate</th> -->
			</tr>

			<c:forEach var="favoriteJoke" items="${listFavoriteJoke}">
			<input type="hidden" name="userID" value="<c:out value='${favoriteJoke.userID}' />" />
			<input type="hidden" name="jokeID" value="<c:out value='${favoriteJoke.jokeID}' />" />
				<tr>
<%-- 					<td><c:out value="${joke.jokeID}" /></td>
 --%>					<%-- <td><c:out value="${favoriteJoke.jokeID}" /></td> --%>
					<td><c:out value="${favoriteJoke.title}" /></td>
<%-- 					<td><c:out value="${joke.description}" /></td>
					<td><c:out value="${joke.createdDate}" /></td> --%>
					
					<td>
					<a href="deleteFavoriteJoke?jokeID=<c:out value='${favoriteJoke.jokeID}' />">Delete</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="listFavoriteJokeDetail?jokeID=<c:out value='${favoriteJoke.jokeID}' />">ListJokeDetail</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>

</html>


