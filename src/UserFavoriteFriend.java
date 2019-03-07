
public class UserFavoriteFriend {

	private String userID;
	private String friendID;

	public UserFavoriteFriend() {
	}

	public UserFavoriteFriend(String userID, String friendID) {
		this.userID = userID;
		this.friendID = friendID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFriendID() {
		return friendID;
	}

	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}
	
	
}
