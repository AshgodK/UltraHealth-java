/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication3;
import java.io.IOException;

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


/**
 *
 * @author pc
 */
public class JavaApplication3 extends Application {

    /**
     * @param args the command line arguments
     */
    
    
    
    
    public void start(Stage primaryStage) throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException 
    {
     AnchorPane root1 = FXMLLoader.load(getClass().getResource("/gui/evennement.fxml"));
         primaryStage.setScene(new Scene(root1));
        
         primaryStage.show();  


        // Création de la scène
      
       


    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
       
        launch(args);
    }
    
}
