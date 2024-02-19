package gui;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.util.List;

import database.StubDB;
import database.User;

public class LoginController {
	//List<String> UserList = List.of("user1", "user2", "user3");
	//List<String> PasswordList = List.of("password1", "password2", "password3");


	// Users List from STUB DB

	List<User> usersList = StubDB.getUsersList();
	int userIndex = 0;


	protected EventHandler<ActionEvent> onLoginButtonClick(Text actionTarget, TextField userTextField,
			PasswordField pwBox, Stage stage) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String username = userTextField.getText();
				String password = pwBox.getText();


				// Check if username and password match
				for(int i = 0; i < usersList.size();i++) {
					if (username.equals(usersList.get(i).getUserName()) && password.equals(usersList.get(i).getPassword())){
						actionTarget.setFill(javafx.scene.paint.Color.GREEN);
						actionTarget.setText("Login successful");
						// change scene						

						// If User is of type "Customer", open CustomerLandingPage
						// If User is of type "Farmer", open FarmerLandingPage
						if(usersList.get(i).getUserType().equals("Farmer")) {
							FarmerLandingPage farmerLandingPage = new FarmerLandingPage(stage);
							return;
						}
						else if (usersList.get(i).getUserType().equals("Customer")) {
							CustomerLandingPage customerLandingPage = new CustomerLandingPage(stage);
							return;
						}

					}
				}


				actionTarget.setText("Login failed");

			}
		};
	}
	protected ChangeListener<? super String> onUserNameTextChange(Text actionTarget) {
		return (observable, oldValue, newValue) -> {
			actionTarget.setText("");
		};
	}
	protected ChangeListener<? super String> onPasswordTextChange(Text actionTarget) {
		return (observable, oldValue, newValue) -> {
			actionTarget.setText("");
		};
	}

	// Event Handler for Register Button
	protected EventHandler<ActionEvent> onRegisterButtonClick(Stage stage) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				 RegistrationPageView registrationPage = new RegistrationPageView(stage);
	
			}

		};

	}

}
