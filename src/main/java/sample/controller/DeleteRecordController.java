package sample.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DBcon;
import sample.model.Record;
import sample.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class DeleteRecordController {
    private Stage stage;
    private User user;
    private Record record;
    private ArrayList<Record> allRecords;
    private TableView<Record> table;
    private Label message;

    public DeleteRecordController(Stage stage, User user, Record record) {
        this.stage = stage;
        this.user = user;

        DBcon db = new DBcon();

            allRecords = db.viewAllRecords(user.getId());

            //infoBox("", "Delete a Record", "No records found to be deleted", Alert.AlertType.ERROR);


       // allRecords = db.viewAllRecords(user.getId());



    }
    public TableView<Record> setupTable() {

        //Setting the tableview

        ObservableList<Record> recordList = FXCollections.observableArrayList(allRecords);

        System.out.println(recordList.get(0).getHigh());
        table = new TableView<>();

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn weightCol = new TableColumn("Weight");
        weightCol.setMinWidth(100);
        weightCol.setCellValueFactory(
                new PropertyValueFactory<>("weight"));

        TableColumn temperatureCol = new TableColumn("Temperature");
        temperatureCol.setMinWidth(100);
        temperatureCol.setCellValueFactory(
                new PropertyValueFactory<>("high"));

        TableColumn lowCol = new TableColumn("Low");
        lowCol.setMinWidth(100);
        lowCol.setCellValueFactory(
                new PropertyValueFactory<>("low"));

        TableColumn highCol = new TableColumn("High");
        highCol.setMinWidth(100);
        highCol.setCellValueFactory(
                new PropertyValueFactory<>("high"));

        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setMinWidth(100);
        notesCol.setCellValueFactory(
                new PropertyValueFactory<>("notes"));

        table.setItems(recordList);
        table.getColumns().addAll(dateCol, weightCol, temperatureCol, lowCol,highCol,notesCol);

        table.setEditable(true);

        return table;

    }
    //Dialog box for the confirmation of deleting a record
    public Alert alertEditRecord() throws SQLException{

        // create a alert
        Alert a = new Alert(Alert.AlertType.NONE);
        // set alert type
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete a record");
        a.setHeaderText("Do you want to delete this record?");
        a.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response ->deleteRecord());

        return a;

    }


//Delete the selected record and also deleted from the database
    public void deleteRecord() {
        Record selectedRecord = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedRecord);
           DBcon db = new DBcon();
           db.deleteRecord(selectedRecord);
        infoBox("", "Delete a Record", "Your record has been deleted!", Alert.AlertType.INFORMATION);

        System.out.println("deleted");

    }
    
    public Button setDeleteButton() {
        //Creating a Button
        Button delete = new Button();
        //Setting text to the button
        delete.setText("Select");
        //Setting the location of the button

        delete.setTranslateX(280);
        delete.setTranslateY(220);
        delete.setAlignment(Pos.CENTER);

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

               //On clicking the delete button

                try {
                    alertEditRecord();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        return delete;
    }
    private void infoBox(String infoMessage, String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(headerText);
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    try {
                        backToHome();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    //Redirecting to the home page
    public void backToHome() throws SQLException{
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
    //Impementation of the buttons on the page
    public Button setCancelButton(){
        //Creating a Button
        Button cancel = new Button();
        //Setting text to the button
        cancel.setText("Cancel");
        //Setting the location of the button
        cancel.setTranslateX(350);
        cancel.setTranslateY(220);
        cancel.setAlignment(Pos.CENTER);

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                System.out.println("clicked");
//                /* Allow for the values in each cell to be changable */

                try {

                    backToHome();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        return cancel;
    }


    public void showStage(GridPane root) {
        TableView<Record>  table = setupTable();
        Button delete = setDeleteButton();
        Button cancel = setCancelButton();
//loading buttons on the stage̵
        root.getChildren().add(table);
        root.getChildren().add(delete);
        root.getChildren().add(cancel);


        Scene scene = new Scene(root, 1000.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Delete a record");
        this.stage.show();
    }
}
