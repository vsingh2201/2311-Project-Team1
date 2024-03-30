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

public class UpdateProfilePage {

	private RegistrationController registrationController;

	public UpdateProfilePage(Stage stage,Scene previousScene) {

		this.registrationController = RegistrationController.getInstance(DbConfig.IS_MOCK);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(25);
		grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Farmers Hub - Update Profile");
		sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 1, 0, 2, 1);
		
		Label currentUserName = new Label("Current Username:");
		grid.add(currentUserName, 0, 1);
		
		Label currentPassword = new Label("Current Password:");
		grid.add(currentPassword, 0, 2);
		
		Label newUserName = new Label("New Username:");
		grid.add(newUserName, 0, 3);
		
		Label newPassword = new Label("New Password:");
		grid.add(newPassword, 0, 4);
		
		
		TextField currentNameField = new TextField();
		grid.add(currentNameField, 1, 1, 2,1);
		
		PasswordField currentPwdField = new PasswordField();
		grid.add(currentPwdField, 1, 2,2,1);
		
		TextField newNameField = new TextField();
		grid.add(newNameField, 1, 3,2,1);
			
		PasswordField newPwdField = new PasswordField();
		grid.add(newPwdField, 1, 4, 2, 1);
		
		
		Button backBtn = new Button("Back");
		backBtn.getStyleClass().setAll("btn","btn-primary");
		Button updateBtn = new Button("Update");
		updateBtn.getStyleClass().setAll("btn","btn-success");
		
		
		
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(updateBtn);
		hbBtn.getChildren().add(backBtn);
		grid.add(hbBtn, 1, 5);
		
		
		Text actionTarget = new Text();
		grid.add(actionTarget, 1, 7);
		actionTarget.getStyleClass().setAll("em", "text-danger", "h4");
		

		// ADD EVENT HANDLER AND LISTENERS
		// Event Handler for Buttons
		
		backBtn.setOnAction(e->stage.setScene(previousScene));

		//registerBtn.setOnAction(registrationController.onRegisterButtonClick(fnTextField, lnTextField, unTextField, pwBox, actionTarget, stage));
		
		updateBtn.setOnAction(registrationController.onUpdateButtonClick(currentNameField, currentPwdField, newNameField, newPwdField, actionTarget, stage));
		
		Scene scene = new Scene(grid, 600, 600);
		
		scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
		stage.setScene(scene);
		stage.show();
	}
}
