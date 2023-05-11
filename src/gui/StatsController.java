/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gui;

import Model.Question;
import Service.ServiceQuestion;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class StatsController implements Initializable {

     @FXML
    private VBox statisticsBox;
    
      private List<Question> question;
    @FXML
    private ImageView home;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         ServiceQuestion sp = new  ServiceQuestion();
         question =  sp.displayPosts();
       Map<String, Integer> postCommentCounts = new HashMap<>();

       // Get number of comments for each post and store in a map
       for (Question post : question) {
           int commentCount = sp.nbrComments(post.getId());
           System.out.println(commentCount);
           postCommentCounts.put(post.getTitle(), commentCount);
       }

          // Create a pie chart to display the statistics
            PieChart pieChart = new PieChart();
            pieChart.setTitle("Number of comments per post");

            // Add the data to the chart
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : postCommentCounts.entrySet()) {
                String postName = entry.getKey();
                int commentCount = entry.getValue();
                data.add(new PieChart.Data(postName + " (" + commentCount + " comments)", commentCount));
            }
            pieChart.setData(data);

            // Add the chart to the statisticsBox
            statisticsBox.getChildren().add(pieChart);
    }

     @FXML
     public void goHome()
    {
     try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/FXMLDocument.fxml"));
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
