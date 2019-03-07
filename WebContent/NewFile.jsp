<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
    <title>Dynamic Drop Down List Demo - CodeJava.net</title>
</head>
<body>
 
<div align="center">
    <h2>Dynamic Drop Down List Demo</h2>
<%--     <form action="list" method="post">
        Select a Category:&nbsp;
        <select name="category">
            <c:forEach items="${listCategory}" var="category" >
                <option value="${category.id}"
                    <c:if test="${category.id eq selectedCatId}">selected="selected"</c:if>
                    ${category.name}
                </option>
            </c:forEach>
        </select>
        <br/><br/>
        <input type="submit" value="Submit" />
    </form> --%>
</div>
</body>
</html>