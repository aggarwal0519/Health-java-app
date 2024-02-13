package sample.controller;

import java.io.IOException;
import java.sql.SQLException;

import sample.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.DBcon;
import javafx.scene.control.Label;

public class LoginController {
    //specifying the fields from the fxml file
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private Label message;

    private Stage stage;
   

    public LoginController(Stage stage) {
        this.stage = stage;
    }


    public void login(ActionEvent event) throws SQLException{
        Window loginWindow = loginButton.getScene().getWindow();
        //Retrieving text from the text fields of the login page
        String username = usernameField.getText();
        String password = passwordField.getText();
//Condition if the username is empty
        if(usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, loginWindow, "Form Error!",
                    "Please enter your username");
            return;
        }
//̵Condition if the password is empty
        if (passwordField.getText().isEmpty()) {
        showAlert(Alert.AlertType.ERROR,loginWindow, "Form Error!",
                "Please enter a password");
        return;
    }
        //Linking to the database
        DBcon db = new DBcon();
        User user = db.validate(username,password);
        //If the username or password doesn't match the values in the database
        if(user == null){
            message.setText("Please enter correct username and password");
        }
        //Condition if the login is successful
        else{
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

            infoBox("Login Successful!"," ","Congratulations!");

        }

    }

//Structure for the infobox
    private void infoBox(String infoMessage,String title,String headerText) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        //alert.showAndWait();
    }

    private void showAlert(AlertType error, Window loginWindow, String s, String please_enter_a_password) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(null);
        alert.setContentText(please_enter_a_password);
        alert.initOwner(loginWindow);
        alert.show();
    }
//Redirecting to aignup page through login
    public void signup(ActionEvent event) throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/signUp.fxml"));

            // Customize controller instance
            SignUpController signupController = new SignUpController(this.stage);
            loader.setController(signupController);
            VBox root = (VBox)loader.load();
            signupController.showStage(root);
            //this.stage.close();

        } catch (IOException var5) {
            var5.printStackTrace();
        }
//
//        Parent root = null;
//        try {
//            root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        stage.setTitle("SignUp");
//        stage.setScene(new Scene(root, 800, 500));
//        stage.show();

    }
    public void showStage(GridPane root){
        Scene scene = new Scene(root, 700.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Login");
        this.stage.show();
    }
    @FXML
    public void initialize() {
        //actions performed on clicking the login button
        this.loginButton.setOnAction((event) -> {
            try {
                this.login(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        //actions performed on clicking the signup button

        this.signupButton.setOnAction((event) -> {
            try{
            this.signup(event);
            }
             catch(SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }


    }



