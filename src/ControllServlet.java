import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
 
/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControllServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
//	private BookDAO bookDAO;
	private JokeDAO jokeDAO;
	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
//		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeDAO = new JokeDAO(jdbcURL, jdbcUsername, jdbcPassword);
		System.out.println("init called");
		
		}
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        System.out.println("Hello");
        System.out.println("action=" + action);
        try {
            switch (action) {
            case "/initializeDB":
                System.out.println("initializeDB");
            	initializeDB(request, response);
                break;            
//            case "/new":
//                showNewForm(request, response);
//                break;
//            case "/insert":
//            	insertBook(request, response);
//                break;
//            case "/delete":
//            	deleteBook(request, response);
//                break;
//            case "/edit":
//                showEditForm(request, response);
//                break;
//            case "/update":
//            	updateBook(request, response);
//                break;
//            case "/list":
//            	listBook(request, response);
//                break;    
            default:          	
//            	listBook(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void initializeDB(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
    	HttpSession sess = request.getSession(false); //use false to use the existing session
    	String userName = (String) sess.getAttribute("username");
    	
    	if (userName.equals("root")) {
        	System.out.println("userName root called");
        	jokeDAO.initializeDB();
        	RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        	dispatcher.forward(request, response);
    	}
    	else {
        	System.out.println("Error page is called");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        	dispatcher.forward(request, response);
    	}
		 
    }


//	private void listBook(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException, ServletException {
//        List<Book> listBook = bookDAO.listAllBooks();
//        request.setAttribute("listBook", listBook);       
//        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");       
//        dispatcher.forward(request, response);
//    }
// 
//    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
//        dispatcher.forward(request, response);
//    }
// 
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Book existingBook = bookDAO.getBook(id);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
//        request.setAttribute("book", existingBook);
//        dispatcher.forward(request, response);
// 
//    }
// 
//    private void insertBook(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException {
//        String title = request.getParameter("title");
//        String author = request.getParameter("author");
//        float price = Float.parseFloat(request.getParameter("price"));
//        Book newBook = new Book(title, author, price);
//        bookDAO.insertBook(newBook);
//        response.sendRedirect("list");
//    }
// 
//    private void updateBook(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        
//        System.out.println(id);
//        String title = request.getParameter("title");
//        String author = request.getParameter("author");
//        float price = Float.parseFloat(request.getParameter("price"));
//        
//        System.out.println(title);
//        
//        Book book = new Book(id, title, author, price);
//        bookDAO.updateBook(book);
//        response.sendRedirect("list");
//    }
// 
//    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        bookDAO.deleteBook(id);
//        response.sendRedirect("list"); 
//    }

}