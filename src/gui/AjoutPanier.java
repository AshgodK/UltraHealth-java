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

import javafx.scene.control.DatePicker;
	public class AjoutPanier {
    	ObservableList<String> listusersusername = FXCollections.observableArrayList();		
    	ObservableList<String> listbooksname = FXCollections.observableArrayList();
    	ObservableList<Integer> listbooksid = FXCollections.observableArrayList();
        @FXML
        private ChoiceBox<String> bookselect;

        @FXML
        private ChoiceBox<String> usersselect;

        @FXML
        private DatePicker dateemprunt;

        @FXML
        private DatePicker dateretour;

    	@FXML
	    void closeWindow(ActionEvent event) {
	    	((Node)(event.getSource())).getScene().getWindow().hide();

	    }


	    @FXML
	    void onAddclicked(ActionEvent event) {
	    	if (!usersselect.getValue().isEmpty() && !bookselect.getValue().isEmpty() && !dateemprunt.getValue().toString().isEmpty() && !dateretour.getValue().toString().isEmpty()) {
	    		if (dateemprunt.getValue().isBefore(dateretour.getValue())) {
	    			
	    			try {
		    			String sql = "INSERT INTO `reservation`(`iduser`, `idbook`, `dateemprunt`, `dateretour`) VALUES ('"+usersselect.getValue()+"','"+listbooksid.get(listbooksname.indexOf(bookselect.getValue()))+"','"+dateemprunt.getValue().toString()+"','"+dateretour.getValue().toString()+"')";
		    			Statement smt = Main.con.createStatement() ;
		    		    smt.executeUpdate((sql)) ;
			    		Alert alert = new Alert(AlertType.INFORMATION);
			    		alert.setTitle("Success");
			    		alert.setHeaderText(null);
			    		alert.setContentText("You Added The Reservation for the book "+bookselect.getValue());
						Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
		    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
			    		alert.showAndWait();
		    		}catch(Exception e) {
		    			System.out.println(e);
		    		}
	    		}else {
    	    		Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Error Adding Reservation");
    	    		alert.setHeaderText("Date of return before date of borrow");
    	    		alert.setContentText("Please change the borrow and return date");
    				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
        			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
    	    		alert.showAndWait();

	    		}
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Adding Reservation");
	    		alert.setHeaderText("One of the fields is empty");
	    		alert.setContentText("Please fill all the fields!");
				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	    		alert.showAndWait();
	    		
	    	}
	    }

	    public void initialize() {
		    try {
		    	listusersusername.removeAll();
		    	String userssql =  "select * from users where 1" ;
		        Statement smt = Main.con.createStatement() ;
		        ResultSet rs = smt.executeQuery(userssql) ;
		        while (rs.next()) {

		        	listusersusername.add(rs.getString("username")) ;
			        }
		        usersselect.getItems().addAll(listusersusername);
		        listbooksname.removeAll();
		        listbooksid.removeAll();
		    	String bookssql =  "SELECT * FROM `book` WHERE `id` not in(select idbook from reservation where 1)" ;
		        ResultSet rss = smt.executeQuery(bookssql) ;
		        while (rss.next()) {

		        	listbooksname.add(rss.getString("titre")) ;
		        	listbooksid.add(rss.getInt("id")) ;
			        }
		        bookselect.getItems().addAll(listbooksname);
		    }catch(Exception e){
		    	System.out.println(e);
		    }
	   }
}
