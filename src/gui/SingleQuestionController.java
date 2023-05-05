/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gui;

import Model.Question;
import Model.Response;
import Service.ServiceResponse;
import Utils.MyConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class SingleQuestionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
private Label lblTitle;

@FXML
private Label lblCategorie;

@FXML
private Label lblDate;

@FXML
private Label lblContent;
@FXML
private Label likesLabel;
@FXML
private Label dislikecounts;

  @FXML
    private TextArea commentTextArea;
    @FXML
    private Button commentButton;
         @FXML
    private Button back;
      
        @FXML
    private Button like;  
        @FXML
    private Button dislike;
          @FXML
    private Button show;
    
    Service.ServiceResponse sr;
    Question question;
    private int questionId;
    SingleQuestionController singleQuestion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sr=new ServiceResponse();
        question = new Question();
        
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

    void setQuestion(Question question) {
 // set the question's details in the single question view
    lblTitle.setText(question.getTitle());
    lblCategorie.setText(question.getCategorie());
    lblDate.setText(question.getCreateDate().toString());
    lblContent.setText(question.getContent());  
        displayResponse(); 
    
}
    
    
    public void setQuestionId(int questionId) {
    this.questionId = questionId;
}

    public int getQuestionId() {
    return question.getId();
}
        private void displayResponse() {
        List<Response> comments = sr.displayResponse();
        for (Response comment : comments) {
            if (comment.getQuestion().getId() == question.getId()) {
                Label commentLabel = new Label(comment.getText());
                Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                
                
        Dialog<ButtonType> confirmationDialog = new Dialog<>();
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Are you sure to delete this comment?");

        // set the graphic for the confirmation dialog window
        Stage stage = (Stage) confirmationDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/image/logo.png"));

        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationDialog.getDialogPane().getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            // delete the comment
           sr.deleteResponse(comment.getId());
       //    this.post.removeComment(comment);
            displayResponse();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setContentText("Comment deleted successfully");
              alert.showAndWait();  
              
        }
            });
            
                 Button updateButton = new Button("Update");
            updateButton.setOnAction(e -> {
                TextArea editCommentArea = new TextArea(comment.getText());
                Button saveButton = new Button("Save");
                Button cancelButton = new Button("Cancel");
                HBox buttonBox = new HBox(saveButton, cancelButton);
                buttonBox.setSpacing(5);
                VBox editBox = new VBox(editCommentArea, buttonBox);
                editBox.setSpacing(10);
                Stage editStage = new Stage();
                editStage.setScene(new Scene(editBox));
                editStage.setTitle("Update your comment");
                editStage.show();
                cancelButton.setOnAction(event -> editStage.close());
                saveButton.setOnAction(event -> {
                    String updatedComment = editCommentArea.getText();
                    if (updatedComment.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Comment cannot be empty");
                        alert.showAndWait();
                    } else {
                        
                        // Send HTTP request to Neutrino API to check for bad words
                     try {
                        URL url = new URL("https://neutrinoapi.net/bad-word-filter");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("User-ID", "dhiaa");
                        connection.setRequestProperty("API-Key", "G0xqXJGgRKqnmp9antKSChgQnE1dIODmHJ3FfQLkl30nm06D");
                        
                        // Send request content
                        connection.setDoOutput(true);
                       OutputStream os = connection.getOutputStream();
                       os.write(("content=" + updatedComment).getBytes());
                       os.flush();
                       os.close();
                       
                        // Read API response
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        // Check if content contains bad words
                        if (response.toString().contains("true")) {
                            // Handle bad word found
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Your comment contains inappropriate language and cannot be posted.");
                            alert.showAndWait();
                        }
                        else{
                        comment.setText(updatedComment);
                        sr.updateResponse(comment);
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Comment updated successfully");
                         alert.showAndWait();
                        editStage.close();
                        displayResponse();
                    }
                         } catch (IOException ex) {
        System.out.println("Error checking for bad words: " + ex.getMessage());
    }
                    }
                });
            });
             HBox buttonBox = new HBox(deleteButton, updateButton);
            buttonBox.setSpacing(5);
               VBox commentBox = new VBox(commentLabel, buttonBox);
            commentBox.setSpacing(5);
            }
        
        }
    }

      
    
     @FXML 
     private void addResponse() {
    String content = commentTextArea.getText();

    if (content.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Comment cannot be empty");
        alert.showAndWait();
    } else {
    // Send HTTP request to Neutrino API to check for bad words
    try {
        URL url = new URL("https://neutrinoapi.net/bad-word-filter");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-ID", "dhiaa");
        connection.setRequestProperty("API-Key", "G0xqXJGgRKqnmp9antKSChgQnE1dIODmHJ3FfQLkl30nm06D");
       
        
        // Send request content
         connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(("content=" + content).getBytes());
        os.flush();
        os.close();
        
        // Read API response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        // Check if content contains bad words
        if (response.toString().contains("true")) {
            // Handle bad word found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your comment contains inappropriate language and cannot be posted.");
            alert.showAndWait();
        } else {
            

            // Add comment to list of comments
            Response c = new Response();
            c.setText(content);
            c.setDate(new Date());
            c.setQuestion(this.question);
         
           // this.post.addComment(c);
         
            sr.addResponse(c);
            commentTextArea.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Comment saved successfully");
            alert.showAndWait();
            displayResponse();
            
            

        }
    } catch (IOException e) {
        System.out.println("Error checking for bad words: " + e.getMessage());
    }
}
}
     
     @FXML
    private void showResponse(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/showResponse.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                 
                    stage.show(); 
    }
   
        
                 
@FXML
private void rater(ActionEvent event) {
  
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
 private void Like(ActionEvent event) throws SQLException{
String query = "UPDATE question SET likes = likes + 1 WHERE id = ?" ;
PreparedStatement statement = getConnection().prepareStatement(query);
statement.setInt(1, questionId);
statement.executeUpdate();
     
 }
 
 @FXML
 private void disLike(ActionEvent event) throws SQLException{
String query = "UPDATE question SET dislikes = dislikes + 1 WHERE id = ?";
PreparedStatement statement = getConnection().prepareStatement(query);
statement.setInt(1, questionId);
statement.executeUpdate();
   System.out.println("+1 like");

     
 }
 
 public void displayLikesCount(Question question, Label likesLabel) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "", "");
        PreparedStatement statement = connection.prepareStatement("SELECT likes FROM question WHERE id = ?");
        statement.setInt(1, questionId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int likesCount = resultSet.getInt("like");
            likesLabel.setText("Like : " + likesCount);
        }
    } catch (SQLException e) {
        System.out.println("Error while fetching likes count : " + e.getMessage());
    }
}



    
}
