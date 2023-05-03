/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultrahealth;


import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ASUS
 */
public class Ultrahealth extends Application{

    private double xoffset=0;
    private double yoffset=0;
    
    public void start(Stage primaryStage) throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException 
    {
     AnchorPane root1 = FXMLLoader.load(getClass().getResource("/gui/consultation.fxml"));
         primaryStage.setScene(new Scene(root1));
        root1.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event){
                xoffset=event.getSceneX();
               yoffset=event.getSceneY();
                
              // Enregistrement de l'heure de démarrage
       
             
        }
        } );
        
  
      root1.setOnMouseDragged(new EventHandler<MouseEvent>(){
          @Override
          public void handle(MouseEvent event)
          {
            primaryStage.setX(event.getScreenX() - xoffset);
            primaryStage.setY(event.getScreenY() - yoffset);
          }
      });
         primaryStage.show();  


        // Création de la scène
      
       


    }
   
        
  
    
    
public static void main (String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException 
{
    


       
       
             
        
             launch(args);
             
}}
    

