<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="java.sql.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Validation</title>
</head>
<!-- </html> -->
<%
    try{
        String username = request.getParameter("username");   
        String password = request.getParameter("password");
        
        HttpSession sess = request.getSession(); 
        sess.setAttribute("username", username);
        sess.setAttribute("password", password);
        
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sampledb?" + "user=vzhang&password=victor1234");    
        PreparedStatement pst = conn.prepareStatement("Select userID,password from user where userID=? and password=?");
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();                        
        if(rs.next()) {           
            out.println("Valid login credentials"); 
//             request.setAttribute("username", username);
            request.getRequestDispatcher("JokeHomePage.jsp").forward(request, response);
//          request.setAttribute("listBook", listBook);       
//          RequestDispatcher dispatcher = request.getRequestDispatcher("JokeHomePage.jsp");       
//          dispatcher.forward(request, response);
        }
        else
           out.println("Invalid login credentials");            
   }
   catch(Exception e){       
       out.println("Something went wrong !! Please try again");       
   }      
%>
</html>