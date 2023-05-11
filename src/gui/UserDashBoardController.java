/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.Users;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javaapp.JavaApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Base;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class UserDashBoardController implements Initializable {

    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
   
   // private Button checkEvents;

    private Base db;
    private Users user ;


    @FXML
    private Label profile;

    @FXML
    private DatePicker birtDayEditField;

    @FXML
    private Label birthDay;

    @FXML
    private Label cin;

    @FXML
    private TextField cinEditField;

    @FXML
    private Label email;

    @FXML
    private TextField emailEditField;

    @FXML
    private Label firstName;

    @FXML
    private TextField firstNameEditField;

    @FXML
    private Label lastName;

    @FXML
    private TextField lastNameEditField;

    @FXML
    private Button updateBtn;

    @FXML
    private Label zone;

    @FXML
    private Label role;

    @FXML
    private TextField zoneEditField;

    @FXML
    private Button logOutBtn;
    @FXML
    private ImageView eventKey;
    @FXML
    private ImageView GoToPara;
    private Button GoToRd;
    @FXML
    private ImageView GoToCon;
    @FXML
    private ImageView GoToArticle;
    @FXML
    private ImageView GoToMenu;
    @FXML
    private Button Forum;
    

     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        
    }    
    
    @FXML
    void iconProfileClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
        this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
        this.user = new Users();
        this.db.connection();
        String sqlQuery = "SELECT * FROM users WHERE role != 'ADMIN' AND token IS NOT NULL";
        ResultSet res = this.db.useStatment(sqlQuery);
        if(res.next()){
            this.user.setEmail(res.getString(8));
            this.profile.setText(this.user.getEmail());
            this.role.setText(res.getString(7));
            this.cin.setText(res.getString(2));
            this.firstName.setText(res.getString(3));
            this.lastName.setText(res.getString(4));
            this.zone.setText(res.getString(5));
            this.birthDay.setText(res.getString(6));
            this.email.setText(res.getString(8));
        }
    }

    @FXML
    void updateBtnClicked(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.user = new Users();
        String sqlQuery = "SELECT * FROM users WHERE email = '"+this.profile.getText()+"'";
        ResultSet result = this.db.useStatment(sqlQuery);
        if(result.next()){
            this.user.setId(result.getInt(1));
            System.out.println("id user -> "+ this.user.getId());
        }

        db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
        db.connection();
        LocalDate lDate = this.birtDayEditField.getValue();
        java.util.Date date = this.user.converter(lDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        sqlQuery = "UPDATE users SET cin = '"+this.cinEditField.getText()+"', firstname = '"+this.firstNameEditField.getText()+"', lastname = '"+this.lastNameEditField.getText()+"', zone = '"+this.zoneEditField.getText()+"', birthday = '"+lDate+"', email = '"+this.emailEditField.getText()+"' WHERE id = '"+this.user.getId()+"'";
        System.out.println(sqlQuery);
        int res = this.db.insertIntoBase(sqlQuery);
        if(res == 1){
            System.out.println("succes ! ");
        }else{
            System.out.println("failed ! ");
        }
    

    }

    @FXML
    void editIconClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
        this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
        this.user = new Users();
        this.db.connection();
        this.user.setEmail(this.profile.getText());
        String sqlQuery = "SELECT * FROM users WHERE email = '"+this.user.getEmail()+"'";
        System.out.println("Query -> "+this.user.getEmail());
        ResultSet res = this.db.useStatment(sqlQuery);
        if(res.next()){
            this.user.setId(res.getInt(1));
            this.user.setCin(res.getString(2));
            this.user.setFirstName(res.getString(3));
            this.user.setLastName(res.getString(4));
            this.user.setZone(res.getString(5));
            this.user.setBirthDay(res.getDate(6));
            this.user.setRole(res.getString(7));
            this.user.setEmail(res.getString(8));
            this.user.setPassword(res.getString(9));
            this.user.setToken(res.getString(10));
        }
       //System.out.println("->"+this.user.toString());
       this.cinEditField.setText(this.user.getCin());
       this.firstNameEditField.setText(this.user.getFirstName());
       this.lastNameEditField.setText(this.user.getLastName());
       this.zoneEditField.setText(this.user.getZone());
       LocalDate localDate = ((Date) this.user.getBirthDay()).toLocalDate();
       this.birtDayEditField.setValue(localDate);
       this.emailEditField.setText(this.user.getEmail());
    }

    @FXML
    void deleteIconClicked(MouseEvent event) throws ClassNotFoundException, SQLException {
       this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
       db.connection();
        String sqlQuery = "DELETE FROM users WHERE email = '"+this.profile.getText()+"'";
        int result = db.deleteFrom(sqlQuery);
        System.out.println(sqlQuery);

        if (result == 1) {
            System.out.println("User deleted successfully from the database.");
        } else {
            System.out.println("Error deleting user from the database.");
        }
    }

    @FXML
    void logOutBtnClicked(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
      this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
      this.db.connection();
        this.user = new Users();
        this.user.setEmail(this.profile.getText());
        String sqlQuery = "SELECT *  FROM users WHERE email = '"+this.user.getEmail()+"'";
        
        ResultSet res = this.db.useStatment(sqlQuery);
        if(res.next()){
            this.user.setId(res.getInt(1));
        }
        sqlQuery = "UPDATE users SET token = NULL WHERE id = '"+this.user.getId()+"'";
        
        int reslt = this.db.insertIntoBase(sqlQuery);
        if(reslt == 1 ){
            System.out.println("log out succes ! ");
            JavaApp.setRoot("connection");
            
        }else{
            System.out.println("failed ! ");
        }
    }
    @FXML
    public void gotoPara()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) GoToPara.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void gotoCon()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/rendezVous.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) GoToCon.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void gotoArticle()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guii/AfficherPost.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) GoToArticle.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void gotoMenu()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) GoToMenu.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
     @FXML
    public void gotoEvent()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/evennementFront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) eventKey.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
  
    @FXML
    public void goForum()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/FXMLDocument.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) Forum.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void toMenu(MouseEvent event) {
        System.out.println("menu");
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) GoToMenu.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   

}
