package sample;

import sample.model.User;
import sample.model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.sql.PreparedStatement;
import java.util.ArrayList;


public class DBcon {
    private static final String DB_URL = "jdbc:sqlite:con.db";
    private static final String DB_Query = "Select * from users where username = ? and password = ?";

    private static final String DB_InsertedQuery = "Insert into users (firstname, lastname, username, password) values (?,?,?,?)";

    private static final String DB_record_Query = "Insert into records(user_id,date, weight, temperature, low, high, notes) values (?,?,?,?,?,?,?)";

    private static final String DB_updatedRecord_Query = "Update records set date =?, weight =?, temperature =?, low =?, high =?, notes=? where id =? ";

    private static final String DB_updatedProfile_Query = "Update users set firstname =?, lastname =? where id=?";
    private static final String DB_allRecord_Query = "Select * from records where user_id = ?";

    private static final String DB_deletedRecordQuery = "Delete from records where id = ?";

    public User validate(String username, String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DB_URL);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DB_Query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);



            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                System.out.println(user.getId());
                return user;
            }



        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return null;
    }
    public User createUser(String firstname, String lastname, String username, String password){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        User newUser = null;
        try (Connection connection = DriverManager
                .getConnection(DB_URL);


             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DB_InsertedQuery)) {
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();

            newUser = new User();

            System.out.println(preparedStatement);


        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return newUser;
    }
    public Record createRecord(int user_id, String date, String weight, String temperature, String low, String high, String notes) {
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.


        Record newRecord = null;
        try (Connection connection = DriverManager
                .getConnection(DB_URL);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DB_record_Query)) {
            preparedStatement.setString(1, String.valueOf(user_id));
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, weight);
            preparedStatement.setString(4, temperature);
            preparedStatement.setString(5, low);
            preparedStatement.setString(6, high);
            preparedStatement.setString(7, notes);

            preparedStatement.executeUpdate();

            newRecord = new Record();

            System.out.println(preparedStatement);



        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return newRecord;
    }

    public ArrayList<Record> viewAllRecords(int user_id){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.

        try (Connection connection = DriverManager
                .getConnection(DB_URL);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DB_allRecord_Query)) {
            preparedStatement.setString(1, String.valueOf(user_id));

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Record> allRecords = new ArrayList<Record>();


            while(resultSet.next()) {
                Record record = new Record();
                record.setRecordId(resultSet.getInt("id"));
                record.setDate(resultSet.getString("date"));
                record.setWeight(resultSet.getString("weight"));
                record.setTemperature(resultSet.getString("temperature"));
                record.setLow(resultSet.getString("low"));
                record.setHigh(resultSet.getString("high"));
                record.setNotes(resultSet.getString("notes"));
                allRecords.add(record);


            }
                return allRecords;
        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return null;
    }


    public Boolean editedRecord(Record selectedRecord) {
        try (Connection connection = DriverManager
                .getConnection(DB_URL);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DB_updatedRecord_Query)) {
            //preparedStatement.setString(1, String.valueOf(user_id));
            preparedStatement.setString(1, selectedRecord.getDate());
            preparedStatement.setString(2, selectedRecord.getWeight());
            preparedStatement.setString(3, selectedRecord.getTemperature());
            preparedStatement.setString(4, selectedRecord.getLow());
            preparedStatement.setString(5, selectedRecord.getHigh());
            preparedStatement.setString(6, selectedRecord.getNotes());
            preparedStatement.setString(7, String.valueOf(selectedRecord.getRecordId()));

            preparedStatement.executeUpdate();


            System.out.println(preparedStatement);

        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return true;
    }

    public boolean updatedProfile(User user) {
        try(Connection connection = DriverManager
                .getConnection(DB_URL);

        // Step 2:Create a statement using connection object
        PreparedStatement preparedStatement = connection.prepareStatement(DB_updatedProfile_Query)) {
            //preparedStatement.setString(1, String.valueOf(user_id));
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, String.valueOf(user.getId()));

            preparedStatement.executeUpdate();


            System.out.println(preparedStatement);

        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return true;
    }
    public boolean deleteRecord(Record record) {
        try(Connection connection = DriverManager
                .getConnection(DB_URL);

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(DB_deletedRecordQuery)) {

            preparedStatement.setString(1, String.valueOf(record.getRecordId()));

            preparedStatement.executeUpdate();


            System.out.println(preparedStatement);

        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return true;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:con.db");
    }



}
