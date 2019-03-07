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
				<h2>List of One Friend's Profile</h2>
			</caption>
			<tr>
				<th>FriendID</th>
				<th>FirstName</th>
				<th>LastName</th>
				<th>PostedJoke</th>
				<th>PostedDate</th>
			</tr>
	
			<c:forEach var="friendProfile" items="${listFriendProfile}">
				<tr>
					<td><c:out value="${friendProfile.friendID}" /></td>
					<td><c:out value="${friendProfile.firstName}" /></td>
					<td><c:out value="${friendProfile.lastName}" /></td>
					<td><c:out value="${friendProfile.postedJoke}" /></td>
					<td><c:out value="${friendProfile.postedDate}" /></td>
					
<%-- 					<td><a href="editJoke?jokeID=<c:out value='${joke.jokeID}' />">Edit</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="deleteJoke?jokeID=<c:out value='${joke.jokeID}' />">Delete</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="reviewJokeForm?jokeID=<c:out value='${joke.jokeID}' />">Review</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="favoriteJoke?jokeID=<c:out value='${joke.jokeID}' />">FavoriteJoke</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="favoriteFriend?userID=<c:out value='${joke.userID}' />">FavoriteFriend</a>
					</td> --%>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>

</html>


