package services;

import javafx.scene.text.Text;
import models.Customer;
import models.Farmer;
import models.User;
import repositories.userRepository.IUserRepository;
import statics.UserRoles;

public class UserService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    public User handleGetUserById(int id) {
        return userRepository.getUserById(id);
    }

    public boolean handleRegisterUser(String firstName, String lastName, String userName, String password, String role, Text actionTarget) {
        String validationMessage = validateUserRegistration(firstName, lastName, userName, password, role);

        if (!validationMessage.isEmpty()) {

            actionTarget.setText(validationMessage);

            return false; 
        }

        User newUser = null;

        if(role.toUpperCase().equals(UserRoles.FARMER)) {
            newUser = new Farmer(firstName, lastName, userName, password);
        }
        else {
            newUser = new Customer(firstName, lastName, userName, password);
        }

        User user = userRepository.getUserByUsername(userName);

        if (user != null) {
            actionTarget.setText("User already exists");
            return false;
        }

        userRepository.createUser(newUser);

        actionTarget.setText("Registration Successful");	
        
        return true;
    }
    // Method for updating user profile (changing password only)
    public boolean handleUpdateUser(String oldUserName, String oldPassword, String newPassword) {
    	User user = userRepository.validateUser(oldUserName, oldPassword);
    	
    	if (user == null) {
    		// Old username or password is invalid
    		return false;
    	}
    	
    	// Update password with new values
  
        user.setPassword(newPassword);
        
        userRepository.updateUserProfile(user);
        
        
        return true;
    }
    
    public User handleUserLogin(String username, String password) {
        return userRepository.validateUser(username, password);
    }

    private String validateUserRegistration(String firstName, String lastName, String userName, String password, String role) {
        StringBuilder missingFields = new StringBuilder("Missing fields: ");

        if (firstName == null || firstName.trim().isEmpty()) missingFields.append("\nFirst Name, ");

        if (lastName == null || lastName.trim().isEmpty()) missingFields.append("\nLast Name, ");

        if (userName == null || userName.trim().isEmpty()) missingFields.append("\nUser Name, ");

        if (password == null || password.trim().isEmpty()) missingFields.append("\nPassword, ");

        if (role == null || role.trim().isEmpty()) missingFields.append("\nRole, ");
        

        if (missingFields.length() > "Missing fields: ".length()) {

            missingFields.setLength(missingFields.length() - 2);

            return missingFields.toString();

        } else {
            // Return empty string if no fields are missing
            return "";
        }
    }


}