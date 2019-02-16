

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
                Class.forName("com.mysql.cj.jdbc.Driver");
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
		
		statement = jdbcConnection.createStatement();
//		boolean resultSet1 = statement.execute(sqlStatement);
		sqlStatement = "CREATE TRIGGER joke_before_insert BEFORE INSERT ON joke " + 
				"	FOR EACH ROW " + 
				"	BEGIN " + 
				"    IF (select count(1) from sampledb.joke where date(createddate) =  date(current_timestamp()) group by userID order by 1 desc limit 1) = 5 THEN " + 
				"        SIGNAL SQLSTATE '45002'  " + 
				"           SET MESSAGE_TEXT = 'check constraint on joke posts per day failed'; " + 
				"    END IF; " + 
				"	END;";
		statement = jdbcConnection.createStatement();
		resultSet1 = statement.execute("CREATE TRIGGER obs_update BEFORE INSERT ON sampledb.joke " //
		        + "FOR EACH ROW "//
		        + "BEGIN "//
		        + "IF joke.jokeID = 1 THEN "//
		        + "   DELETE FROM joke WHERE jokeID = 1; "//
		        + "END IF; "//
		        + "END;");
		
//		String sqlStatement = "";
//		int resultSet = 0;
//		ResultSet resultSetQuery = null;
//		Statement statement = null;
/*		
//		drop database sampledb
		String sql = "drop database if exists sampledb";
		Statement statement = jdbcConnection.createStatement();
		int resultSet = statement.executeUpdate(sql);
		
//		create database sampledb
		sql = "create database sampledb";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		
//		use sampledb
		sql = "use sampledb";
		Statement statementQuery = jdbcConnection.createStatement();
		ResultSet resultSetQuery = statementQuery.executeQuery(sql);

//		create table user
		sql = "create table user (" + 
				"userID int not null auto_increment," +
				"userName varchar(20) not null," +
				"password varchar(50) not null,"+
				"firstName varchar(50) not null," +
				"lastName varchar(50) not null," +
				"email varchar(100) not null," +
				"gender varchar(20)," +
				"age int," +
				"isRoot int default 0," +
				"createdDate datetime default current_timestamp()," +
				"updatedDate datetime default current_timestamp()," +
				"primary key (userID)," +
				"unique user_userName_unique (userName)," +
				"unique user_email_unique (email)" +
				") auto_increment = 1";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table user is created");

//		initialize table user
		sql = "insert into user(" + 
				"userName, password, firstName, lastName, email, isRoot" + 
				")" + 
				"values" + 
				"('root', 'pass1234', 'vzhang', 'man', 'root@hotmail.com', 1)" + 
				",('john', 'pass1234', 'root', 'man2', 'man@hotmail.com', 0)" + 
				",('root1', 'pass1234', 'john1', 'man', 'root1@hotmail.com', 0)" + 
				",('john1', 'pass1234', 'root1', 'man2', 'man1@hotmail.com', 0)" + 
				",('root2', 'pass1234', 'john2', 'man', 'root2@hotmail.com', 0)" + 
				",('john2', 'pass1234', 'root2', 'man2', 'man2@hotmail.com', 0)" + 
				",('root3', 'pass1234', 'john3', 'man3', 'root3@hotmail.com', 0)" + 
				",('john3', 'pass1234', 'root3', 'man3', 'man3@hotmail.com', 0)" + 
				",('root4', 'pass1234', 'john4', 'man', 'root4@hotmail.com', 0)" + 
				",('john4', 'pass1234', 'root4', 'man2', 'man4@hotmail.com', 0)" + 
				",('vzhang', 'victor1234', 'vzhang', 'man', 'vzhang@hotmail.com', 0)";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table user is populated");

//		create table joke
		sql = "create table joke (" + 
				"jokeID int not null auto_increment," + 
				"userID int," + 
				"title varchar(100) not null," + 
				"description text," + 
				"createdDate datetime default current_timestamp()," + 
				"updatedDate datetime default current_timestamp()," + 
				"primary key (jokeID)," + 
				"FOREIGN KEY FK_joke_userID (userID) REFERENCES user(userID)" + 
				"		ON DELETE NO ACTION  " + 
				"        ON UPDATE CASCADE" + 
				") auto_increment = 1"; 
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table joke is created");

//		create table joke_tag
		sql = "create table joke_tag(" + 
				"jokeID int not null," + 
				"tag varchar(50)," + 
				"createdDate datetime default current_timestamp()," + 
				"updatedDate datetime default current_timestamp()," + 
				"primary key (jokeID, tag)," + 
				"FOREIGN KEY (jokeID) REFERENCES joke(jokeID)" + 
				"		ON DELETE NO ACTION  " + 
				"        ON UPDATE CASCADE)";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table joke_tag is created");
		
//		create table user_favorite
		sql = "create table user_favorite(" + 
				"userID int not null," + 
				"type varchar(20)," + 
				"favorite int," + 
				"createdDate datetime default current_timestamp()," + 
				"updatedDate datetime default current_timestamp()," + 
				"primary key (userID, type, favorite)," + 
				"FOREIGN KEY (userID) REFERENCES user(userID)" + 
				"		ON DELETE NO ACTION  " + 
				"        ON UPDATE CASCADE)"; 
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table user_favorite is created");
		
//		create table joke_review
		sql = "create table joke_review(" + 
				"reviewerID int not null," + 
				"jokeID int not null," + 
				"reviewUsername varchar(20) not null," + 
				"score varchar(10) not null," + 
				"remark varchar(1000)," + 
				"createdDate datetime default current_timestamp()," + 
				"updatedDate datetime default current_timestamp()," + 
				"primary key (reviewerID, jokeID)," + 
				"FOREIGN KEY (reviewUsername) REFERENCES user(username) " + 
				"		ON DELETE NO ACTION  " + 
				"        ON UPDATE CASCADE," + 
				"FOREIGN KEY (reviewerID) REFERENCES user(userID) " + 
				"		ON DELETE NO ACTION  " + 
				"        ON UPDATE CASCADE," + 
				"FOREIGN KEY (jokeID) REFERENCES joke(jokeID)" + 
				"		ON DELETE NO ACTION  " + 
				"        ON UPDATE CASCADE)" ;
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table joke_review is created");
		
//		create table blacklist
		sql = "create table blacklist(" + 
				"userID varchar(20) not null," + 
				"createdDate datetime default current_timestamp()," + 
				"PRIMARY key (userID))"; 
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table blacklist is created");
		
//		initialize table joke
		sql = "insert into joke (" + 
				"userID, title, description" + 
				")" + 
				"values" + 
				"(11, 'joke_vzhang', 'this is a normal joke')" + 
				",(3, 'joke1', 'this is a fancy joke')" + 
				",(3, 'joke2', 'this is a very intereasting joke i heard')" + 
				",(4, 'joke3', 'this is a boring joke i heard')" + 
				",(3, 'joke4', 'this is a very intereasting joke i heard')" + 
				",(4, 'joke5', 'this is a boring joke i heard')" + 
				",(2, 'joke6', 'this is a very intereasting joke i heard')" + 
				",(2, 'joke7', 'this is a boring joke i heard')" + 
				",(2, 'joke8', 'this is a very intereasting joke i heard')" + 
				",(5, 'joke9', 'this is a boring joke i heard')" + 
				",(6, 'joke210', 'this is a very intereasting joke i heard')" +
				",(3, 'joke11', 'this is a fancy joke')" + 
				",(3, 'joke12', 'this is a very intereasting joke i heard')";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table joke is populated");

//		initialize table joke_tag
		sql = "insert into joke_tag(" + 
				"jokeID," + 
				"tag" + 
				")" + 
				"values" + 
				"(1, 'XYY')," + 
				"(1, 'X')," + 
				"(1, 'Y')," + 
				"(2, 'X')," + 
				"(2, 'Y')," + 
				"(3, 'X')," + 
				"(3, 'Y')," + 
				"(3, 'XYYY')," + 
				"(3, 'YXY')," + 
				"(4, 'story')," + 
				"(4, 'kids')";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table joke_tag is populated");
		
//		initialize table joke_review
		sql = "insert into sampledb.joke_review(" + 
				"reviewerID," + 
				"reviewUsername," + 
				"jokeid," + 
				"score," + 
				"remark" + 
				")" + 
				"values" + 
				"(2, 'john', 7, 'good', 'XXX')" + 
				",(2, 'john', 3, 'good', 'XXX')" + 
				",(2, 'john', 4, 'good', 'XXX')" + 
				",(2, 'john', 5, 'good', 'XXX')" + 
				",(11, 'vzhang', 2, 'good', 'XXX')" + 
				",(11, 'vzhang', 3, 'good', 'XXX')" + 
				",(11, 'vzhang', 4, 'good', 'XXX')" + 
				",(11, 'vzhang', 5, 'good', 'XXX')" + 
				",(11, 'vzhang', 6, 'good', 'XXX')" + 
				",(2, 'john', 2, 'good', 'XXX')";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table joke_review is populated");
		
//		initialize table user_favorite
		sql = "INSERT INTO `sampledb`.`user_favorite`" + 
				"(`userID`," + 
				"`type`," + 
				"`favorite`)" + 
				"VALUES" + 
				"(11, 'friend', 2)" + 
				",(11, 'friend', 3)" + 
				",(11, 'friend', 4)" + 
				",(11, 'friend', 5)" + 
				",(11, 'friend', 6)" + 
				",(11, 'joke', 4)" + 
				",(11, 'joke', 5)" + 
				",(11, 'joke', 6)" + 
				",(11, 'joke', 7)" + 
				",(11, 'joke', 8)";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table user_favorite is populated");
		
//		initialize table blacklist
		sql = "INSERT INTO `sampledb`.`blacklist`" + 
				"(`userID`)" + 
				"VALUES" + 
				"(3)" + 
				",(4)";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Table blacklist is populated");
		
//		Create triggers
//		create trigger joke_before_insert
		sql = "DELIMITER $ CREATE TRIGGER joke_before_insert BEFORE INSERT ON joke FOR EACH ROW CALL test.sp_check_posts_perDay_joke $ DELIMITER ;";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger joke_before_insert is created");
		
//		create trigger joke_tag_before_insert
		sql = "DELIMITER $\n" + 
				" CREATE TRIGGER `joke_tag_before_insert` BEFORE INSERT ON `joke_tag`\n" + 
				" FOR EACH ROW\n" + 
				"    CALL test.sp_check_single_word_joke_tag(new.tag) $\n" + 
				" DELIMITER";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger joke_tag_before_insert is created");
	
//		create trigger joke_tag_before_update
		sql = "DELIMITER $\n" + 
				" CREATE TRIGGER `joke_tag_before_update` BEFORE UPDATE ON `joke_tag`\n" + 
				" FOR EACH ROW\n" + 
				"    CALL test.sp_check_single_word_joke_tag(new.tag) $\n" + 
				" DELIMITER";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger joke_tag_before_update is created");
		
//		create trigger user_favorite_before_insert
		sql = "DELIMITER $\n" + 
				" CREATE TRIGGER `user_favorite_before_insert` BEFORE INSERT ON `user_favorite`\n" + 
				" FOR EACH ROW\n" + 
				"    CALL test.sp_check_type_user_favorite(new.type) $\n" + 
				" DELIMITER";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger user_favorite_before_insert is created");
		
//		create trigger user_favorite_before_update
		sql = "DELIMITER $\n" + 
				" CREATE TRIGGER `user_favorite_before_update` BEFORE UPDATE ON `user_favorite`\n" + 
				" FOR EACH ROW\n" + 
				"    CALL test.sp_check_type_user_favorite(new.type) $\n" + 
				" DELIMITER";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger user_favorite_before_update is created");
		
//		create trigger joke_review_before_insert
		sql = "DELIMITER $\n" + 
				" CREATE TRIGGER joke_review_before_insert BEFORE INSERT ON joke_review\n" + 
				" FOR EACH ROW\n" + 
				"    CALL test.sp_before_insert_joke_review(new.score, new.jokeID) $\n" + 
				" DELIMITER";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger joke_review_before_insert is created");
		
//		create trigger joke_review_before_update
		sql = "DELIMITER $\n" + 
				"CREATE TRIGGER joke_review_before_update BEFORE UPDATE ON joke_review\n" + 
				"FOR EACH ROW\n" + 
				"    CALL test.sp_before_update_joke_review(new.score, new.reviewUsername) $\n" + 
				"DELIMITER";
		statement = jdbcConnection.createStatement();
		resultSet = statement.executeUpdate(sql);
		System.out.println("Trigger joke_review_before_update is created");
		*/
		statement.close();
//		statementQuery.close();
		resultSetQuery.close();
		disconnect();
		
		System.out.println("InitializeDB is Completed");
	}
}