package controllers;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Customer;
import models.Farmer;
import models.User;
import repositories.userRepository.MockUserRepository;
import repositories.userRepository.UserRepository;
import services.UserService;
import views.RegistrationPageView;
import views.customerViews.CustomerLandingPage;
import views.farmerViews.FarmerLandingPage;



public class LoginController {
	
	private static LoginController instance;
	private UserService userService;

	public LoginController(boolean isMock) {
		if(isMock) {
			this.userService = new UserService(new MockUserRepository());
		} else {
			this.userService = new UserService(new UserRepository());
		}
	}

	public static LoginController getInstance(boolean isMock) {
		if (instance == null) {
			instance = new LoginController(isMock);
		}
		return instance;
	}


	public EventHandler<ActionEvent> onLoginButtonClick(Text actionTarget, TextField userTextField,
			PasswordField pwBox, Stage stage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String username = userTextField.getText();

				String password = pwBox.getText();

				User user = userService.handleUserLogin(username, password);

				boolean isSuccessfulLogin = user != null;


				if (isSuccessfulLogin){
					actionTarget.setFill(javafx.scene.paint.Color.GREEN);
					actionTarget.setText("Login successful");

					if(user instanceof Farmer) {
						new FarmerLandingPage(stage, user);
		
						return;
					}
					else if (user instanceof Customer) {
						 new CustomerLandingPage(stage, user);

						return;
					}

				}	
				actionTarget.setText("Login failed");
			}
		};
	}
	
	public ChangeListener<? super String> onUserNameTextChange(Text actionTarget) {
		return (observable, oldValue, newValue) -> {
			actionTarget.setText("");
		};
	}
	public ChangeListener<? super String> onPasswordTextChange(Text actionTarget) {
		return (observable, oldValue, newValue) -> {
			actionTarget.setText("");
		};
	}

	// Event Handler for Register Button
	public EventHandler<ActionEvent> onRegisterButtonClick(Stage stage) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				 new RegistrationPageView(stage);
			}

		};

	}

	public User getUserById(int userId) {
		return userService.handleGetUserById(userId);
	}	

}