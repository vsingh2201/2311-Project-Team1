package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import controllers.LoginController;



public class LoginView {
	static LoginController loginController;
	
	public LoginView(LoginController loginController) {
		LoginView.loginController = loginController;
	}

	public void start(Stage stage) {
		
		stage.setTitle("Farmers Hub App");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(25);
		grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Welcome to Farmers Hub App");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1, 2,1);
		
		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2, 2, 1);
		
		Button loginBtn = new Button("Login");
		loginBtn.getStyleClass().setAll("btn","btn-success");
		Button registerBtn = new Button("Register");
		registerBtn.getStyleClass().setAll("btn","btn-danger");
		
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(loginBtn);
		hbBtn.getChildren().add(registerBtn);
		grid.add(hbBtn, 1, 4);
		
		
		Text actionTarget = new Text();
		grid.add(actionTarget, 1, 6);
		actionTarget.getStyleClass().setAll("em", "text-danger", "h3");
		
		
		// ADD EVENT HANDLER AND LISTENERS
		loginBtn.setOnAction(loginController.onLoginButtonClick(actionTarget, userTextField, pwBox, stage));
		
		registerBtn.setOnAction(loginController.onRegisterButtonClick(stage));
		
		userTextField.textProperty().addListener(loginController.onUserNameTextChange(actionTarget));
		
		pwBox.textProperty().addListener(loginController.onPasswordTextChange(actionTarget));
		
		Scene scene = new Scene(grid, 500, 450);
		
		scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
		stage.setScene(scene);
		stage.show();
	}
}