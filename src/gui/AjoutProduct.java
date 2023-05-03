package gui;

import java.sql.ResultSet;
import java.sql.Statement;

import javaapp.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
	import javafx.scene.control.RadioButton;
	import javafx.scene.control.TextField;
	import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

	public class AjoutProduct {	
    	ObservableList<String> listcategories = FXCollections.observableArrayList();
	    	    @FXML
	    void closeWindow(ActionEvent event) {
	    	((Node)(event.getSource())).getScene().getWindow().hide();

	    }

	    	    @FXML
	    	    private TextField titletextfield;
	    	    @FXML
	    	    private TextField descriptionfield;
	    	    @FXML
	    	    private TextField pricefield;

	    	    @FXML
	    	    private ChoiceBox<String> categoryselect = new ChoiceBox<String>();


	    @FXML
	    void onAddclicked(ActionEvent event) {
	    	if ( !titletextfield.getText().isEmpty() && !categoryselect.getValue().isEmpty()) {
	    		try {
	    			String sql = "INSERT INTO `product`(`titre`, `description`, `price`, `Categorie`) VALUES ('"+titletextfield.getText()+"','"+descriptionfield.getText()+"','"+pricefield.getText()+"','"+categoryselect.getValue().toString()+"')";
	    			Statement smt = Main.con.createStatement() ;
	    		    smt.executeUpdate((sql)) ;
		    		Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Success");
		    		alert.setHeaderText(null);
		    		alert.setContentText("You Added The Product "+titletextfield.getText());
					Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
	    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
		    		alert.showAndWait();
	    		}catch(Exception e) {
	    			System.out.println(e);
	    		}
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Adding Product");
	    		alert.setHeaderText("One of the fields is empty");
	    		alert.setContentText("Please fill all the fields!");
				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	    		alert.showAndWait();

	    	}
		

	    }
	    public void initialize() {
		    try {
		        Statement smt = Main.con.createStatement() ;
		        listcategories.removeAll();
		    	String categoryssql =  "select * from categories where 1" ;
		        ResultSet rss = smt.executeQuery(categoryssql) ;
		        while (rss.next()) {

		        	listcategories.add(rss.getString("name")) ;
			        }
		        categoryselect.getItems().addAll(listcategories);
		    }catch(Exception e){
		    	System.out.println(e);
		    }
	   }
}
