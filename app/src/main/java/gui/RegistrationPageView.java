package gui;

import org.kordamp.bootstrapfx.BootstrapFX;

import database.User;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationPageView {
	public RegistrationPageView(Stage stage) {
		stage.setTitle("Farmers Hub - Registration");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(25);
		grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Register for Farmers Hub App");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);
		
		Label firstName = new Label("First Name:");
		grid.add(firstName, 0, 1);
		
		Label lastName = new Label("Last Name:");
		grid.add(lastName, 0, 2);
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 3);
		
		Label pw = new Label("Password:");
		grid.add(pw, 0, 4);
		
		Label userType = new Label("User Type:");
		grid.add(userType, 0, 5);
		
		TextField fnTextField = new TextField();
		grid.add(fnTextField, 1, 1, 2,1);
		
		TextField lnTextField = new TextField();
		grid.add(lnTextField, 1, 2,2,1);
		
		TextField unTextField = new TextField();
		grid.add(unTextField, 1, 3,2,1);
			
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 4, 2, 1);
		
		ChoiceBox<String> cb = new ChoiceBox<String>();
		cb.getItems().addAll("Farmer","Customer");
		grid.add(cb, 1,5,2,1);
		
		Button loginBtn = new Button("Back to Login");
		loginBtn.getStyleClass().setAll("btn","btn-primary");
		Button registerBtn = new Button("Register");
		registerBtn.getStyleClass().setAll("btn","btn-success");
		
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(registerBtn);
		hbBtn.getChildren().add(loginBtn);
		grid.add(hbBtn, 1, 7);
		
		
		Text actionTarget = new Text();
		grid.add(actionTarget, 1, 8);
		actionTarget.getStyleClass().setAll("em", "text-danger", "h3");
		
		User oneUser = new User();
		
		
		// ADD EVENT HANDLER AND LISTENERS
		// Event Handler for Buttons
		loginBtn.setOnAction(RegistrationController.onBackToLoginButtonClick(stage));
		registerBtn.setOnAction(RegistrationController.onRegisterButtonClick(oneUser, actionTarget,stage));
		
		
		// Event Handler for TextFields
		fnTextField.textProperty().addListener(RegistrationController.onFirstNameTextChange(oneUser));
		lnTextField.textProperty().addListener(RegistrationController.onLastNameTextChange(oneUser));
		unTextField.textProperty().addListener(RegistrationController.onUserNameTextChange(oneUser));
		pwBox.textProperty().addListener(RegistrationController.onPasswordTextChange(oneUser));
		
		// Event handler for the ChoiceBox
		cb.setOnAction(RegistrationController.onUserTypeSelection(oneUser, cb));
		
		
		Scene scene = new Scene(grid, 500, 450);
		
		scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
		stage.setScene(scene);
		stage.show();
	}
}
