
public class User {

	private String userID;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int age;
	private String gender;
	private Boolean isRoot;
	private Boolean isBlacklist;
	
	public User() {
	}

	public User(String userID, String password, String firstName, String lastName, String email) {
		this.userID = userID;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public User(String userID, String password, String firstName, String lastName, String email, String gender, int age, Boolean isRoot, Boolean isBlacklist) {
		this(userID, password, firstName, lastName, email);
		this.age = age;
		this.gender = gender;
		this.isRoot = isRoot;
		this.isBlacklist = isBlacklist;		
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	public Boolean getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(Boolean isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

}
