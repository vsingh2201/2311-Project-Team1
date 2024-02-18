package database;

public class User {
	private String firstName;
	private String lastName;
	private String userType; // Can be "Farmer" or "Customer"
	private String userName;
	private String password;
	
	// Constructor
	public User(String firstName, String lastName, String userType, String userName, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.userName = userName;
		this.password = password;
	}

	// Getters and Setters
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
	

}
