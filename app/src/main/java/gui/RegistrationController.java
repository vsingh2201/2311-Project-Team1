package gui;

import database.StubDB;
import database.User;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegistrationController {

	// Add Event Handlers for the Registration Page View
	// Store data from Registration Page into the Users List in StubDB
	
	
	
	
	// Event Handler for Back to Login Button
		protected static EventHandler<ActionEvent> onBackToLoginButtonClick(Stage stage) {
			return new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// Go Back to Login Page
					LoginView loginView = new LoginView(new LoginController());
		            loginView.start(stage);
		
				}

			};

		}
		
		// Event Handler for Register Button
		protected static EventHandler<ActionEvent> onRegisterButtonClick(User oneUser, Text actionTarget,Stage stage){
			return new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
					// Add User to the usersList in StubDB
					StubDB.addUser(oneUser);
					actionTarget.setText("Registration Successful");
					
					// Go Back to Login Page after 3 seconds

			        PauseTransition delay = new PauseTransition(Duration.seconds(3));
			        delay.setOnFinished(e -> {
			            // Go back to Login Page after delay
			            LoginView loginView = new LoginView(new LoginController());
			            loginView.start(stage);
			        });
			        delay.play();
					
				}		
				
			};
		}
		
		// Event Handler for First Name Text Field
		protected static ChangeListener<? super String> onFirstNameTextChange(User oneUser) {
			return (observable, oldValue, newValue) -> {
				oneUser.setFirstName(newValue);
			};
		}
	
		
		// Event Handler for Last Name Text Field
		protected static ChangeListener<? super String> onLastNameTextChange(User oneUser) {
			return (observable, oldValue, newValue) -> {
				oneUser.setLastName(newValue);
			};
		}
		
		// Event Handler for User Name Text Field
		protected static ChangeListener<? super String> onUserNameTextChange(User oneUser) {
			return (observable, oldValue, newValue) -> {
				oneUser.setUserName(newValue);
			
			};
		}
		
		// Event Handler for Password Text Field
		protected static ChangeListener<? super String> onPasswordTextChange(User oneUser) {
			return (observable, oldValue, newValue) -> {
				oneUser.setPassword(newValue);
			};
		}

		
		// Event Handler for User Type Choice Box
		protected static EventHandler<ActionEvent> onUserTypeSelection(User oneUser, ChoiceBox<String> userTypeChoiceBox) {
		    return event -> {
		        String selectedUserType = userTypeChoiceBox.getValue();
		        oneUser.setUserType(selectedUserType);
		    };
		}

		
}
