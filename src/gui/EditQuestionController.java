/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gui;

import Model.Question;
import Service.ServiceQuestion;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class EditQuestionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Question selected;
   
    @FXML
    private TextField themeField;
    @FXML
    private TextArea contentArea;
       @FXML
    private TextField categoriField;
    
    @FXML
    private Button saveButton;
    
      Service.ServiceQuestion serviceQuestion;
    @FXML
    private Button backButton;
    @FXML
    private ImageView home;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        serviceQuestion = new ServiceQuestion();
    }    
    
     public void setSelected(Question question) {
        this.selected = question;
         
        contentArea.setText(selected.getContent());
        themeField.setText(selected.getTitle());
        categoriField.setText(selected.getCategorie());

    }
     
     
      @FXML
     public void updateQuestion(javafx.event.ActionEvent event) throws IOException {
      
                String content = contentArea.getText();
                String theme = themeField.getText();
                
               
                String categorie = categoriField.getText();
        
            if ( content.isEmpty() || theme.isEmpty() || categorie.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter content, title,categorie ");
            alert.showAndWait();
            return;
            }
        if( content.length()<20 ){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a content greater than 20 caracters");
            alert.showAndWait();
            return;
        }
        
                  selected.setTitle(theme);
               
                  selected.setContent(content);
                  selected.setCategorie(categorie);

                  
                  serviceQuestion.updateQuestion(selected);
                  
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Post updated successfully");
                    alert.showAndWait();
                    
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/showQuestion.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Show Post");
                    stage.setScene(scene);
                    stage.show(); 
              
             
    }

     
    @FXML
    private void back(ActionEvent event) throws IOException {
         
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/FXMLDocument.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show(); 
    }

    @FXML
    private void goHome(MouseEvent event) {
         try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/userDashBoard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) home.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
}
