import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private JokeDAO jokeDAO;
	private String userName;
	private HttpSession sess;
	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		jokeDAO = new JokeDAO(jdbcURL, jdbcUsername, jdbcPassword);
		
		System.out.println("init called");
		}
	
 
	protected void getUserName(HttpServletRequest request){
    	sess = request.getSession(false); //use false to use the existing session
    	userName = (String) sess.getAttribute("username");		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
//        System.out.println(action);
//        System.out.println("Hello");
        System.out.println("action=" + action);
        try {
            switch (action) {
            case "/initializeDB":
                System.out.println("initializeDB");
            	initializeDB(request, response);
                break;            
            case "/newJoke":
                showNewJokeForm(request, response);
                break;
            case "/insertJoke":
            	insertJoke(request, response);
                break;
            case "/deleteJoke":
            	deleteJoke(request, response);
                break;
//            case "/editJoke":
//                showEditForm(request, response);
//                break;
//            case "/updateJoke":
//            	updateJoke(request, response);
//                break;
            case "/listJoke":
            	listJoke(request, response);
                break;  
            case "/searchJokeByTagForm":
            	searchJokeByTagForm(request, response);
                break; 
            case "/searchJokeByTag":
            	searchJokeByTag(request, response);
                break; 
            case "/reviewJokeForm":
            	reviewJokeForm(request, response);
                break;
            case "/reviewJoke":
            	reviewJoke(request, response);
                break;
            case "/insertFavoriteFriend":
            	insertFavoriteFriend(request, response);
                break;
            case "/listFavoriteFriend":
            	listFavoriteFriend(request, response);
                break;
            case "/insertFavoriteJoke":
            	insertFavoriteJoke(request, response);
                break;                  
            case "/listFavoriteJoke":
            	listFavoriteJoke(request, response);
                break;
            case "/listFavoriteJokeDetail":
            	listFavoriteJokeDetail(request, response);
                break;
            case "/deleteFavoriteJoke":
            	deleteFavoriteJoke(request, response);
                break;
            case "/deleteFriend":
            	deleteFriend(request, response);
                break;
            case "/listFriendProfile":
            	listFriendProfile(request, response);
                break;
            default:          	
            	listJoke(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listFavoriteJokeDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int jokeID = Integer.parseInt(request.getParameter("jokeID"));
		System.out.println("entering listFavoriteJokeDetail");
//		System.out.println("tag = " + tag);
        List<Joke> listJoke = jokeDAO.listSelectedJokesByJokeID(jokeID);
        request.setAttribute("listJoke", listJoke);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");       
        dispatcher.forward(request, response);			
	}


	private void deleteFavoriteJoke(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
    	this.getUserName(request);
        String userID = userName;
    	int jokeID = Integer.parseInt(request.getParameter("jokeID"));
    	jokeDAO.deleteUserFavoriteJoke(userID, jokeID);
    	response.sendRedirect("listFavoriteJoke");		
	}


	private void listFriendProfile(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, ServletException, IOException {
        System.out.println("entering into listFriendProfile");
    	this.getUserName(request);
        String userID = userName;
               
        System.out.println("userID = " + userID);

        String friendID = request.getParameter("friendID");
        System.out.println("friendID = " + friendID);

		List<FriendProfile> listFriendProfile = jokeDAO.listFriendProfile(friendID );
		
		for (FriendProfile friendProfile: listFriendProfile) {
			System.out.println(friendProfile.getFriendID() + friendProfile.getPostedJoke());
		}
		
        request.setAttribute("listFriendProfile", listFriendProfile);   
        RequestDispatcher dispatcher = request.getRequestDispatcher("FriendProfileList.jsp");       
        dispatcher.forward(request, response);			
	}


	private void deleteFriend(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
        this.getUserName(request);
        String userID = userName;
		String friendID = request.getParameter("friendID");
        jokeDAO.deleteUserFavoriteFriend(userID, friendID);
        response.sendRedirect("listFavoriteFriend"); 		
	}


	private void listFavoriteJoke1(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
        System.out.println("entering into controller.listFavoriteJoke");
		this.getUserName(request);
        String userID = userName;
        ArrayList<UserFavoriteJoke> listFavoriteJoke = jokeDAO.listFavoriteJoke1(userID);
//		List<Joke> listJoke = jokeDAO.listSelectedJokesByUserID(userID);
        request.setAttribute("listFavoriteJoke", listFavoriteJoke);     
        System.out.println("dispatching ...");
        RequestDispatcher dispatcher = request.getRequestDispatcher("FavoriteJokeList.jsp");       
        dispatcher.forward(request, response);				
	}

	private void listFavoriteJoke(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
        System.out.println("entering into controller.listFavoriteJoke");
		this.getUserName(request);
        String userID = userName;
        List<Joke> listJoke = jokeDAO.listFavoriteJoke(userID);
//		List<Joke> listJoke = jokeDAO.listSelectedJokesByUserID(userID);
        request.setAttribute("listJoke", listJoke);     
//        System.out.println("dispatching ...");
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");       
        dispatcher.forward(request, response);				
	}

	private void listFavoriteFriend(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
        this.getUserName(request);
        String userID = userName;
		List<UserFavoriteFriend> listFriend = jokeDAO.listFriend(userID);
        request.setAttribute("listFriend", listFriend);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("FavoriteFriendList.jsp");       
        dispatcher.forward(request, response);		
	}


	private void insertFavoriteJoke(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, ServletException, IOException {
		System.out.println("entering into insertFavoriteJoke");
		this.getUserName(request);
		String userID = userName;
        int jokeID = Integer.parseInt(request.getParameter("jokeID"));       
        UserFavoriteJoke userFavoriteJoke = new UserFavoriteJoke(userID, jokeID, "");
		jokeDAO.insertUserFavoriteJoke(userFavoriteJoke );
		response.sendRedirect("listFavoriteJoke");
		
		
//        List<Joke> listJoke = jokeDAO.listAllJokes();
//        request.setAttribute("listFriend", listFriend); 
        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");
////        request.setAttribute("jokeID", jokeID);
//        dispatcher.forward(request, response);			
	}


	private void insertFavoriteFriend(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
		System.out.println("entering into insertFavoriteFriend");
		this.getUserName(request);
		String userID = userName;
//        int jokeID = Integer.parseInt(request.getParameter("jokeID")); 
		String friendID = request.getParameter("userID");
		UserFavoriteFriend userFavoriteFriend = new UserFavoriteFriend(userID, friendID);
		jokeDAO.insertUserFavoriteFriend(userFavoriteFriend );
		response.sendRedirect("listFavoriteFriend");			
	}


	private void reviewJoke(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException {
		System.out.println("entering into reviewJoke");

    	this.getUserName(request);
    	
    	String userID = userName;   
    	
		System.out.println("userID = " + userID);
		System.out.println("spot1");

    	int jokeID = Integer.parseInt(request.getParameter("jokeID"));
    	System.out.println("spot2");
		System.out.println("jokeID = " + jokeID);

    	String score = request.getParameter("score");
		String remark = request.getParameter("remark");
		System.out.println("entering reviewJoke");
		System.out.println("score = " + score);
		
        JokeReview jokeReview = new JokeReview(userID, jokeID, score.trim().toLowerCase(), remark);
        jokeDAO.insertJokeReview(jokeReview);       
        response.sendRedirect("listJoke");
	}


	private void reviewJokeForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
//        Book existingBook = bookDAO.getBook(id);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
//        request.setAttribute("book", existingBook);
//        dispatcher.forward(request, response);
		
		System.out.println("entering into reviewJokeForm");
        int jokeID = Integer.parseInt(request.getParameter("jokeID"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeReviewForm.jsp");
        request.setAttribute("jokeID", jokeID);
        dispatcher.forward(request, response);			
	}


	private void searchJokeByTagForm(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeSearchForm.jsp");
        dispatcher.forward(request, response);		
	}


	private void searchJokeByTag(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
		String tag = request.getParameter("tag");
		System.out.println("entering searchJokeByTag");
		System.out.println("tag = " + tag);
        List<Joke> listJoke = jokeDAO.listSelectedJokesByTag(tag.trim().toLowerCase());
        request.setAttribute("listJoke", listJoke);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");       
        dispatcher.forward(request, response);		
	}


	private void initializeDB(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
    	this.getUserName(request);
    	System.out.println("userName = " + userName); 

//    	HttpSession sess = request.getSession(false); //use false to use the existing session
//    	String userName = (String) sess.getAttribute("username");
    	
    	if (userName.equals("john")) {
        	System.out.println("userName john called");
//        	jokeDAO.initializeDB();
        	RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");
        	dispatcher.forward(request, response);
    	}
    	else if (userName.equals("root")) {
        	System.out.println("userName root called");
        	jokeDAO.initializeDB();
        	RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");
        	dispatcher.forward(request, response);
    	}
    	else {
        	System.out.println("Error page is called");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
        	dispatcher.forward(request, response);
    	}
    }

	private void listJoke(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Joke> listJoke = jokeDAO.listAllJokes();
        request.setAttribute("listJoke", listJoke);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeList.jsp");       
        dispatcher.forward(request, response);
    }
 
    private void showNewJokeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeForm.jsp");
        dispatcher.forward(request, response);
    }
 
//    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Book existingBook = bookDAO.getBook(id);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
//        request.setAttribute("book", existingBook);
//        dispatcher.forward(request, response);
// 
//    }

    private void insertJoke(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {    	
		System.out.println("entering insertJoke function");

		this.getUserName(request);
//    	HttpSession sess = request.getSession(false); //use false to use the existing session
//    	String userName = (String) sess.getAttribute("username");
		
    	String userID = userName;    
		System.out.println("userName = " + userName);
		System.out.println("userID = " + userID);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");

        //check each tag is a single word, without space
		String[] tagArr = tags.split(",");
		boolean tagHasSpace = false;
		
		for (String tag: tagArr) {
			if (tag.trim().contains(" ")) tagHasSpace = true;
		}

		System.out.println("tagHasSpace = " + tagHasSpace);
		
		if (tagHasSpace) {
			System.out.println("Tag has space inside it!");

			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
	        dispatcher.forward(request, response);
		}
		else {	        
	        Joke newJoke = new Joke(userID, title, description);
	        jokeDAO.insertJoke(newJoke);
	        System.out.println("title = " + title);
	        int jokeID = jokeDAO.getJokeID(userID);
	        System.out.println("jokeID = " + jokeID);
	        jokeDAO.insertJokeTag(jokeID, tags);
	        
	        response.sendRedirect("listJoke");
		}
//		System.out.println("enterng insertJokeTag function");
    }

//    private void updateJoke(HttpServletRequest request, HttpServletResponse response)
//            throws SQLException, IOException {
//    	this.getUserName(request);
//    	String userID = this.userName;    	
//        String title = request.getParameter("title");
//        String description = request.getParameter("description");
//        
//        int jokeID = Integer.parseInt(request.getParameter("jokeID"));       
//        System.out.println(jokeID);
//        String title = request.getParameter("title");
//        String author = request.getParameter("author");
//        float price = Float.parseFloat(request.getParameter("price"));
//        
//        System.out.println(title);
//        
//        Book book = new Book(jokeID, title, author, price);
//        bookDAO.updateBook(book);
//        response.sendRedirect("list");
//    }
 
    private void deleteJoke(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int jokeID = Integer.parseInt(request.getParameter("jokeID"));
//        jokeDAO.deleteJokeTag(jokeID);
        jokeDAO.deleteJoke(jokeID);
        response.sendRedirect("listJoke"); 
    }

}