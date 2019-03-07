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
				<h2>List of All Favorite Friends</h2>
			</caption>
			<tr>
<!-- 				<th>JokeID</th>
 -->				<th>FriendID</th>
<!-- 				<th>Title</th>
				<th>Description</th>
				<th>CreatedDate</th> -->
			</tr>

			<c:forEach var="friend" items="${listFriend}">
			<input type="hidden" name="userID" value="<c:out value='${friend.userID}' />" />
				<tr>
<%-- 					<td><c:out value="${joke.jokeID}" /></td>
 --%>					<td><c:out value="${friend.friendID}" /></td>
<%-- 					<td><c:out value="${joke.title}" /></td>
					<td><c:out value="${joke.description}" /></td>
					<td><c:out value="${joke.createdDate}" /></td> --%>
					
					<td>
					<a href="deleteFriend?friendID=<c:out value='${friend.friendID}' />">Delete</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="listFriendProfile?friendID=<c:out value='${friend.friendID}' />">ListProfile</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>

</html>


