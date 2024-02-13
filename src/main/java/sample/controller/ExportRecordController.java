package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.ArrayList;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import sample.model.Record;
import sample.model.User;

import javax.swing.JOptionPane;

import java.io.File;
import java.sql.SQLException;

import javafx.stage.DirectoryChooser;

public class ExportRecordController {
    private Stage stage;
    private User user;

    private ObservableList<Record> selectedRecords;

    @FXML
    private TextField filepathField;

    @FXML
    private TextField filenameField;

    @FXML
    private Button choose;


    @FXML
    private Button submit;

    @FXML
    private Button cancel;

    @FXML
    private Label message;


    public ExportRecordController(Stage stage, User user, ObservableList<Record> selectedRecords) {
        this.stage = stage;
        this.user = user;
        this.selectedRecords = selectedRecords;

        for (Record r : selectedRecords) {
            System.out.println(r.getRecordId());
        }
    }

    // return true if input values are valid
    public boolean validateInput() {

        //Conditions if the textfields are empty̵

        if (filepathField.getText().isEmpty()) {
            message.setText("Please choose directory!");
            message.setTextFill(Paint.valueOf("red"));
            return false;
        }

        if (filenameField.getText().isEmpty()) {
            message.setText("Please enter filename!");
            message.setTextFill(Paint.valueOf("red"));
            return false;
        }

        return true;
    }

    // show directory path dialog
    public void showDirectoryDialog(ActionEvent event) throws SQLException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);


        if (selectedDirectory != null) {
            filepathField.setText(selectedDirectory.getPath());
        }
    }

    public void clickedOK(ActionEvent event) throws SQLException {
        if (!validateInput()) {
            return;
        }
        //If exporting file is successful
        if (exportFile()) {
            infoBox("", "Export Records", "The records have been successfully exported to" + filepathField.getText() + "/" + filenameField.getText() + ".csv", Alert.AlertType.CONFIRMATION);
        }
        //If exporting file is unsuccessful
        else {
            infoBox("", "Export Records", "An error occured while exporting", Alert.AlertType.ERROR);
        }

    }

    private void infoBox(String infoMessage, String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    // return true if records are written successfully, else false
    public boolean exportFile() {
        PrintWriter writer = null;
        try {
            //Writing the file with the list
            writer = new PrintWriter(filepathField.getText() + "/" + filenameField.getText() + ".csv", "UTF-8");
            writer.println("Date, " + "Weight, " + "Temperature, " + "Low Blood Pressure, " + "High Blood Pressure, " + "Notes");

            for (Record r : selectedRecords) {
                writer.println(r.getDate() + "," + r.getWeight() + "," + r.getTemperature() + "," + r.getLow() + "," + r.getHigh() + "," + r.getNotes());

            }
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
        return false;

    }
//Redirecting to the home page
    public void backToHome(ActionEvent event) throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/home.fxml"));
            HomeController homeController = new HomeController(this.stage, user);
            loader.setController(homeController);
            VBox root = (VBox) loader.load();
            homeController.showStage(root);
            //this.stage.close();

        } catch (IOException var5) {
            var5.printStackTrace();

        }

    }
    //Implementation of the button function on clicking them

    public void initialize() {
        this.choose.setOnAction((event) -> {
            try {
                this.showDirectoryDialog(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.submit.setOnAction((event) -> {
            try {
                this.clickedOK(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.cancel.setOnAction((event) -> {
            try {
                this.backToHome(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void showStage(GridPane root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600.0, 400.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Choose a directory to save a file");
        this.stage.show();
    }
}
