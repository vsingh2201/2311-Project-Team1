package team1.project;


import javafx.stage.Stage;
import java.io.IOException;

import database.StubDB;
import gui.LoginController;
import gui.LoginView;
public class App extends javafx.application.Application {
	static LoginController loginController = new LoginController();
	@Override
	public void start(Stage stage) throws IOException {
		LoginView login = new LoginView(loginController);
		// Initialize the User Database
		StubDB.intializeUserDB();
		// Start the Login View
		login.start(stage);
	}
	public static void main(String[] args) {
		launch();
	}
}