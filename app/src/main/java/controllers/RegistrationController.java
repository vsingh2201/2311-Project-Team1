package controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import repositories.userRepository.MockUserRepository;
import repositories.userRepository.UserRepository;
import services.UserService;
import views.LoginView;

public class RegistrationController {

	private static RegistrationController instance;
	private UserService userService;
	private boolean isMock;

	public RegistrationController(boolean isMock) {
		if(isMock) {
			this.userService = new UserService(new MockUserRepository());
		} else {
			this.userService = new UserService(new UserRepository());
		}
		this.isMock = isMock;
	}

	public static RegistrationController getInstance(boolean isMock) {
		if (instance == null) {
			instance = new RegistrationController(isMock);
		}
		return instance;
	}

	// Event Handler for Back to Login Button
	  public EventHandler<ActionEvent> onBackToLoginButtonClick(Stage stage) {
			return new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					LoginView loginView = new LoginView(LoginController.getInstance(isMock));
		            loginView.start(stage);
		
				}

			};

		}
		
		// Event Handler for Register Button
		public EventHandler<ActionEvent> onRegisterButtonClick(TextField fnTextField, TextField lnTextField, TextField unTextField, PasswordField pwBox, ChoiceBox<String> cb, Text actionTarget, Stage stage) {

			return new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {
					String firstName = fnTextField.getText();
					String lastName = lnTextField.getText();
					String userName = unTextField.getText();
					String password = pwBox.getText();
					String role = cb.getValue();

					boolean isValid = userService.handleRegisterUser(firstName, lastName, userName, password, role, actionTarget);

					if(!isValid) {
						return;
					}

					PauseTransition delay = new PauseTransition(Duration.seconds(0.3));

					delay.setOnFinished(e -> {
						LoginView loginView = new LoginView(LoginController.getInstance(isMock));
						loginView.start(stage);
					});

					delay.play();
				}       
			};

		}


		
}