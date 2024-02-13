package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import sample.DBcon;
import sample.model.Record;
import sample.model.User;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.TableView;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;


public class AllRecordsController {


    @FXML
    private TableView<Record> record_table;
    @FXML
    private TableColumn<Record, String> dateColumn;
    @FXML
    private TableColumn<Record, String> weightColumn;
    @FXML
    private TableColumn<Record, String> temperatureColumn;
    @FXML
    private TableColumn<Record, String> lowColumn;
    @FXML
    private TableColumn<Record, String> highColumn;
    @FXML
    private TableColumn<Record, String> notesColumn;
    @FXML
    private Label title;

    private Record record;
    private User user;
    private Stage stage;
    private Button home;

    //    public AllRecordsController(Stage stage, User user, String date, String weight, String temperature, String low, String high, String notes) {
//
//    }
    private ArrayList<Record> allRecords;

    public AllRecordsController(Stage stage, User user, Record record) {

        this.record = record;
        this.stage = stage;
        this.user = user;


        System.out.println(user.getId());

        DBcon db = new DBcon();

        allRecords = db.viewAllRecords(user.getId());


    }

    public TableView<Record> setupTable() {
        //Displaying all records in the table
        ObservableList<Record> recordList = FXCollections.observableArrayList(allRecords);


        //System.out.println(recordList.get(0).getHigh());
        TableView<Record> table = new TableView<>();

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
        table.getColumns().addAll(dateCol, weightCol, temperatureCol, lowCol, highCol, notesCol);

        return table;


    }
//redirecting to the home page
    public void backToHome() throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            HomeController homeController = new HomeController(this.stage, user);
            loader.setController(homeController);
            VBox root = (VBox) loader.load();
            homeController.showStage(root);
        } catch (IOException e) {
            e.printStackTrace();
            //  message.setText(e.getMessage());
        }
    }
    //Implememtation of the buttons on the page
    public Button setHomeButton() {
        //Creating a Button
        Button home = new Button();
        //Setting text to the button
        home.setText("Ok");
        //Setting the location of the button

        //TODO: Put them in right place

        //home.setLayoutX(500);
        home.setTranslateX(300);
        home.setTranslateY(220);
        home.setAlignment(Pos.BOTTOM_CENTER);

        home.setOnAction(new EventHandler<ActionEvent>() {
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
        return home;

    }
//    public Label labelTitle(){
//        Label title = new Label();
//        title.setText("All my health records");
//        return title;
//    }

    public void showStage(GridPane root) {
        TableView<Record> table = setupTable();
        Button home = setHomeButton();

        //Label title = labelTitle();

        //Loading the button and table on the stage
        root.getChildren().add(table);
        root.getChildren().add(home);

       // root.getChildren().add(title);


        Scene scene = new Scene(root, 1000.0, 500.0);
        this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.setTitle("View all existing records");
        this.stage.show();
    }


}
