
public class FriendProfile {

	private String friendID;
	private String firstName;
	private String lastName;
	private String postedJoke;
	private String postedDate;

	public FriendProfile() {
	}

	public FriendProfile(String friendID, String firstName, String lastName, String postedJoke, String postedDate) {
		this.friendID = friendID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.postedJoke = postedJoke;
		this.postedDate = postedDate;
	}

	public String getFriendID() {
		return friendID;
	}

	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostedJoke() {
		return postedJoke;
	}

	public void setPostedJoke(String postedJoke) {
		this.postedJoke = postedJoke;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	
}
