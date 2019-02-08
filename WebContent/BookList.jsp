<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Books Store Application</title>
</head>
<body>
	<center>
		<h1>Books Management</h1>
		<h2>
			<a href="new">Add New Book</a> &nbsp;&nbsp;&nbsp; <a href="list">List
				All Books</a>
		</h2>
	</center>
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h2>List of All Books</h2>
			</caption>
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Author</th>
				<th>Price</th>
			</tr>

			<c:forEach var="book" items="${listBook}">
				<tr>
					<td><c:out value="${book.id}" /></td>
					<td><c:out value="${book.title}" /></td>
					<td><c:out value="${book.author}" /></td>
					<td><c:out value="${book.price}" /></td>
					<td><a href="edit?id=<c:out value='${book.id}' />">Edit</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="delete?id=<c:out value='${book.id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>

</html>


