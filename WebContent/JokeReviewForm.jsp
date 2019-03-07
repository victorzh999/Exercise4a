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
		
<!--     <form name="scoreForm" method="get" action="#">
     <input type="submit" name="submit" value="Select Score"/>
    </form> -->
    
    
    <form action="" method="get">
       <select name="score">
           			<option value="1">On hold</option>
                    <option value="2">Confirmed</option>
                    <option value="3">Declined</option>
                <input type="hidden" name="action" value="changestatus">
                <input type="hidden" name="ticketId" value="${entry.key}">
                <input type="submit" value="Change">
</form>


	
<%-- 		<c:if test="${joke != null}">
			<form action="updateJoke" method="post">
		</c:if>
		<c:if test="${joke == null}"> --%>
			<form action="reviewJoke" method="post">
<%-- 		</c:if> --%>

       </select>
		<table border="1" cellpadding="5">
			<caption>
				<h2>
<%-- 					<c:if test="${joke != null}">
						Edit Joke
					</c:if>
					<c:if test="${joke == null}"> --%>
						Review a Joke
<%-- 					</c:if> --%>
				</h2>
			</caption>
<%-- 			<c:if test="${joke != null}"> --%>
				<input type="hidden" name="jokeID" value="<c:out value='${jokeID}' />" />
<%-- 			</c:if> --%>
			<tr>
				<th>Score:</th>
				<td><input type="text" name="score" size="45"
					value= "<c:out value='${entry.key}' />" />  </td>
					<%-- value="<c:out value=request.getParameter("score") />" /></td> --%>
			</tr>
			<tr>
				<th>Remark:</th>
				<td><input type="text" name="remark" size="45"
					value="<c:out value='${remark}' />" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save" /></td>
			</tr>
		</table>
		</form>
		


	</div>
</body>
</html>