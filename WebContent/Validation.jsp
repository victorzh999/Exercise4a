<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Validation</title>
</head>

<script type="text/javascript">
	function alertName(){
		alert("You are in blacklist!!");
		} 
</script>

<%-- <% --%>
// 	String message = (String) request.getAttribute("alertMsg");
<%-- %> --%>

<!-- <script type="text/javascript"> -->
<%--     var msg = "<%=message%> --%>
// 	";
// 	alert(msg);
<!-- </script> -->


<%
	try {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HttpSession sess = request.getSession();
		sess.setAttribute("username", username);
		sess.setAttribute("password", password);

		Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL database connection
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost/sampledb?" + "user=vzhang&password=victor1234");
		PreparedStatement pst = conn.prepareStatement(
				"Select userID, password, isBlacklist from user where userID=? and password=?");
		pst.setString(1, username);
		pst.setString(2, password);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			out.println("Valid login credentials! ");
			if (rs.getInt("isBlacklist") == 1) {
// 				alertName;
// 				request.setAttribute("alertMsg", "You are locked to the website! ");
// 				RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
// 				rd.include(request, response);
				out.println("Warning! You are in blacklist!");
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("JokeHomePage.jsp").forward(request, response);
			}
		} else
			out.println("Invalid login credentials!");
	} catch (Exception e) {
		out.println("Something went wrong !! Please try again");
	}
%>
</html>