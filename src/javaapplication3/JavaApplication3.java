/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication3;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//import services.PersonneService;
import util.MyConnection;
/**
 *
 * @author pc
 */
public class JavaApplication3 extends Application {

    /**
     * @param args the command line arguments
     */
    
    
     private double xoffset=0;
    private double yoffset=0;
    
    public void start(Stage primaryStage) throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException 
    {
     AnchorPane root1 = FXMLLoader.load(getClass().getResource("/gui/Evennement.fxml"));
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
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
       
        launch(args);
    }
    
}
