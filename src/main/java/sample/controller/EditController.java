package sample.controller;



import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import sample.DBcon;
import sample.model.Record;
import sample.model.User;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.TableView;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import  javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class EditController  {

    private Record record;
    private User user;
    private Stage stage;




    private ArrayList<Record> allRecords;
    private TableView<Record> table;

    private Label message;




    public EditController(Stage stage, User user, Record record) {

        this.record = record;
        this.stage = stage;
        this.user = user;

       // System.out.println(user.getId());

        DBcon db = new DBcon();
        //Getting data from the database of all the records
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

        table.setItems(recordList);
        table.getColumns().addAll(dateCol, weightCol, temperatureCol, lowCol,highCol, notesCol);

        table.setEditable(true);

        return table;

    }
//Confirmation for the doit functionality
    public Alert alertEditRecord() throws SQLException{

        // create a alert
        Alert a = new Alert(AlertType.NONE);
        // set alert type
        a.setAlertType(AlertType.CONFIRMATION);
        a.setTitle("Edit a record");
        a.setHeaderText("Do you want to edit this record?");
        a.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response ->editRecord());

    return a;

    }

//Results on clicking the confirmation dialog box of the edit functionality
    public Record editRecord() {
        Record selectedRecord = table.getSelectionModel().getSelectedItem();
        try {
            // Customize controller instance
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newrecord.fxml"));
            EditRecordController editRecordController = new EditRecordController(this.stage,user,selectedRecord);
            System.out.println(selectedRecord.getDate());
            loader.setController(editRecordController);
            GridPane root = (GridPane) loader.load();
            editRecordController.showStage(root);

        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
        catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        System.out.println("edited");
        return selectedRecord;

    }
    //redirecting to the home page
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

    public Button setEditButton() {
        //Creating a Button
        Button edit = new Button();
        //Setting text to the button
        edit.setText("Select");
        //Setting the location of the button
        edit.setTranslateX(280);
        edit.setTranslateY(220);
        edit.setAlignment(Pos.CENTER);

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                System.out.println("clicked");
//                /* Allow for the values in each cell to be changable */
                try {
                    alertEditRecord();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        return edit;
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

                //System.out.println("clicked");
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

        // calling the functions that include these buttons
        Button edit = setEditButton();
        Button cancel = setCancelButton();

        //loading the buttons to the fxml
        root.getChildren().add(table);
        root.getChildren().add(edit);
        root.getChildren().add(cancel);


        Scene scene = new Scene(root, 1000.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("Edit a record");
        this.stage.show();

    }



}
