package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.scene.control.TextArea;
import sample.DBcon;
import sample.model.Record;
import sample.model.User;

import java.io.IOException;
import java.sql.SQLException;


public class NewRecordController {
    @FXML
    private DatePicker dateField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField temperatureField;

    @FXML
    private TextField lowField;

    @FXML
    private TextField highField;

    @FXML
    private TextArea notesArea;

    @FXML
    private Button submit;

    @FXML
    private Button cancel;
    private Stage stage;

    private User user;

    private Record record;

    //Constructor for newrecord controller
    public NewRecordController(Stage stage, User user, Record record) {
        this.stage = stage;
        this.user = user;
        this.record = record;
    }


    //Implementation of function to direct to the home page
    public void BackToHome(ActionEvent event) throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/home.fxml"));
            HomeController homeController = new HomeController(this.stage, user);
            loader.setController(homeController);
            VBox root = (VBox) loader.load();
            homeController.showStage(root);

        } catch (IOException var5) {
            var5.printStackTrace();

        }

    }

    //Implementation of the function for creating new record
    public void createNewRecord(ActionEvent event) throws SQLException {
        Window createNewRecordWindow = submit.getScene().getWindow();

        String date = String.valueOf(dateField.getValue());
        String weight = weightField.getText();
        String temperature = temperatureField.getText();
        String low = lowField.getText();
        String high = highField.getText();
        String notes = notesArea.getText();
//Conditions if any textfield is empty
        if (dateField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, createNewRecordWindow, "Form Error!",
                    "Please enter your date");
            return;
        }

        if (weightField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createNewRecordWindow, "Form Error!",
                    "Please enter your weight!");
            return;
        }
        if (temperatureField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createNewRecordWindow, "Form Error!",
                    "Please enter your temperature!");
            return;
        }
        if (lowField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createNewRecordWindow, "Form Error!",
                    "Please enter your low blood pressure!");
            return;
        }
        if (highField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createNewRecordWindow, "Form Error!",
                    "Please enter a high blood pressure!");
            return;
        }
        if (notesArea.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, createNewRecordWindow, "Form Error!",
                    "Please enter a high blood pressure!");
            return;
        }
        DBcon db = new DBcon();

        user.getId();
        //Loading data to the database the entered record data
        Record record = db.createRecord(user.getId(), date, weight, temperature, low, high, notes);

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/allrecords.fxml"));
            AllRecordsController allRecordsController = new AllRecordsController(this.stage, user, record);
            loader.setController(allRecordsController);
            GridPane root = (GridPane) loader.load();
            allRecordsController.showStage(root);

        } catch (IOException var5) {
            var5.printStackTrace();

        }

    }

    private void showAlert(Alert.AlertType error, Window createNewRecordWindow, String s, String s1) {
    }

    public void initialize() {
        this.submit.setOnAction((event) -> {
            try {
                this.createNewRecord(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.cancel.setOnAction((event) -> {
            try {
                this.BackToHome(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void showStage(GridPane root) {
        Scene scene = new Scene(root, 800.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Create a new health record");
        this.stage.show();
    }
}
