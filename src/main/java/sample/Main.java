package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import sample.controller.LoginController;


public class Main extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            // Customize controller instance
            //login page direction
            LoginController loginController = new LoginController(stage);
            loader.setController(loginController);
            GridPane root = (GridPane)loader.load();
            loginController.showStage(root);
//            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//            stage.setTitle("User Login");
//            stage.setScene(new Scene(root, 800, 500));
//            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }

    }

