package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;


public class ViewProfileController implements Initializable{

    private Stage stage;
    private User user;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Button okay;


    public ViewProfileController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;


    }
    //redirecting to the home page
    public void backToHome(ActionEvent event) throws SQLException {
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


// Setting the information of the user logged in
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getUsername());
        firstnameLabel.setText(user.getFirstname());
        lastnameLabel.setText(user.getLastname());

        this.okay.setOnAction(event->{
            try {
                this.backToHome(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }
    public void showStage(GridPane root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root, 500.0, 300.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("View my profile");
        this.stage.show();
    }
}
