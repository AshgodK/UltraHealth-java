package javaapp;
import java.sql.Connection;

//import com.spire.pdf.PdfDocument;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import util.Connexionbdd;


public class Main extends Application {
	public static Connection con = Connexionbdd.Connect();
	public static String username ="kal";
	public static String image="";
	  private double xOffset = 0;
	  private double yOffset = 0;
	  Parent root;
	  Stage stage;
	  
	@Override
	public void start(Stage primaryStage) {
		 try {
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("Auth/Login.fxml"));
		        root = loader.load();
		        Scene scene = new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		        primaryStage.getIcons().add(new Image("/images/logo.jpeg"));
		        primaryStage.setTitle("Think Digital");

		        primaryStage.initStyle(StageStyle.UNDECORATED);
		        primaryStage.setScene(scene);
		        primaryStage.show();  
		        root.setOnMousePressed(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		                xOffset = event.getSceneX();
		                yOffset = event.getSceneY();
		            }
		        });
		        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		                primaryStage.setX(event.getScreenX() - xOffset);
		                primaryStage.setY(event.getScreenY() - yOffset);
		            }
		        });


		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
