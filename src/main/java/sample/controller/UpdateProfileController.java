package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.DBcon;
import sample.model.User;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateProfileController implements Initializable {
    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;
    @FXML
    private Label usernameLabel;

    @FXML
    private Label message;

    @FXML
    private Button submit;

    @FXML
    private Button cancel;



    private Stage stage;
    private User user;

    public UpdateProfileController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }




    public void updateProfile(ActionEvent event) throws SQLException{
        Window updatedWindow = submit.getScene().getWindow();

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();

    //Checking if the fields are empty
        if (firstnameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, updatedWindow, "Form Error!",
                    "Please enter your first name!");
            return;
        }
        if (lastnameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, updatedWindow, "Form Error!",
                    "Please enter your last name!");
            return;
        }
        user.setFirstname(firstname);
        user.setLastname(lastname);

        //Updating User information in the databse
        DBcon db = new DBcon();
        db.updatedProfile(user);

        message.setText("The profile has been updated!");
        message.setTextFill(Color.web("green"));
    }
    //Redirecting to the home page
    public void backToHome(ActionEvent event) throws SQLException{

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            HomeController homeController = new HomeController(this.stage, user);
            loader.setController(homeController);
            VBox root = (VBox) loader.load();
            homeController.showStage(root);
        } catch (
                IOException e) {
            e.printStackTrace();
            //  message.setText(e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType error, Window createUserWindow, String s, String s1) {
    }

    //
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        usernameLabel.setText(user.getUsername());

    //Implementation of the buttons that are clicked
        this.submit.setOnAction(event->{
            try {
                this.updateProfile(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
        this.cancel.setOnAction(event->{
            try {
                this.backToHome(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }



    public void showStage(GridPane root) {
        Scene scene = new Scene(root, 700.0, 300.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Update profile");
        this.stage.show();
    }
}
