package sample.controller;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.User;
import sample.model.Record;

public class HomeController {

    private Stage parentStage;

    @FXML
    private MenuItem viewProfile;
    @FXML
    private MenuItem updateProfile;
    @FXML
    private MenuItem newRecord;
    @FXML
    private MenuItem editRecord;
    @FXML
    private MenuItem deleteRecord;
    @FXML
    private MenuItem allRecords;
    @FXML
    private MenuItem exportRecords;
    @FXML
    private Menu records;
    @FXML
    private MenuItem aboutapp;
    @FXML
    private MenuItem logout;

    @FXML
    private Label nameLabel;

    private Stage stage;

    private Record record;
    private User user;

    public void all_records(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/allrecords.fxml"));
            System.out.println(user.getId());
            AllRecordsController allRecordsController = new  AllRecordsController(this.stage,this.user, record);
            loader.setController(allRecordsController);
            GridPane root = (GridPane) loader.load();
            allRecordsController.showStage(root);
            //allRecordsController.showRecords();
            //this.stage.close();

        } catch (IOException var5) {
            var5.printStackTrace();

        }
    }
    public void edit_records(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/editrecord.fxml"));
            System.out.println(user.getId());
            EditController editController = new EditController(this.stage,this.user, record);
            loader.setController(editController);
            GridPane root = (GridPane) loader.load();
            editController.showStage(root);
            //allRecordsController.showRecords();
            //this.stage.close();

        } catch (IOException var5) {
            var5.printStackTrace();

        }
    }
    public void new_record(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newrecord.fxml"));
            NewRecordController newRecordController = new NewRecordController(this.stage,user,record);
            loader.setController(newRecordController);
            GridPane root = (GridPane)loader.load();
            newRecordController.showStage(root);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    public void view_profile(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewprofile.fxml"));
            ViewProfileController viewProfileController = new ViewProfileController(this.stage,user);
            loader.setController(viewProfileController);
            GridPane root = (GridPane)loader.load();
          viewProfileController.showStage(root);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    public void update_profile(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editprofile.fxml"));
            UpdateProfileController updateProfileController = new UpdateProfileController(this.stage,user);
            loader.setController(updateProfileController);
            GridPane root = (GridPane) loader.load();
            updateProfileController.showStage(root);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    public void delete_record(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editrecord.fxml"));
            DeleteRecordController deleteRecordController = new DeleteRecordController(this.stage,user,record);
            loader.setController(deleteRecordController);
            GridPane root = (GridPane) loader.load();
            deleteRecordController.showStage(root);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    public void export_record(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editrecord.fxml"));
            ExportController exportController = new ExportController(this.stage,user,record);
            loader.setController(exportController);
            GridPane root = (GridPane) loader.load();
            exportController.showStage(root);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }

    }
    public void about_app(ActionEvent event) throws SQLException{
        try {
            //Loading the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/aboutapp.fxml"));
            AboutController aboutController = new AboutController(this.stage,this.user);
            loader.setController(aboutController);
            AnchorPane root = (AnchorPane) loader.load();
            aboutController.showStage(root);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }



        public void initialize() {
        //The  firstname and lastname appears on the home page
        nameLabel.setText(user.getFirstname()+" " +user.getLastname());
        //Implementation of the functions of menu items on clicking them
                this.newRecord.setOnAction((event) -> {
                    try {
                        this.new_record(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.allRecords.setOnAction((event) -> {
                    try {
                        this.all_records(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    catch (IndexOutOfBoundsException e){
                        infoBox("", "View all Records", "No records found to be viewed", Alert.AlertType.ERROR);
                    }
                });
                this.editRecord.setOnAction((event) -> {
                    try {
                        this.edit_records(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    catch (IndexOutOfBoundsException e){
                        infoBox("", "Edit a Record", "No records found to be edited", Alert.AlertType.ERROR);
                    }
                });
                this.viewProfile.setOnAction((event) -> {
                    try {
                        this.view_profile(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.updateProfile.setOnAction((event) -> {
                    try {
                        this.update_profile(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.deleteRecord.setOnAction((event) -> {

                    try {
                        this.delete_record(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    catch (IndexOutOfBoundsException e){
                        infoBox("", "Delete a Record", "No records found to be deleted", Alert.AlertType.ERROR);
                    }
                });
                this.exportRecords.setOnAction((event) -> {
                    try {
                        this.export_record(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    //This exception doesn't seem to work if the user has no records.
                    catch (IndexOutOfBoundsException e){
                        infoBox("", "Export records", "No records found to be exported!", Alert.AlertType.ERROR);
                    }
                });
                this.aboutapp.setOnAction((event) -> {
                    try {
                        this.about_app(event);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.logout.setOnAction((e)->{
                        user = null;
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
                });
            }
    private void infoBox(String infoMessage, String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

//Constructor of the HomeController
    public HomeController(Stage stage, User user) {

        this.stage = stage;
        this.user = user;
        System.out.println(user.getId());
    }

    public void showStage(VBox root) {
            Scene scene = new Scene(root, 600.0, 400.0);
            this.stage.setScene(scene);
            this.stage.setResizable(false);
            this.stage.setTitle("MyHealth");
            this.stage.show();
    }
}
