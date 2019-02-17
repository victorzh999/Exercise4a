

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//  			      .getConnection("jdbc:mysql://localhost/test?"
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
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = (Connection) DriverManager//.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
  			      .getConnection("jdbc:mysql://localhost/sys?"
  			          + "user=vzhang&password=victor1234");
            System.out.println(jdbcConnection);
        }
    }
    
	public boolean insertBook(Book book) throws SQLException {
		String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, book.getTitle());
		statement.setString(2, book.getAuthor());
		statement.setFloat(3, book.getPrice());

		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
    
	public List<Book> listAllBooks() throws SQLException {
		List<Book> listBook = new ArrayList<Book>();

		String sql = "select * from book";

		connect();

		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String title = resultSet.getString("title");
			String author = resultSet.getString("author");
			float price = resultSet.getFloat("price");

			Book book = new Book(id, title, author, price);
			listBook.add(book);
		}

		resultSet.close();
		statement.close();

		disconnect();

		return listBook;
	}  

	public boolean deleteBook(int id) throws SQLException {
		String sql = "DELETE FROM book where id = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);

		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;
	}

	public boolean updateBook(Book book) throws SQLException {
		String sql = "UPDATE book SET title = ?, author = ?, price = ?";
		sql += " WHERE id = ?";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, book.getTitle());
		statement.setString(2, book.getAuthor());
		statement.setFloat(3, book.getPrice());
		statement.setInt(4, book.getId());

		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;
	}

	public Book getBook(int id) throws SQLException {
		Book book = null;
		String sql = "SELECT * FROM book WHERE id = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			String title = resultSet.getString("title");
			String author = resultSet.getString("author");
			float price = resultSet.getFloat("price");

			book = new Book(id, title, author, price);
		}

		resultSet.close();
		statement.close();

		return book;
	}

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
}