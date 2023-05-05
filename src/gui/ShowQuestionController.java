/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gui;

import Model.Question;
import Service.ServiceQuestion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ShowQuestionController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
    @FXML
    private TableColumn<Question,String> colTitle;
      @FXML
    private TableColumn<Question,String> colCategorie; 
      
        @FXML
    private TableColumn<Question,String> colContent; 
 
      @FXML
    private TableColumn<Question,String> colDate;  
      @FXML
      private TableView <Question> tvQuestion;
    
    @FXML
    private TextField searchField;
    
    private Service.ServiceQuestion service;
    
    @FXML
    private Button details;
      @FXML
    private Button edit;  
      @FXML
    private Button remove;
         @FXML
    private Button btndate;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Question question = new Question();
        service = new  ServiceQuestion();
         // add an event listener to the search field
    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        searchPosts();
    });
  
    
    }    
    
    
    
    
    
    
    
    
public Connection getConnection(){
      Connection conn;
      try{
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","");
                     System.out.println("Connexion établie!");

          return conn;
          
      }catch(Exception ex){
          System.out.println("Error : " + ex.getMessage());
          return null;
      }
  }
    
public ObservableList<Question> getQuestionList() {
    ObservableList<Question> questionList = FXCollections.observableArrayList();
    Connection conn = getConnection();
    String query = "SELECT * FROM question";
    Statement st;
    ResultSet rs;
    try {
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            Question question = new Question(
                rs.getInt("id"), 
                rs.getString("title"),
                rs.getString("categorie"),
                rs.getDate("createDate"),
                rs.getString("content")
            );
            questionList.add(question);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return questionList;
}
     
public void showQuestion() {
    ObservableList<Question> list = getQuestionList();
        
    colTitle.setCellValueFactory(new PropertyValueFactory<Question,String>("title"));
    colCategorie.setCellValueFactory(new PropertyValueFactory<Question,String>("categorie"));
    colDate.setCellValueFactory(new PropertyValueFactory<Question,String>("createDate"));
    colContent.setCellValueFactory(new PropertyValueFactory<Question,String>("content"));
    
    tvQuestion.setItems(list);
    
   

}

public void redirectUpdate(javafx.event.ActionEvent event) throws IOException{
         if ( tvQuestion.getSelectionModel().getSelectedItem()== null) {
        // show an error message if no post is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please select a post");
        alert.showAndWait();
        return;
    }
        
        else{  FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/editQuestion.fxml"));
            Parent root = loader.load();
            EditQuestionController editController = loader.getController();
            editController.setSelected(tvQuestion.getSelectionModel().getSelectedItem());
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Edit Post");
            stage.setScene(scene);
            stage.show();
}
   
}

public void redirectSingle(javafx.event.ActionEvent event) throws IOException{
         if ( tvQuestion.getSelectionModel().getSelectedItem()== null) {
        // show an error message if no post is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please select a post");
        alert.showAndWait();
        return;
    }
        
        else{ 
             // Récupérer l'id de la question sélectionnée
int questionId = tvQuestion.getSelectionModel().getSelectedItem().getId();

// Envoyer l'id au contrôleur SingleQuestionController
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/singleQuestion.fxml"));
            Parent root = loader.load();
            SingleQuestionController singleQuestionController = loader.getController();
            singleQuestionController.setQuestion(tvQuestion.getSelectionModel().getSelectedItem());
            singleQuestionController.setQuestionId(questionId);

            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("single question");
            stage.setScene(scene);
            stage.show();
}
   
}



 @FXML
    public void remove(javafx.event.ActionEvent event) {
         Question p=tvQuestion.getSelectionModel().getSelectedItem();
           
        if ( p== null) {
        // show an error message if no post is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please select a post");
        alert.showAndWait();
        return;
    }
        else{
          ServiceQuestion service = new ServiceQuestion();
          Dialog<ButtonType> confirmationDialog = new Dialog<>();
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Are you sure to delete this post?");

        // set the graphic for the confirmation dialog window
        Stage stage = (Stage) confirmationDialog.getDialogPane().getScene().getWindow();

        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationDialog.getDialogPane().getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            // delete the post
            service.deleteQuestion(p.getId());
            showQuestion();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Post deleted successfully");
            alert.showAndWait();
        }

    }
    }
        
    



 public void searchPosts() {
    String query = searchField.getText();
    List<Question> searchResults = service.searchByTitle(query);
    tvQuestion.setItems(FXCollections.observableArrayList(searchResults));
}
 
   @FXML
    private void displayStats(javafx.event.ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("stats.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Stats Post");
            stage.setScene(scene);
            stage.show();
    }
   
 
public ObservableList<Question> orderList() {
    ObservableList<Question> questionList = FXCollections.observableArrayList();
    Connection conn = getConnection();
    String query = "SELECT * FROM question ORDER BY createDate DESC";
    Statement st;
    ResultSet rs;
    try {
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            Question question = new Question(
                rs.getInt("id"), 
                rs.getString("title"),
                rs.getString("categorie"),
                rs.getDate("createDate"),
                rs.getString("content")
            );
            questionList.add(question);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return questionList;
}

public void OderQuestion() {
    ObservableList<Question> list = orderList();
        
    colTitle.setCellValueFactory(new PropertyValueFactory<Question,String>("title"));
    colCategorie.setCellValueFactory(new PropertyValueFactory<Question,String>("categorie"));
    colDate.setCellValueFactory(new PropertyValueFactory<Question,String>("createDate"));
    colContent.setCellValueFactory(new PropertyValueFactory<Question,String>("content"));
    
    tvQuestion.setItems(list);
    
   

}

}
