package gui;

	import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

	public class ModifierProduct {
    	ObservableList<String> listauthors = FXCollections.observableArrayList();		
    	ObservableList<String> listcategories = FXCollections.observableArrayList();
		static ArrayList<String> selectedbooknew = new ArrayList<String>();
		public static void Modifier(ArrayList<String> selectedbook) {
			selectedbooknew = selectedbook;
		}
	    	    @FXML
	    void closeWindow(ActionEvent event) {
	    	((Node)(event.getSource())).getScene().getWindow().hide();

	    }
	    	    @FXML
	    	    private TextField idtextfield;

	    	    @FXML
	    	    private TextField titletextfield;

	    	    @FXML
	    	    private ChoiceBox<String> categoryselect;

	    	    @FXML
	    	    private ChoiceBox<String> Authorselect;

	    @FXML
	    void onModifierclicked(ActionEvent event) {
	    	if ( !idtextfield.getText().isEmpty() && !titletextfield.getText().isEmpty()) {
	    		try {
	    			String sql =  "UPDATE `product` SET `titre`='"+titletextfield.getText()+"',`categorie`='"+categoryselect.getValue().toString()+"',`description`= '"+Authorselect.getValue().toString()+"' WHERE `id` = '"+idtextfield.getText()+"'" ;
	    			Statement smt = Main.con.createStatement() ;
	    		    smt.executeUpdate((sql)) ;
		    		Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Success");
		    		alert.setHeaderText(null);
		    		alert.setContentText("You Updated The Book "+titletextfield.getText());
					Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
	    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
		    		alert.showAndWait();
	    		}catch(Exception e) {
	    			System.out.println(e);
	    		}
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Updating Book");
	    		alert.setHeaderText("One of the fields is empty");
	    		alert.setContentText("Please fill all the fields!");
				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	    		alert.showAndWait();

	    	}
		

	    }
	    public void initialize() {
	    	try {
		    	listauthors.removeAll();
		    	String authorssql =  "select * from authors where 1" ;
		        Statement smt = Main.con.createStatement() ;
		        ResultSet rs = smt.executeQuery(authorssql) ;
		        while (rs.next()) {

		        	listauthors.add(rs.getString("name")) ;
			        }
		        Authorselect.getItems().addAll(listauthors);
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

			idtextfield.setText(selectedbooknew.get(0));
			titletextfield.setText(selectedbooknew.get(1));
			categoryselect.setValue(selectedbooknew.get(2));
			Authorselect.setValue(selectedbooknew.get(3));
	    }
}
