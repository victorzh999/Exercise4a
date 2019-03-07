
/**
 * Book.java This is a model class represents a book entity
 * 
 * @author www.codejava.net
 *
 */
public class Joke {
	protected int jokeID;
	protected String userID;
	protected String title;
	protected String description;
	protected String createdDate;
	
	public Joke() {
	}
	
	public Joke(String userID, String title, String description) {
		this.userID = userID;
		this.title = title;
		this.description = description;
//		this.jokeID = 1;
//		this.createdDate = null;
	}

	public Joke(String userID, String title, String description, String createdDate) {
		this(userID, title, description);
		this.createdDate = createdDate;
//		this.jokeID = 1;
	}
	
	public Joke(int jokeID, String userID, String title, String description, String createdDate) {
		this(userID, title, description, createdDate);
		this.jokeID = jokeID;
	}
	

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getJokeID() {
		return jokeID;
	}

	public void setJokeID(int jokeID) {
		this.jokeID = jokeID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}