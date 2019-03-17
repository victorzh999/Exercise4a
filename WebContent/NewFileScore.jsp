<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html> --%>

<%@ page language="java" import="java.lang.*" import="java.sql.*"%>

<html>
<body border="1" bgcolor="pink" width="650">
	<%
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "sampledb";
		String driver = "com.mysql.cj.jdbc.Driver";
		String userName = "vzhang";
		String password = "victor1234";
		Class.forName(driver);
		con = DriverManager.getConnection(url + db, userName, password);
		Statement stmt = null;
	%>

	<form method="GET" ACTION="ProcessAction.jsp">
		<h3>
			<P ALIGN="CENTER">
				<FONT SIZE=5> EMPLOYEE INFORMATION </FONT>
			</P>
		</h3>
		</br> </br> <br> <br>
		<table callspacing=5 cellpadding=5 bgcolor="lightblue" colspan=2
			rowspan=2 align="center">
			<tr>
				<td><font size=5> Enter Employee ID </td>
				<td><input type="TEXT" ID="id" name="userID"> </font> <select
					name="userID"
					onchange="document.getElementById('id').value=this.options[this.selectedIndex].text">
						<option>Select One</option>
						<%
							String rec = "SELECT userID, firstName FROM user ORDER BY userID";
							try {
								stmt = con.createStatement();
								ResultSet rs = stmt.executeQuery(rec);
								while (rs.next()) {
						%>
						<option><%=rs.getString(1)%></option>
						<%
							}
							} catch (Exception e) {
								System.out.println(e);
							}
						%>
				</select> </font></td>
			</tr>
			<tr>
				<td><font size=5> Enter Employee Name </td>
				<td><input type="text" name="firstName"> </font></td>
			</tr>
			<tr>
				<font size=5> <B>
						<td><input type="RADIO" name="r1" VALUE="add">Insert
					</td>
			</tr>
			<tr>
				<td><input type="RADIO" name="r1" VALUE="del">Delete</td>
			</tr>
			<tr>
				<td><input type="RADIO" name="r1" VALUE="mod">Modify</td>
			</tr>
			</font>
			</b>
			<tr>
				<td><input type="SUBMIT" VALUE="Submit"> <input
					type="RESET" value="Reset"></TD>
			</tr>
</body>
</html>