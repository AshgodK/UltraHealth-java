package gui;

import java.io.File;
import java.io.IOException;

import javaapp.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Adminhome {
	  private double xOffset = 0;
	  private double yOffset = 0;
    @FXML
    private Label esmwla9ab;
    
    @FXML
    private Pane panetetbadel;
    @FXML
    private ImageView profilepic=new ImageView();;
    @FXML
    void changepagemanageaccount(ActionEvent event) {
    	try {
	    	Pane newLoadedPane =        FXMLLoader.load(getClass().getResource("/gui/GestionUtilisateurPara.fxml"));
	    	panetetbadel.getChildren().clear();
	    	panetetbadel.getChildren().add(newLoadedPane); 
		}catch(Exception e) {
	    	System.out.println(e);
	    }

    }
	 @FXML
	    public void initialize() {
		 esmwla9ab.setText(Main.username);
         File file = new File(Main.image);
            Image img = new Image(file.toURI().toString());
         profilepic.setImage(img);
         Rectangle clip = new Rectangle(
                 profilepic.getFitWidth(), profilepic.getFitHeight()
                );
                clip.setArcWidth(150);
                clip.setArcHeight(150);
                profilepic.setClip(clip);

                // snapshot the rounded image.
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                WritableImage image = profilepic.snapshot(parameters, null);

                // remove the rounding clip so that our effect can show through.
                profilepic.setClip(null);

                // apply a shadow effect.
                profilepic.setEffect(new DropShadow(20, Color.WHITE));

                // store the rounded image in the imageView.
                profilepic.setImage(image);	 }

    @FXML
    void changepagemanagebooks(ActionEvent event) {
    	try {
	    	Pane newLoadedPane =        FXMLLoader.load(getClass().getResource("/gui/GestionProduct.fxml"));
	    	panetetbadel.getChildren().clear();
	    	panetetbadel.getChildren().add(newLoadedPane); 
		}catch(Exception e) {
	    	System.out.println(e);
	    }
    }

    @FXML
    void changepagemanagecategories(ActionEvent event) {
    	try {
	    	Pane newLoadedPane =        FXMLLoader.load(getClass().getResource("/gui/GestionCategories.fxml"));
	    	panetetbadel.getChildren().clear();
	    	panetetbadel.getChildren().add(newLoadedPane); 
		}catch(Exception e) {
	    	System.out.println(e);
	    }
    }
    @FXML
    void changepagemanageauthors(ActionEvent event) {
    	try {
	    	Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/admin/gestionauthors/authors.fxml"));
	    	panetetbadel.getChildren().clear();
	    	panetetbadel.getChildren().add(newLoadedPane); 
		}catch(Exception e) {
	    	System.out.println(e);
	    }
    }

    @FXML
    void changepagemanagereservation(ActionEvent event) {
    	try {
	    	Pane newLoadedPane =        FXMLLoader.load(getClass().getResource("/gui/GestionPanier.fxml"));
	    	panetetbadel.getChildren().clear();
	    	panetetbadel.getChildren().add(newLoadedPane); 
		}catch(Exception e) {
	    	System.out.println(e);
	    }
    }

    @FXML
    void logout(ActionEvent event) {

    	Parent root;
        try {   
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/Login.fxml"));
        
        root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/style/application.css").toExternalForm());
        Stage stage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
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
            	stage.setX(event.getScreenX() - xOffset);
            	stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.setX(500);
    	stage.setY(200);
        stage.setScene(scene);    

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void closeWindow(ActionEvent event) {

    	((Node)(event.getSource())).getScene().getWindow().hide();

    }

}
