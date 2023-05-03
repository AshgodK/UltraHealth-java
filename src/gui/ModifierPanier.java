package gui;

	import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

	public class ModifierPanier {
    	ObservableList<String> listusersusername = FXCollections.observableArrayList();		
    	ObservableList<String> listbooksname = FXCollections.observableArrayList();
    	ObservableList<Integer> listbooksid = FXCollections.observableArrayList();

		static ArrayList<String> selectedreservationnew = new ArrayList<String>();
		public static void Modifier(ArrayList<String> selectedres) {
			selectedreservationnew = selectedres;
		}
	    	    @FXML
	    void closeWindow(ActionEvent event) {
	    	((Node)(event.getSource())).getScene().getWindow().hide();

	    }
	    	    @FXML
	    	    private TextField idreservation;

	    	    @FXML
	    	    private ChoiceBox<String> bookselect;

	    	    @FXML
	    	    private DatePicker dateemprunt;

	    	    @FXML
	    	    private DatePicker dateretour;

	    	    @FXML
	    	    private ChoiceBox<String> userselect;


	    	    @FXML
	    	    void onModifierclicked(ActionEvent event) {
	    	    	if (!dateemprunt.getValue().toString().isEmpty() && !dateretour.getValue().toString().isEmpty()) {
	    	    		try {
	    	    			String sql =  "UPDATE `reservation` SET `iduser`='"+userselect.getValue()+"',`idbook`='"+listbooksid.get(listbooksname.indexOf(bookselect.getValue()))+"',`dateemprunt`= '"+dateemprunt.getValue().toString()+"',`dateretour`= '"+dateretour.getValue().toString()+"' WHERE `id`='"+idreservation.getText()+"'" ;
	    	    			Statement smt = Main.con.createStatement() ;
	    	    		    smt.executeUpdate((sql)) ;
	    		    		Alert alert = new Alert(AlertType.INFORMATION);
	    		    		alert.setTitle("Success");
	    		    		alert.setHeaderText(null);
	    		    		alert.setContentText("You Updated The Reservation for the book "+bookselect.getValue());
	    					Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	    		    		alert.showAndWait();
	    	    		}catch(Exception e) {
	    	    			System.out.println(e);
	    	    		}

	    	    	}else{
	    	    		Alert alert = new Alert(AlertType.ERROR);
	    	    		alert.setTitle("Error Updating Reservation");
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
	    		        userselect.getItems().addAll(listusersusername);
	    		        listbooksname.removeAll();
	    		        listbooksid.removeAll();
	    		    	String bookssql =  "SELECT * FROM `book` WHERE `id` not in(select idbook from reservation where id != '"+selectedreservationnew.get(0)+"')" ;
	    		        ResultSet rss = smt.executeQuery(bookssql) ;
	    		        while (rss.next()) {
	    		        	listbooksname.add(rss.getString("titre")) ;
	    		        	listbooksid.add(rss.getInt("id")) ;
	    			        }
	    		        bookselect.getItems().addAll(listbooksname);
	    		    }catch(Exception e){
	    		    	System.out.println(e);
	    		    }
	    		    idreservation.setText(selectedreservationnew.get(0));
	    			userselect.setValue(selectedreservationnew.get(1));
	    			bookselect.setValue(listbooksname.get(listbooksid.indexOf(Integer.parseInt(selectedreservationnew.get(2)))));
	    			dateemprunt.setValue(LocalDate.parse(selectedreservationnew.get(3)));
	    			dateretour.setValue(LocalDate.parse(selectedreservationnew.get(4)));
	    			
	    	   }
}
