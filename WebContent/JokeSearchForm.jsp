<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<!-- <head>
<title>Books Store Application</title>
</head> -->
<body>
<title>Joke Website Application</title>
</head>
<body>
	
		<h1>Joke Management</h1>
		<h3>
			<a href="newJoke">Post a Joke</a> 
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
		
		
		<div align="center">
		
<%-- 		<c:if test="${joke != null}">
			<form action="updateJoke" method="post">
		</c:if>
		<c:if test="${joke == null}"> --%>
			<form action="searchJokeByTag" method="post">
<%-- 		</c:if> --%>
		<table border="1" cellpadding="5">
			<caption>
				<h2>
					<%-- <c:if test="${joke != null}"> --%>
						Edit Joke
					<%-- </c:if>
					<c:if test="${joke == null}">
						Search Jokes by Tag --%>
					<%-- </c:if> --%>
				</h2>
			</caption>
			<%-- <c:if test="${joke != null}">
				<input type="hidden" name="userID" value="<c:out value='${joke.userID}' />" />
			</c:if> --%>
			<tr>
				<th>Tag:</th>
				<td><input type="text" name="tag" size="45"
					value="<c:out value='${title}' />" /></td>
			</tr>
<%-- 			<tr>
				<th>Description:</th>
				<td><input type="text" name="description" size="45"
					value="<c:out value='${joke.description}' />" /></td>
			</tr>
			<tr>
				<th>Tags:</th>
				<td><input type="text" name="tags" size="45"
					value="<c:out value='${joke.tags}' />" /></td>
			</tr> --%>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save" /></td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>