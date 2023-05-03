package gui;

	import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;

import javaapp.Main;
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
import javafx.stage.Stage;

	public class ModifierUserPara {
		static ArrayList<String> selectedusernew = new ArrayList<String>();
		public static void Modifier(ArrayList<String> selecteduser) {
			selectedusernew = selecteduser;
		}
	    @FXML
	    private TextField usernametextfield;

	    @FXML
	    private TextField emailtextfield;

	    @FXML
	    private PasswordField passwordtextfield;

	    @FXML
	    private PasswordField confirmpasstextfield;

	    @FXML
	    private RadioButton isadmin;

	    @FXML
	    private ToggleGroup howadmin;

	    @FXML
	    private RadioButton isuser;

	    @FXML
	    void closeWindow(ActionEvent event) {
	    	((Node)(event.getSource())).getScene().getWindow().hide();

	    }

	    @FXML
	    void onModifierclicked(ActionEvent event) {
	    	if ( !usernametextfield.getText().isEmpty() && !emailtextfield.getText().isEmpty() && !passwordtextfield.getText().isEmpty()) {
	    		if (passwordtextfield.getText().equals(confirmpasstextfield.getText())) {
	    			try {
	    				String sql =  "UPDATE `users` SET `email`='"+emailtextfield.getText()+"',`password`='"+passwordtextfield.getText()+"',`isadmin`="+(isadmin.isSelected()?"1":"0")+" WHERE `username` = '"+usernametextfield.getText()+"'" ;
	    				Statement smt = Main.con.createStatement() ;
	    		        smt.executeUpdate((sql)) ;
		    			Alert alert = new Alert(AlertType.INFORMATION);
		    			alert.setTitle("Success");
		    			alert.setHeaderText(null);
		    			alert.setContentText("You Updated The Account "+usernametextfield.getText());
						Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
		    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
		    			alert.showAndWait();
	    			}catch(Exception e) {
	    				System.out.println(e);
	    			}

	    			
	    			
	    		}else {		
	    			Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error Updating Account");
	        		alert.setHeaderText("Confirmation password");
	        		alert.setContentText("Please verify your password and it s confirmation");
					Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
	    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	        		alert.showAndWait();

	    		}
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Updating Account");
	    		alert.setHeaderText("One of the fields is empty");
	    		alert.setContentText("Please fill all the fields!");
				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	    		alert.showAndWait();

	    	}
		

	    }
	    public void initialize() {
			usernametextfield.setText(selectedusernew.get(0));
			emailtextfield.setText(selectedusernew.get(1));
			passwordtextfield.setText(selectedusernew.get(2));
			howadmin.selectToggle(selectedusernew.get(3).equals("0")?isuser:isadmin);
	    }
}
