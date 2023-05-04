
package gui;

//import com.journaldev.mail.*;

import javaapp.Main;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoginController {
	  private double xOffset = 0;
	  private double yOffset = 0;
	    private TextField usernametextfield;

	    private TextField emailtextfield;
	    private PasswordField passwordtextfield;

	    private PasswordField confirmpasstextfield;

	    private String imagePath= new String();
	            private Label filelabel;
    @FXML
	    private Label Success;
	 @FXML
	    private TextField loginusername;
@FXML
	    private PasswordField loginpasseword;
	    private TextField forgetpasswordusername;
	    private TextField forgetpasswordemail;
    @FXML
    private ImageView logo;
@FXML
          void forgotPassClicked(MouseEvent event) {

		Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/gui/ForgetPassPara.fxml"));
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
	        
	        stage.setScene(scene);     

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
@FXML
          void onloginclicked(ActionEvent event) {

    	Parent root;
        
        	try {
        	String sql =  "select * from admin where username = '"+loginusername.getText()+"' and password = '"+ loginpasseword.getText()+"'" ;
	        Statement smt = Main.con.createStatement() ;
	        ResultSet rs = smt.executeQuery(sql) ;
	        int i = 0;
	        int isadmin = 0;
	         while (rs.next()) {
	        	 i++;
	        	 Main.username = rs.getString("username") ;
	        	 isadmin = rs.getInt("isadmin");
	        	 Main.image = rs.getString("image") ;
	        }
	         if (i==1) {
	        	 if (isadmin == 1) {
		        	 try {
		             	FXMLLoader loader = new FXMLLoader();
		    	        loader.setLocation(getClass().getResource("/gui/Adminhome.fxml"));
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
		    	        stage.setX(100);
		            	stage.setY(80);
		         
		    	        stage.setScene(scene);     
	
		            }
		            catch (IOException e) {
		                e.printStackTrace();
		            }
	        	 }else {
	        		 try {
			             	FXMLLoader loader = new FXMLLoader();
			    	        loader.setLocation(getClass().getResource("/gui/UserhomePara.fxml"));
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
			    	        stage.setX(100);
			            	stage.setY(80);
			         
			    	        stage.setScene(scene);     
		
			            }
			            catch (IOException e) {
			                e.printStackTrace();
			            }
		        	 }
	        	 
	        	 
	         }else {
	        	 Success.setText("Username Or PassWord incorrect!");
	         }
        }catch(Exception e) {
        	System.out.println(e);
        }
    }
@FXML
          void registerClicked(MouseEvent event) {//don't have account button
    	
    	Parent root;
        try {   
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/SignupPara.fxml"));
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
    
          void onSignupclicked(ActionEvent event) {
    	if ( !usernametextfield.getText().isEmpty() && !emailtextfield.getText().isEmpty() && !passwordtextfield.getText().isEmpty()&& !imagePath.isEmpty()) {
    		if (passwordtextfield.getText().equals(confirmpasstextfield.getText())) {
    			try {
    				String sql =  "INSERT INTO admin(username, email, password, image) VALUES ('"+usernametextfield.getText()+"','"+emailtextfield.getText()+"','"+passwordtextfield.getText()+"','"+imagePath+"')" ;
    				Statement smt = Main.con.createStatement() ;
    		        smt.executeUpdate((sql)) ;
        			Alert alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle("Success");
        			alert.setHeaderText(null);
        			alert.setContentText("You now have an account in Think Digital! Please LogIn to access our system");
        			Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
        			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
        			alert.showAndWait();
    			}catch(Exception e) {
    				Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error Creating Account");
            		alert.setHeaderText("Wrong information");
            		alert.setContentText(e.toString());
            		Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
        			alertstage.getIcons().add(new Image("/gui/images/logo.jpeg")); // To add an icon
            		alert.showAndWait();

    			}

    	    	Parent root;
    	        try {   
    	        FXMLLoader loader = new FXMLLoader();
    	        loader.setLocation(getClass().getResource("Login.fxml"));
    	        
    	        root = loader.load();
    	        Scene scene = new Scene(root);
    	        scene.getStylesheets().add(getClass().getResource("/gui/style/application.css").toExternalForm());
    	        Stage stage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
    	        stage.setScene(scene);    

    	        }
    	        catch (IOException e) {
    	            e.printStackTrace();
    	        }


    		}else {		
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Creating Account");
        		alert.setHeaderText("Confirmation password");
        		alert.setContentText("Please verify your password and it s confirmation");
        		Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
        		alert.showAndWait();

    		}
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Creating Account");
    		alert.setHeaderText("One of the fields is empty");
    		alert.setContentText("Please fill all the fields!");
    		Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
			
    		alert.showAndWait();

    	}
	

	    
    }

          void Backlogin(MouseEvent event) {

    	Parent root;
        try {   
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        
        root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/style/application.css").toExternalForm());
        Stage stage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
        stage.setScene(scene);    

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    void onforgetclicked(ActionEvent event) {
    	String password = "";
    	try {
        	String sql =  "select * from admin where username = '"+forgetpasswordusername.getText()+"' and email = '"+ forgetpasswordemail.getText()+"'" ;
	        Statement smt = Main.con.createStatement() ;
	        ResultSet rs = smt.executeQuery(sql) ;
	        int i = 0;
	         while (rs.next()) {
	        	 i++;
	        	 password = rs.getString("password") ;
	        }
	         if (i==1) {       	 
	        	 final String fromEmailcon = "kalaide46@gmail.com"; //requires valid gmail id
	             final String passwordcon = "Minefokma123"; // correct password for gmail id
	             final String toEmail = forgetpasswordemail.getText(); // can be any email id 
	             Properties props = new Properties();
	             props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
	             props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
	             props.put("mail.smtp.socketFactory.class",
	                     "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
	             props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
	             props.put("mail.smtp.port", "465"); //SMTP Port

	             Authenticator auth = new Authenticator() {
	                 //override the getPasswordAuthentication method
	                 protected PasswordAuthentication getPasswordAuthentication() {
	                     return new PasswordAuthentication(fromEmailcon, passwordcon);
	                 }
	             };

	             Session session = Session.getDefaultInstance(props, auth);
	          //   EmailUtil.sendEmail(session, toEmail,"[Reset Password Request]", "Hello.\nYour Password is '"+password+"'\nYours Think Digital support,");
	      
	         }else {
	        	 Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error Sending Password");
	        		alert.setHeaderText("Confirmation email and username");
	        		alert.setContentText("Please verify your username and or your email");
					Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
	    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	        		alert.showAndWait();

	         }
        }catch(Exception e) {
        	System.out.println(e);
        }

    	 
    	   }
          void uploadPic(ActionEvent event) {
        try {
            Stage stage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
            FileChooser fil_chooser = new FileChooser();
            File file = fil_chooser.showOpenDialog(stage); 

            if (file != null) { 

                filelabel.setText(file.getAbsolutePath()
                                    + "  selected"); 
                imagePath=file.getAbsolutePath().toString().replace("\\", "\\\\");
            } 
        }catch(Exception e) {
            System.out.println(e);
        }
}
    
          @FXML
    public void back()
    {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/userDashBoard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) logo.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}