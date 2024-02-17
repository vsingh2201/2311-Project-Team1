package team1.project;


import javafx.stage.Stage;
import java.io.IOException;

import gui.LoginController;
import gui.LoginView;
public class App extends javafx.application.Application {
 static LoginController loginController = new LoginController();
 @Override
 public void start(Stage stage) throws IOException {
 LoginView login = new LoginView(loginController);
 login.start(stage);
 }
 public static void main(String[] args) {
 launch();
 }
}