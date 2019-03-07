
public class UserFavoriteJoke {

	private String userID;
	private int jokeID;
	private String title;

	public UserFavoriteJoke() {
	}

	public UserFavoriteJoke(String userID, int jokeID, String title) {
		this.userID = userID;
		this.jokeID = jokeID;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getJokeID() {
		return jokeID;
	}

	public void setJokeID(int jokeID) {
		this.jokeID = jokeID;
	}
	
	
}
