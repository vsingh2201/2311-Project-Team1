package database;

import java.util.ArrayList;
import java.util.List;

// This is a STUB Database
// User Database
// Produce Listings Database
// Machine Listings Database

public class StubDB {
	
	// List of Users
	private static List<User> usersList = new ArrayList<User>();
	
	
	// Getters and Setters for usersList
	public static List<User> getUsersList() {
		return usersList;
	}

	public static void setUsersList(List<User> usersList) {
		StubDB.usersList = usersList;
	}
	
	public static void intializeUserDB() {
		StubDB.usersList = new ArrayList<User>();
		User u1 = new User("John","Smith","Farmer","john123","password1");
		User u2 = new User("Alice","Johnson","Customer","alice456","password2");
		User u3 = new User("Bob","Williams","Customer","bob789","password3");
		usersList.add(u1);
		usersList.add(u2);
		usersList.add(u3);
		
		
	}
	
	
	

}
