package gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
public class GestionProduct {
    ArrayList<String> selectedbook=new ArrayList<String>();
	ObservableList<String> listsearch = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> searchcategory;

    @FXML
    private TextField searchby;
	@FXML
    private TableView<ObservableList> booksTable = new TableView<ObservableList>();
	ObservableList<ObservableList> data = FXCollections.observableArrayList();
	  

		  private double xOffset = 0;
		  private double yOffset = 0;

	public void initialize() {
			loadtable();
			loadchoicebox();
		 
	 }
    public void loadtable() {
    	try {
        	String sql =  "select * from product where 1" ;
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

                booksTable.getColumns().addAll(col); 
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
	    	booksTable.setItems(data);

    	}catch(Exception e ) {
    		System.out.println(e);
    	}
    }
    public void loadtable(String cat, String vaal) {
	    	int num;
	    	if (cat == "id") {
	    		num=0;
	    	}else {
		    	if (cat == "titre") {
		    		num=1;
		    	}else {
			    	if (cat == "description") {
			    		num=2;
			    	}else {
			    		if (cat == "price") {
				    		num=3;
				    	}else {
				    		num = 4;
				    	}
			    	}
		    	}
	    	}
	    	ObservableList<ObservableList> dataa =data.stream().filter(cas -> cas.get(num).toString().contains(vaal)).collect(Collector.of(
	                FXCollections::observableArrayList,
	                ObservableList::add,
	                (l1, l2) -> { l1.addAll(l2); return l1; }));
            //FINALLY ADDED TO TableView
	    	booksTable.setItems(dataa);
    }

    @FXML
    void AddBook(ActionEvent event) {
    	 try {
			 	Parent root;
			 	Stage primaryStage = new Stage();
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("AjoutProduct.fxml"));
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
    void UpdateBook(ActionEvent event) {
    	ObservableList<ObservableList> dataa = booksTable.getSelectionModel().getSelectedItem();
    	selectedbook.clear();
    	selectedbook.add((String)(Object)dataa.get(0));
    	selectedbook.add((String)(Object)dataa.get(1));
    	selectedbook.add((String)(Object)dataa.get(2));
    	selectedbook.add((String)(Object)dataa.get(3));
    	ModifierProduct.Modifier(selectedbook);
		 try {
			 	Parent root;
			 	Stage primaryStage = new Stage();
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("/gui/ModifierProduct.fxml"));
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
    void DeleteBook(ActionEvent event) {
    	ObservableList<ObservableList> dataa = booksTable.getSelectionModel().getSelectedItem();
    	try {
			String sql =  "DELETE FROM `product` WHERE `id` = '"+(String)(Object)dataa.get(0)+"'" ;
			Statement smt = Main.con.createStatement() ;
	        smt.executeUpdate((sql)) ;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("You Deleted The Book "+(String)(Object)dataa.get(1));
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
	    	loadtable(searchcategory.getValue().toString(),searchby.getText());
    	}
    }
    public void loadchoicebox() {
    	listsearch.clear();
    	listsearch.add("id");
    	listsearch.add("titre");
    	listsearch.add("description");
    	listsearch.add("price");
    	listsearch.add("Categorie");
    	searchcategory.getItems().addAll(listsearch);
    }
    

}
