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
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.DBcon;
import sample.model.Record;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditRecordController implements Initializable {
    private Stage stage;
    private User user;

    private Record selectedRecord;

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
    private Label message;

// Constructor for the edit record controller
    public EditRecordController(Stage stage, User user, Record record) {
        this.stage = stage;
        this.user = user;
        this.selectedRecord = record;
        System.out.println(selectedRecord.getDate());


    }
    public void editedRecord(ActionEvent event) throws SQLException {
        Window createNewRecordWindow = submit.getScene().getWindow();

        String date = String.valueOf(dateField.getValue());
        String weight = weightField.getText();
        String temperature = temperatureField.getText();
        String low = lowField.getText();
        String high = highField.getText();
        String notes = notesArea.getText();
// Checking conditions if the textfields are empty
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
        selectedRecord.setDate(date);
        selectedRecord.setWeight(weight);
        selectedRecord.setTemperature(temperature);
        selectedRecord.setLow(low);
        selectedRecord.setHigh(high);
        selectedRecord.setNotes(notes);

        //Editing the selected record in the database
        DBcon db = new DBcon();
        //selectedRecord.getRecordId();
        db.editedRecord(selectedRecord);
        infoBox("", "Edit a Record", "Your record has been updated!", Alert.AlertType.INFORMATION);


    }
    //Redirecting to the home page
    public void backToHome() {
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
    //Structure for the infobox
    private void infoBox(String infoMessage, String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(headerText);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> backToHome());
    }

    private void showAlert(Alert.AlertType error, Window createNewRecordWindow, String s, String please_enter_your_date) {
    }


//̵Setting the text that the user has input
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateField.setValue(LocalDate.parse(selectedRecord.getDate()));
        weightField.setText(selectedRecord.getWeight());
        temperatureField.setText(selectedRecord.getTemperature());
        lowField.setText(selectedRecord.getLow());
        highField.setText(selectedRecord.getHigh());
        notesArea.setText(selectedRecord.getNotes());

        this.submit.setOnAction((event) -> {
            try {
                this.editedRecord(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public void showStage(GridPane root) {


        Scene scene = new Scene(root, 600.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.setTitle("Edit a record");
        this.stage.show();
    }
}



