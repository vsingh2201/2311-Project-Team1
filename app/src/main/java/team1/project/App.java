package team1.project;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.kordamp.bootstrapfx.BootstrapFX;

public class App extends Application {

    @Override
    public void start(Stage stage) {
    	stage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 3);

        grid.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput, loginButton, registerButton);

        loginButton.setOnAction(e -> {
            // Add your login functionality here
            // For demonstration purposes, let's just print the entered username and password
            System.out.println("Username: " + nameInput.getText());
            System.out.println("Password: " + passwordInput.getText());
        });

        registerButton.setOnAction(e -> {
            // Add your registration functionality here
            System.out.println("Registration button clicked");
        });

        Scene scene = new Scene(grid, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}