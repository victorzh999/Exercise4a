

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table book in the database.
 * 
 * @author www.codejava.net
 *
 */
@WebServlet("/JokeDAO")
public class JokeDAO extends HttpServlet{
	private static final long serialVersionUID = 1L;
//	private Statement statement = null;
//	private PreparedStatement preparedStatement = null;
//	private ResultSet resultSet = null;
	private Connection jdbcConnection = null;
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	
    public JokeDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
    
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//  			      .getConnection("jdbc:mysql://localhost/sampledb?"
//  			          + "user=vzhang&password=victor1234");
            System.out.println(jdbcConnection);
        }
    }
    
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

    protected void root_connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = (Connection) DriverManager//.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
  			      .getConnection("jdbc:mysql://localhost/sys?"
  			          + "user=vzhang&password=victor1234");
            System.out.println(jdbcConnection);
        }
    }
    
	public boolean insertJoke(Joke joke) throws SQLException {
		String sql = "INSERT INTO sampledb.joke(userID, title, description) VALUES (?, ?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, joke.getUserID());
		statement.setString(2, joke.getTitle());
		statement.setString(3, joke.getDescription());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public boolean insertJokeTag(JokeTag jokeTag) throws SQLException {
		String sql = "INSERT INTO sampledb.joke_tag(jokeID, tag) VALUES (?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, jokeTag.getJokeID());
		statement.setString(2, jokeTag.getTag());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public boolean insertJokeReview(JokeReview jokeReview) throws SQLException {
		String sql = "INSERT INTO sampledb.joke_review(reviewerID, jokeID, score, remark) VALUES (?, ?, ?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, jokeReview.getReviewerID());
		statement.setInt(2, jokeReview.getJokeID());
		statement.setString(3, jokeReview.getScore());
		statement.setString(4, jokeReview.getRemark());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}

	public boolean insertUserFavoriteFriend(UserFavoriteFriend userFavoriteFriend) throws SQLException {
		String sql = "INSERT INTO sampledb.user_favorite_friend(userID, friendID) VALUES (?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, userFavoriteFriend.getUserID());
		statement.setString(2, userFavoriteFriend.getFriendID());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public boolean insertUserFavoriteJoke(UserFavoriteJoke userFavoriteJoke) throws SQLException {
		String sql = "INSERT INTO sampledb.user_favorite_joke(userID, jokeID) VALUES (?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, userFavoriteJoke.getUserID());
		statement.setInt(2, userFavoriteJoke.getJokeID());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
			
	public boolean insertUser(User user) throws SQLException {
		String sql = "INSERT INTO sampledb.user(userID, password, firstName, lastName, email, "
				+ "gender, age, isRoot, isBlacklist) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, user.getUserID());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getFirstName());
		statement.setString(4, user.getLastName());
		statement.setString(5, user.getEmail());
		statement.setString(6, user.getGender());
		statement.setInt(7, user.getAge());
		statement.setBoolean(8, user.getIsRoot());
		statement.setBoolean(9, user.getIsBlacklist());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Joke> listSelectedJokesByTag(String tag) throws SQLException {
		List<Joke> listJoke = new ArrayList<Joke>();

		System.out.println("entering listSelectedJokesByTag");
		System.out.println("tag = " + tag);
		String sql = "select jk.jokeID, jk.userID, jk.title, jk.description, jk.createdDate "
				+ "from `joke` jk join `joke_tag` jt on jk.jokeID = jt.jokeID where jt.tag = '" + tag + "'";

		connect();
		System.out.println("statement");

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setString(1, tag);
				
		ResultSet resultSet = statement.executeQuery(sql);
		System.out.println("sql");
		
		listJoke = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listJoke;
	}  

	public List<Joke> listSelectedJokesByJokeID(int jokeID) throws SQLException {
		List<Joke> listJoke = new ArrayList<Joke>();

		String sql = "select jokeID, userID, title, description, createdDate from sampledb.joke "
				+ "where jokeID = '" + jokeID + "'";

		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setInt(1, jokeID);
				
		ResultSet resultSet = statement.executeQuery(sql);
		listJoke = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listJoke;
	}  

	public List<FriendProfile> listFriendProfile(String friendID) throws SQLException {
		System.out.println("entering into JokeDAO.listFriendProfile");
		List<FriendProfile> listFriendProfile = new ArrayList<FriendProfile>();

		String sql = "select user.firstName, user.lastName, joke.title as postedJoke, "
				+ "joke.createdDate as postedDate from sampledb.user join sampledb.joke "
				+ "on user.userID = joke.userID where user.userID = '" + friendID
				+ "'";

		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setString(1, friendID);
				
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			String firstName = resultSet.getString("firstName");
			String lastName = resultSet.getString("lastName");
			String postedJoke = resultSet.getString("postedJoke");
			String postedDate = resultSet.getString("postedDate");

			FriendProfile friendProfile = new FriendProfile(friendID, firstName, lastName, postedJoke, postedDate);
			listFriendProfile.add(friendProfile);
		}

		resultSet.close();
		statement.close();

		disconnect();

		return listFriendProfile;
	}  

	public boolean deleteUserFavoriteFriend(String userID, String friendID) throws SQLException {
		String sql = "DELETE FROM User_Favorite_Friend where userID = ? and friendID = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, userID);
		statement.setString(2, friendID);

		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;
	}

	public boolean deleteUserFavoriteJoke(String userID, int jokeID) throws SQLException {
		String sql = "DELETE FROM User_Favorite_Joke where userID = ? and jokeID = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, userID);
		statement.setInt(2, jokeID);

		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;
	}
	
//	public boolean updateBook(Joke book) throws SQLException {
//		String sql = "UPDATE book SET title = ?, author = ?, price = ?";
//		sql += " WHERE id = ?";
//		connect();
//
//		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setString(1, book.getTitle());
//		statement.setString(2, book.getAuthor());
//		statement.setFloat(3, book.getPrice());
//		statement.setInt(4, book.getId());
//
//		boolean rowUpdated = statement.executeUpdate() > 0;
//		statement.close();
//		disconnect();
//		return rowUpdated;
//	}
//
//	public Joke getBook(int id) throws SQLException {
//		Joke book = null;
//		String sql = "SELECT * FROM book WHERE id = ?";
//
//		connect();
//
//		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setInt(1, id);
//
//		ResultSet resultSet = statement.executeQuery();
//
//		if (resultSet.next()) {
//			String title = resultSet.getString("title");
//			String author = resultSet.getString("author");
//			float price = resultSet.getFloat("price");
//
//			book = new Joke(id, title, author, price);
//		}
//
//		resultSet.close();
//		statement.close();
//
//		return book;
//	}

	public void initializeDB() throws SQLException, IOException {
		root_connect();
		
		String sqlStatement = "";
		int resultSet = 0;
		ResultSet resultSetQuery = null;
		Statement statement = null;
		String sql1 = " ";
	
//		create database and tables
		String fileName = "/Users/vzhang/eclipse-workspace/Exercise4a/assignment1.sql";
		List<String> lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());

		List<String> sqls = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++){
			if (lines.get(i).startsWith("--")){
				continue;
			}
			else if (lines.get(i).contains(";")) {
				sqls.add(sql1 + lines.get(i));
				sql1 = " ";
			}
			else {
				sql1  += lines.get(i);
			}
		}
		
// create database and table			
		for (String sql: sqls) {
			sql = sql.trim();

			if (sql.startsWith(" use")){
				sqlStatement = sql;
				statement = jdbcConnection.createStatement();
				resultSetQuery = statement.executeQuery(sqlStatement);
			}
			else {
				sqlStatement = sql;
				statement = jdbcConnection.createStatement();
				resultSet = statement.executeUpdate(sqlStatement);
			}
		}
		
		System.out.println("tables are created!");
		
		// create triggers and procedures
		fileName = "/Users/vzhang/eclipse-workspace/Exercise4a/assignment1_triggers.sql";
		lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());

		boolean resultSet1 = false;
		
		sql1 = " ";
		sqls = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++){
			if (lines.get(i).startsWith("--")){
				continue;
			}
			else if (lines.get(i).contains("END;")) {
				sqls.add(sql1 + lines.get(i));
				sql1 = " ";
			}
			else {
				sql1  += lines.get(i);
			}
		}
			
		for (String sql: sqls) {
			sql = sql.trim();

			sqlStatement = sql;
			statement = jdbcConnection.createStatement();
			resultSet1 = statement.execute(sqlStatement);	
		}

		System.out.println("triggers are created!");

//		statement.close();
//		resultSetQuery.close();
		disconnect();
		
		System.out.println("InitializeDB is Completed");
	}

	public List<Joke> listAllJokes() throws SQLException {
		List<Joke> listJoke = new ArrayList<Joke>();
		String sql = "select jokeID, userID, title, description, createdDate "
				+ "from sampledb.joke order by createdDate desc limit 20";

		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery(sql);

		listJoke = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listJoke;
	}

	public List<Joke> listJoke(ResultSet resultSet) throws SQLException {
		List<Joke> listJoke = new ArrayList<Joke>();
		
		while (resultSet.next()) {
			int jokeID = resultSet.getInt("jokeID");
			String userID = resultSet.getString("userID");
			String title = resultSet.getString("title");
			String description = resultSet.getString("description");
			String createdDate = resultSet.getString("createdDate");

			Joke joke = new Joke(jokeID, userID, title, description, createdDate);
			listJoke .add(joke);
		}
		return listJoke;
	}

	public boolean deleteJoke(int jokeID) throws SQLException {
		String sql = "DELETE FROM Joke where jokeID = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, jokeID);

		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;
	}

	
	public int getJokeID(String userID) throws SQLException {
		int jokeID = -1;
//		title = "joke1";
//		String sql = "select max(jokeID) as maxJokeID from joke where jokeID > 0 ";
		String sql = "select max(jokeID) as maxJokeID from joke where userID = '"
				 + userID + "'";

		System.out.println("entering into function getJokeID");
		System.out.println("userID = " + userID);
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setString(1, userID);	
		System.out.println("userID = " + userID);
		ResultSet resultSet = statement.executeQuery(sql);

		System.out.println("run sql");
		
		
		while (resultSet.next()) {
			jokeID = resultSet.getInt("maxJokeID");
			System.out.println("jokeID = " + jokeID);
			break;
////			listJoke.add(joke);
//			System.out.println("jokeID = " + jokeID);
		}
//
		resultSet.close();
		statement.close();

		disconnect();

		return jokeID;
	}

	public boolean insertJokeTag(int jokeID, String tags) throws SQLException {
		String[] tagArr = tags.split(",");
		
		System.out.println("enterng insertJokeTag function");
		List<JokeTag> jokeTags = new ArrayList<JokeTag>();
		for (String tag: tagArr) {
			jokeTags.add(new JokeTag(jokeID, tag.trim().toLowerCase()));
		}

		connect();

		PreparedStatement statement = null;
		int rowInserted = 0;
				
		for (JokeTag jokeTag: jokeTags) {
			String sql = "INSERT INTO sampledb.joke_tag(jokeID, tag) VALUES (?, ?)";
			statement = jdbcConnection.prepareStatement(sql);
			System.out.println("jokeID = " + jokeTag.getJokeID());
			statement.setInt(1, jokeTag.getJokeID());
			System.out.println("tag = " + jokeTag.getTag());
			statement.setString(2, jokeTag.getTag());
			rowInserted = statement.executeUpdate();
		}
		
//		String sql = "INSERT INTO sampledb.joke_tag(jokeID, tag) VALUES (?, ?)";
//
//
//		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setInt(1, jokeTag.getJokeID());
//		statement.setString(2, jokeTag.getTag());
		
		boolean rowInsertedBool  = rowInserted > 0;
		statement.close();
		disconnect();
		return rowInsertedBool;
		
	}

	public boolean deleteJokeTag(int jokeID) throws SQLException {
		String sql = "DELETE FROM joke_tag where jokeID = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, jokeID);

		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}

	public List<UserFavoriteFriend> listFriend(String userID) throws SQLException {
		List<UserFavoriteFriend> listFriend = new ArrayList<UserFavoriteFriend>();
		String sql = "SELECT userID, friendID FROM user_favorite_friend where userID = '" + userID +
				"'";

		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
//			int jokeID = resultSet.getInt("jokeID");
//			String userID = resultSet.getString("userID");
			String friendID = resultSet.getString("friendID");
//			String description = resultSet.getString("description");
//			String createdDate = resultSet.getString("createdDate");

			UserFavoriteFriend freind = new UserFavoriteFriend(userID, friendID);
			listFriend.add(freind);
		}
//		listFriend = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listFriend;	
	}

	public List<Joke> listSelectedJokesByUserID(String userID) throws SQLException {
		List<Joke> listJoke = new ArrayList<Joke>();

		System.out.println("entering listSelectedJokesByUserID");
		System.out.println("userID = " + userID);
		String sql = "select jokeID, userID, title, description, createdDate from sampledb.joke where userID = '"
				+ userID + "'";
		connect();
		System.out.println("statement");

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
				
		ResultSet resultSet = statement.executeQuery(sql);
		System.out.println("sql");
		
		listJoke = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listJoke;
		
	}

	public ArrayList<UserFavoriteJoke> listFavoriteJoke1(String userID) throws SQLException {
		ArrayList<UserFavoriteJoke> listFavoriteJoke = new ArrayList<UserFavoriteJoke>();
		String sql = "SELECT ufj.userID, ufj.jokeID, joke.title FROM user_favorite_joke ufj join joke on "
				+ "joke.jokeID = ufj.jokeID and ufj.userID = '" + userID +
				"'";

		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
//			int jokeID = resultSet.getInt("jokeID");
//			String userID = resultSet.getString("userID");
			int jokeID = resultSet.getInt("jokeID");
			String title = resultSet.getString("title");
//			String createdDate = resultSet.getString("createdDate");

			System.out.println(jokeID + title);
			UserFavoriteJoke userFavoriteJoke = new UserFavoriteJoke(userID, jokeID, title);
			listFavoriteJoke.add(userFavoriteJoke);
		}
//		listFriend = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listFavoriteJoke;		
	}

	public List<Joke> listFavoriteJoke(String userID) throws SQLException {
		List<Joke> listJoke = new ArrayList<Joke>();
//		String sql = "SELECT ufj.userID, ufj.jokeID, joke.title FROM user_favorite_joke ufj join joke on "
//				+ "joke.jokeID = ufj.jokeID and ufj.userID = '" + userID +
//				"'";
		String sql = "select jk.jokeID, jk.userID, jk.title, jk.description, jk.createdDate "
				+ "from joke jk join user_favorite_joke ufj on jk.jokeID = ufj.jokeID "
				+ "and ufj.userID = '" + userID + "' order by jk.createdDate desc limit 20";

		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery(sql);


		listJoke = listJoke(resultSet);

		resultSet.close();
		statement.close();

		disconnect();

		return listJoke;
	}	
}