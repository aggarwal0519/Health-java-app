package sample.controller;

import java.io.IOException;
import java.sql.SQLException;

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
import javafx.scene.control.Label;
import javafx.stage.Window;
import javafx.stage.Stage;
import sample.DBcon;
import sample.model.User;

public class SignUpController {
    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createUserButton;

    @FXML
    private Label status;

    private Stage stage;


    public SignUpController(Stage stage) {
        this.stage = stage;
    }

       @FXML
    public void initialize() {
        createUserButton.setOnAction((event) -> {
            try {
                this.signUp(event);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        loginButton.setOnAction((event) ->{
            try{
                this.login(event);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void login(ActionEvent event) throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/login.fxml"));
            LoginController loginController = new LoginController(this.stage);
            loader.setController(loginController);
            GridPane root = (GridPane) loader.load();
            loginController.showStage(root);
            //this.stage.close();

        } catch (IOException var5) {
            var5.printStackTrace();

        }

    }
    public void signUp(ActionEvent event) throws SQLException {
        Window createUserWindow = createUserButton.getScene().getWindow();

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
    //condition if the firstname is empty
        if (firstnameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createUserWindow, "Form Error!",
                    "Please enter your first name!");
            return;
        }
    //condition if the lastname is empty
        if (lastnameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createUserWindow, "Form Error!",
                    "Please enter your last name!");
            return;
        }
    //condition if the username is empty

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createUserWindow, "Form Error!",
                    "Please enter your username");
            return;
        }
    //condition if the password is empty
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createUserWindow, "Form Error!",
                    "Please enter a password");
            return;
        }
    //linking to the database to insert the data in database
        DBcon db = new DBcon();
        User user = db.createUser(firstname,lastname, username, password);

        infoBox("Your account has been successfully created!"," ","Congratulations!");
    }


//Structure of the dialog box
    private void infoBox(String s, String s1, String s2) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(s);
        alert.setTitle(s1);
        alert.setHeaderText(s2);
        alert.showAndWait();
    }

    private void showAlert(AlertType error, Window createUserWindow, String s, String message) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(createUserWindow);
        alert.show();
    }

//Entering into a new stage
    public void showStage(Pane root) {
        Scene scene = new Scene(root, 700.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Sign up");
        this.stage.show();
    }
}
