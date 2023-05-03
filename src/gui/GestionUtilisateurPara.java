package gui;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javaapp.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
public class GestionUtilisateurPara {
    ArrayList<String> selecteduser=new ArrayList<String>();
	@FXML
    private TableView<ObservableList> accountsTable = new TableView<ObservableList>();
	ObservableList<ObservableList> data = FXCollections.observableArrayList();
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
	    private RadioButton isuser;

	    @FXML
	    private ToggleGroup howadmin;
	    @FXML
	    private ChoiceBox<String> searchcategory = new ChoiceBox<String>();
		ObservableList<String> listsearch = FXCollections.observableArrayList();

	    @FXML
	    private TextField searchby;


		  private double xOffset = 0;
		  private double yOffset = 0;

	public void initialize() {
			loadtable();
			loadchoicebox();
		 
	 }
    public void loadtable() {
    	try {
        	String sql =  "select * from admin " ;
	        Statement smt = Main.con.createStatement() ;
	        ResultSet rs = smt.executeQuery(sql) ;
	        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback) new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                accountsTable.getColumns().addAll(col); 
            }
	    	while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
            //FINALLY ADDED TO TableView
	    	accountsTable.setItems(data);

    	}catch(Exception e ) {
    		System.out.println(e);
    	}
    }
    public void loadtable(String cat, String vaal) {
    	data.clear();
    	try {
        	String sql =  "select * from admin where "+cat+" LIKE '%"+vaal+"%'" ;
	        Statement smt = Main.con.createStatement() ;
	        ResultSet rs = smt.executeQuery(sql) ;
	    	while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
            //FINALLY ADDED TO TableView
	    	accountsTable.setItems(data);

    	}catch(Exception e ) {
    		System.out.println(e);
    	}
    }

    @FXML
    void UpdateUser(ActionEvent event) {
    	ObservableList<ObservableList> dataa = accountsTable.getSelectionModel().getSelectedItem();
    	selecteduser.clear();
    	selecteduser.add((String)(Object)dataa.get(0));
    	selecteduser.add((String)(Object)dataa.get(1));
    	selecteduser.add((String)(Object)dataa.get(2));
    	selecteduser.add((String)(Object)dataa.get(3));
    	ModifierUserPara.Modifier(selecteduser);
		 try {
			 	Parent root;
			 	Stage primaryStage = new Stage();
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("/gui/ModifierUserPara.fxml"));
		        root = loader.load();
		        Scene scene = new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("/gui/style/application.css").toExternalForm());
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
    @FXML
    void DeleteUser(ActionEvent event) {
    	ObservableList<ObservableList> dataa = accountsTable.getSelectionModel().getSelectedItem();
    	try {
			String sql =  "DELETE FROM `admin` WHERE `username` = '"+(String)(Object)dataa.get(0)+"'" ;
			Statement smt = Main.con.createStatement() ;
	        smt.executeUpdate((sql)) ;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("You Deleted The Account "+(String)(Object)dataa.get(0));
			Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
			alert.showAndWait();
			loadtable();
		}catch(Exception e) {
			System.out.println(e);
		}
    }
    	
    @FXML
    void closeWindow(ActionEvent event) {
    	((Node)(event.getSource())).getScene().getWindow().hide();

    }
    @FXML
    void searchkeypressed(KeyEvent event) {
    	if(searchcategory.getValue()!=null) {
	    	loadtable(searchcategory.getValue(),searchby.getText());
    	}
    }
    public void loadchoicebox() {
    	listsearch.clear();
    	listsearch.add("username");
    	listsearch.add("email");
    	listsearch.add("isadmin");
    	searchcategory.getItems().addAll(listsearch);
    }

}
