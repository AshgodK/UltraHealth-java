package gui;

import java.sql.ResultSet;
import java.sql.Statement;

import javaapp.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GestionCategories {
	ObservableList<ObservableList> data = FXCollections.observableArrayList();

    @FXML
    private Pane panetetbadel;

    @FXML
    private TableView<ObservableList> categorytableview;

    @FXML
    private TextField searchby;

    @FXML
    private TextField ajoutwith;

    @FXML
    void addcategory(ActionEvent event) {
    	if (ajoutwith.getText() != null)
	    	try {
				String sql =  "INSERT INTO  `categories` (`name`) VALUES ('"+ajoutwith.getText()+"')" ;
				Statement smt = Main.con.createStatement() ;
		        smt.executeUpdate((sql)) ;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("You Added The Category  "+ajoutwith.getText());
				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
				alert.showAndWait();
				loadtable();
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Adding Category");
	    		alert.setHeaderText("Category Name");
	    		alert.setContentText("Please verify your Category name");
				Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
    			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
	    		alert.showAndWait();

			}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Adding Category");
    		alert.setHeaderText("Category Name");
    		alert.setContentText("Please verify your Category name");
			Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
    		alert.showAndWait();

		}

    }

    @FXML
    void onsearchtyped(KeyEvent event) {
    	loadtable(searchby.getText());
    }
    
    public void loadtable() {
    	try {
        	String sql =  "select * from categories where 1" ;
	        Statement smt = Main.con.createStatement() ;
	        ResultSet rs = smt.executeQuery(sql) ;
	        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn("Categories");
                col.setPrefWidth(262.4);
                col.setCellValueFactory((Callback) new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                categorytableview.getColumns().addAll(col); 
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
	    	categorytableview.setItems(data);

    	}catch(Exception e ) {
    		System.out.println(e);
    	}
    }
    public void initialize() {
    	loadtable();
    }
    public void loadtable( String vaal) {
    	data.clear();
    	try {
        	String sql =  "select * from categories where name LIKE '%"+vaal+"%'" ;
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
	    	categorytableview.setItems(data);

    	}catch(Exception e ) {
    		System.out.println(e);
    	}
    }


    @FXML
    void removeselected(ActionEvent event) {
    	ObservableList<ObservableList> dataa = categorytableview.getSelectionModel().getSelectedItem();
    	try {
			String sql =  "DELETE FROM `categories` WHERE `name` = '"+(String)(Object)dataa.get(0)+"'" ;
			Statement smt = Main.con.createStatement() ;
	        smt.executeUpdate((sql)) ;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("You Deleted The Category  "+(String)(Object)dataa.get(0));
			Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
			alert.showAndWait();
			loadtable();
		}catch(Exception e) {
			System.out.println(e);
		}
    }

}
