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
import sample.DBcon;
import sample.model.Record;
import sample.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert.AlertType;

public class ExportController {

        private Record record;
        private User user;
        private Stage stage;


        private ArrayList<Record> allRecords;
        private TableView<Record> table;

        private  ObservableList<Record> recordList;




        public  ExportController(Stage stage, User user, Record record) {

            this.record = record;
            this.stage = stage;
            this.user = user;


           // System.out.println(user.getId());
//Linking to the database
            DBcon db = new DBcon();
//Getting all the records from the dataabse
            allRecords = db.viewAllRecords(user.getId());


        }


        public TableView<Record> setupTable() {
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
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            table.setItems(recordList);
            table.getColumns().addAll(dateCol, weightCol, temperatureCol, lowCol, highCol, notesCol);
            //table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            table.setEditable(true);

            return table;

        }
    // show alert to ask user to select the record
    public void showSelectRecordAlert() {
        Alert alert = new Alert(AlertType.NONE, "Please select records to Export.", ButtonType.OK);
        alert.setTitle("Select Records");
        alert.showAndWait();
    }

    public ObservableList<Record> exportRecord() {

        ObservableList<Record> selectedRecords = table.getSelectionModel().getSelectedItems();


        try {
            // Customize controller instance
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/export.fxml"));
                ExportRecordController exportRecordController = new ExportRecordController(this.stage,user,selectedRecords);
                loader.setController(exportRecordController);
                GridPane root = (GridPane) loader.load();
                exportRecordController.showStage(root);

        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
        System.out.println("edited");

    return selectedRecords;

    }
    //Dialog box for the confirmation
    public Alert alertExportRecord() throws SQLException{

        // create a alert
        Alert a = new Alert(Alert.AlertType.NONE);
        // set alert type
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setTitle("Export Records");
        a.setHeaderText("Do you want to export these records?");
        a.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response ->exportRecord());

        return a;

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
        }
    }

    //Implementation of the button function
    public Button setExportButton() {
        //Creating a Button
        Button export = new Button();
        //Setting text to the button
        export.setText("Select");
        //Setting the location of the button

        export.setTranslateX(280);
        export.setTranslateY(220);
        export.setAlignment(Pos.CENTER);

        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                System.out.println("clicked");
//                /* Allow for the values in each cell to be changable */

                try {
                        alertExportRecord();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });

        return export;
    }
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
    public void initialize(){
        TableView<Record>  table = setupTable();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void showStage(GridPane root) {
        TableView<Record>  table = setupTable();
        Button export = setExportButton();
        Button cancel = setCancelButton();
 //Loading the data into the stage
        root.getChildren().add(table);
        root.getChildren().add(export);
        root.getChildren().add(cancel);


        Scene scene = new Scene(root, 1000.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.setTitle("Export records");
        this.stage.show();

    }

}
