package views;

import org.kordamp.bootstrapfx.BootstrapFX;

import controllers.RegistrationController;
import javafx.geometry.Pos;
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
import statics.DbConfig;
import statics.UserRoles;
import utils.StringUtils;

public class RegistrationPageView {

	private RegistrationController registrationController;

	public RegistrationPageView(Stage stage) {

		this.registrationController = RegistrationController.getInstance(DbConfig.IS_MOCK);

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
		cb.getItems().addAll(StringUtils.capitalize(UserRoles.FARMER), StringUtils.capitalize(UserRoles.CUSTOMER.toLowerCase()));
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
		actionTarget.getStyleClass().setAll("em", "text-danger", "h4");
		

		// ADD EVENT HANDLER AND LISTENERS
		// Event Handler for Buttons
		loginBtn.setOnAction(registrationController.onBackToLoginButtonClick(stage));

		registerBtn.setOnAction(registrationController.onRegisterButtonClick(fnTextField, lnTextField, unTextField, pwBox, cb, actionTarget, stage));
		
		
		Scene scene = new Scene(grid, 600, 600);
		
		scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
		stage.setScene(scene);
		stage.show();
	}
}