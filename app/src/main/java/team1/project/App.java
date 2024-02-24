package team1.project;



import javafx.stage.Stage;
import statics.DbConfig;
import utils.DatabaseSeedingUtil;
import views.LoginView;

import java.io.IOException;

import controllers.LoginController;

public class App extends javafx.application.Application {



	static LoginController loginController = LoginController.getInstance(DbConfig.IS_MOCK);

    @Override
    public void start(Stage primaryStage) throws IOException {
    
        System.out.println("ApplicationRunner.java Running....");

        if(!DbConfig.IS_MOCK){
            DatabaseSeedingUtil db = new DatabaseSeedingUtil();

            db.seedDatabase();
        }

        // Start the first Login View
        LoginView login1 = new LoginView(loginController);
        login1.start(new Stage()); // Use a new stage for each login view

        // Start the second Login View
        LoginView login2 = new LoginView(loginController);
        login2.start(new Stage()); // Use another new stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}