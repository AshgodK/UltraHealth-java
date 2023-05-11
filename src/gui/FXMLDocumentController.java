/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package Gui;

import Model.Question;
import Service.ServiceQuestion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class FXMLDocumentController implements Initializable {
    
      @FXML
    private TextField titreField;
    @FXML
    private TextField categoriField;
    @FXML
    private TextArea contentField;
    @FXML
    private Button btnInsert;
    
    Service.ServiceQuestion serviceQuestion;
    
    Question question;
    @FXML
    private Label label;
    @FXML
    private ImageView ho;
    
  public Connection getConnection(){
      Connection conn;
      try{
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","");
          return conn;
      }catch(Exception ex){
          System.out.println("Error : " + ex.getMessage());
          return null;
      }
  }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    @FXML
    public void savePost(javafx.event.ActionEvent event) throws IOException {
        
      
        question =new Question();
        serviceQuestion = new ServiceQuestion();
        String titre = titreField.getText();
        String categorie = categoriField.getText();
        String content = contentField.getText();
        
        /*LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);*/
        

      
        
        if (titre.isEmpty()|| !titre.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter title or  must contain only letters");
            alert.showAndWait();
            return;
        }
        
        if (categorie.isEmpty()|| !categorie.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter categorie or  must contain only letters");
            alert.showAndWait();
            return;
        }
        if (content.isEmpty()|| !content.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter content or  must contain only letters");
            alert.showAndWait();
            return;
        }
     
        if( content.length()<20 ){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a content greater than 20 caracters");

            alert.showAndWait();
            return;
        }
      //  Post post = new Post(theme,nom, content);
      

        //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
          Date now = new Date(); 
         
          //String date= dtf.format(now);
          question.setTitle(titre);
          question.setContent(content);
          question.setCategorie(categorie);
          question.setCreateDate(now);

      
          
      
        serviceQuestion.addQuestion(question);

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(" saved successfully");
        alert.showAndWait();

      
        titreField.clear();
        contentField.clear();
        categoriField.clear();
       // datePicker.setValue(null);
      
       
    }
    
      @FXML
    private void back(ActionEvent event) throws IOException {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/showQuestion.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Show Post");
                    stage.setScene(scene);
                    stage.show(); 
    }
    
   public void redirectToUpdate(javafx.event.ActionEvent event) throws IOException {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/editQuestion.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Edit");
                    stage.setScene(scene);
                    stage.show(); 
      
}

    @FXML
    private void goho(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/userDashBoard.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                   // stage.setTitle("Edit");
                    stage.setScene(scene);
                    stage.show(); 
    }
   
    
        
   
  
  

  }
    

