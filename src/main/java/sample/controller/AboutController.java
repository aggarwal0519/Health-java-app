package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.User;

import java.io.IOException;
import java.sql.SQLException;

public class AboutController {

    private Stage stage;
    private User user;
    @FXML
    private Button submit;

    public AboutController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void backToHome(ActionEvent event) throws SQLException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            HomeController homeController = new HomeController(this.stage,user);
            loader.setController(homeController);
            VBox root = (VBox)loader.load();
            homeController.showStage(root);
        }
        catch (IOException e) {
            e.printStackTrace();
            //  message.setText(e.getMessage());
        }
    }

    public void initialize(){
        this.submit.setOnAction((event) -> {
            try {
                this.backToHome(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void showStage(AnchorPane  root) {
        Scene scene = new Scene(root,600.0, 400.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("MyHealth");
        this.stage.show();
    }
}
