/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.Users;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javaapp.JavaApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.Base;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AdminDashboardController implements Initializable {

   Base db;
    Users user;
    ConnectionController cnxController;

    @FXML
    private TextField cinEdit;

    @FXML
    private TextField emailEdit;

    @FXML
    private TextField fNameEdit;

    @FXML
    private TextField lNameEdit;

    @FXML
    private TextField zoneEdit;

    @FXML
    private TableColumn<Users, String> cinColumn;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView editIcon;



    @FXML
    private TableColumn<Users, String> emailColumn;

    @FXML
    private TableColumn<Users, String> firstNameColumn;

    @FXML
    private TableColumn<Users, Integer> idColumn;

    @FXML
    private TableColumn<Users, String> lastNameColumn;

    private Pane formPane;

    @FXML
    private Label profileLabale;

    @FXML
    private ImageView refreshIcone;

    @FXML
    private Button disconnectinBtn;

    @FXML
    private TableColumn<Users, String> roleColumn;

    @FXML
    private ImageView profileIcon;

    @FXML
    private TableView<Users> userTableView;

    @FXML
    private Button UpdateBtn;

    @FXML
    private ImageView allUsersConected;


    @FXML
    private TableColumn<String, String> zoneColumn;  
    @FXML
    private Button EVENTS_man;
    
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
EVENTS_man.setOnAction(event -> {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/evennement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) EVENTS_man.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });
        // other initialization code for your TableView and other components
    }

private void handleUpdateButtonAction(ActionEvent event) {
    Users selectedUser = userTableView.getSelectionModel().getSelectedItem();

    if (selectedUser != null) {
        // populate form fields with selected user's information
        cinEdit.setText(selectedUser.getCin());
        fNameEdit.setText(selectedUser.getFirstName());
        lNameEdit.setText(selectedUser.getLastName());
        zoneEdit.setText(selectedUser.getZone());
        emailEdit.setText(selectedUser.getEmail());

        // show the form
        formPane.setVisible(true);
    }
}

private void handleSaveButtonAction(ActionEvent event) {
    Users selectedUser = userTableView.getSelectionModel().getSelectedItem();

    if (selectedUser != null) {
        // update selected user's information
        selectedUser.setCin(cinEdit.getText());
        selectedUser.setFirstName(fNameEdit.getText());
        selectedUser.setLastName(lNameEdit.getText());
        selectedUser.setZone(zoneEdit.getText());
        selectedUser.setEmail(emailEdit.getText());

        // hide the form
        formPane.setVisible(false);

        // update the TableView
        userTableView.refresh();
    }
}

    @FXML
    void deleteClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
        Users selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
        userTableView.getItems().remove(selectedUser);

       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       db.connection();
        String sqlQuery = "DELETE FROM users WHERE id = '"+selectedUser.getId()+"'";
            int result = db.deleteFrom(sqlQuery);

        if (result == 1) {
            System.out.println("User deleted successfully from the database.");
        } else {
            System.out.println("Error deleting user from the database.");
        }
    }
    }

    @FXML
    void editClicked() throws ClassNotFoundException, SQLException {
       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       db.connection();
        Users selectedUser = userTableView.getSelectionModel().getSelectedItem();
        user = selectedUser;
        cinEdit.setText(selectedUser.getCin());
        fNameEdit.setText(selectedUser.getFirstName());
        lNameEdit.setText(selectedUser.getLastName());
        zoneEdit.setText(selectedUser.getZone());
        emailEdit.setText(selectedUser.getEmail());
    }

    @FXML
    void UpdateBtnClicked(ActionEvent event) throws ClassNotFoundException, SQLException {
      this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
      db.connection();
        String sqlQuery = "UPDATE users SET cin=?, firstName=?, lastName=?, zone=?, email=? WHERE id=?";
        PreparedStatement statement = db.connection().prepareStatement(sqlQuery);
        statement.setString(1, cinEdit.getText());
        statement.setString(2, fNameEdit.getText());
        statement.setString(3, lNameEdit.getText());
        statement.setString(4, zoneEdit.getText());
        statement.setString(5, emailEdit.getText());
        statement.setInt(6, user.getId());
        int result = statement.executeUpdate();
        if (result == 1) {
            System.out.println("User updated successfully in the database.");
            // Update the TableView with the new data.
            user.setCin(cinEdit.getText());
            user.setFirstName(fNameEdit.getText());
            user.setLastName(lNameEdit.getText());
            user.setZone(zoneEdit.getText());
            user.setEmail(emailEdit.getText());
            userTableView.refresh();
        } else {
            System.out.println("Error updating user in the database.");
        }
    }

    @FXML
    void refreshClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       this.db.connection();
        String sqlQuery = "SELECT * FROM users ";
        ResultSet result = this.db.useStatment(sqlQuery);
        
        // Create an ObservableList to store the user data.
        ObservableList<Users> userList = FXCollections.observableArrayList();

        // Loop through the ResultSet and add each user to the ObservableList.
        while(result.next()){
            int id = result.getInt(1);
            String cin = result.getString(2);
            String firstName = result.getString(3);
            String lastName = result.getString(4);
            String zone = result.getString(5);
            String role = result.getString(7);
            String email = result.getString(8);
            Users user = new Users(id, cin, firstName, lastName, zone, null, role, email, email, email);
            userList.add(user);
        }

        // Set the TableView's items to the ObservableList.
        userTableView.setItems(userList);
    }

    @FXML
    void allUsersConectedClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       this.db.connection();
        String sqlQuery = "SELECT * FROM users WHERE token IS NOT NULL";
        ResultSet result = this.db.useStatment(sqlQuery);
        
        // Create an ObservableList to store the user data.
        ObservableList<Users> userList = FXCollections.observableArrayList();

        // Loop through the ResultSet and add each user to the ObservableList.
        while(result.next()){
            int id = result.getInt(1);
            String cin = result.getString(2);
            String firstName = result.getString(3);
            String lastName = result.getString(4);
            String zone = result.getString(5);
            String role = result.getString(7);
            String email = result.getString(8);
            Users user = new Users(id, cin, firstName, lastName, zone, null, role, email, email, email);
            userList.add(user);
        }

        // Set the TableView's items to the ObservableList.
        userTableView.setItems(userList);
    }

    @FXML
    void profileClicked(MouseEvent event) throws SQLException, IOException, ClassNotFoundException, URISyntaxException {

       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       this.db.connection();
        this.user = new Users();
        
        String sqlQuery = "SELECT * FROM users WHERE role = 'ADMIN' AND token IS NOT NULL";
        ResultSet res = this.db.useStatment(sqlQuery);
        if(res.next()){
            this.user.setId(res.getInt(1));
            
                this.user.setFirstName(res.getString(2));
                this.user.setLastName(res.getString(3));
                this.user.setFirstName(res.getString(4));
                this.user.setZone(res.getString(5));
                this.user.setBirthDay(res.getDate(6));
                this.user.setRole(res.getString(7));
                this.user.setEmail(res.getString(8));
                this.user.setPassword(res.getString(9));
                this.user.setToken(res.getString(10));
                System.out.println(this.user.toString());
            this.profileLabale.setText(this.user.getEmail());
        }

    }

    @FXML
    void disconnectinBtnClicked(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       this.db.connection();
        this.user = new Users();
        this.user.setEmail(this.profileLabale.getText());
        String sqlQuery = "SELECT *  FROM users WHERE email = '"+this.user.getEmail()+"'";
        ResultSet res = this.db.useStatment(sqlQuery);
        if(res.next()){
            this.user.setId(res.getInt(1));
        }
        sqlQuery = "UPDATE users SET token = NULL WHERE id = '"+this.user.getId()+"'";
        int reslt = this.db.insertIntoBase(sqlQuery);
        if(reslt == 1 ){
            System.out.println("succes ! ");
            JavaApp.setRoot("connection");
            try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/connection.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) EVENTS_man.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }else{
            System.out.println("failed ! ");
        }

    }
    
}
