package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.FontsResourceAnchor;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SortEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
public class GestionPanier {
    ArrayList<String> selectedreservation=new ArrayList<String>();
	ObservableList<String> listsearch = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> searchcategory;

    @FXML
    private TextField searchby;
	@FXML
    private TableView<ObservableList> reservationsTable = new TableView<ObservableList>();
	ObservableList<ObservableList> data = FXCollections.observableArrayList();
	  

		  private double xOffset = 0;
		  private double yOffset = 0;
    @FXML
    private Pane panetetbadel;
    @FXML
    private Button pdf;

	public void initialize() {
			loadtable();
			loadchoicebox();
		 
	 }
    public void loadtable() {
    	try {
        	String sql =  "select * from panier where 1" ;
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

                reservationsTable.getColumns().addAll(col); 
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
	    	reservationsTable.setItems(data);

    	}catch(Exception e ) {
    		System.out.println(e);
    	}
    }
    public void loadtable(String cat, String vaal) {
	    	int num;
	    	if (cat == "id") {
	    		num=0;
	    	}else {
		    	if (cat == "iduser") {
		    		num=1;
		    	}else {
			    	if (cat == "idbook") {
			    		num=2;
			    	}else {
			    		if (cat == "dateemprunt") {
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
	    	reservationsTable.setItems(dataa);
    }

    @FXML
    void AddReservation(ActionEvent event) {
    	 try {
			 	Parent root;
			 	Stage primaryStage = new Stage();
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("AjoutPanier.fxml"));
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
    void UpdateReservation(ActionEvent event) {
    	ObservableList<ObservableList> dataa = reservationsTable.getSelectionModel().getSelectedItem();
    	selectedreservation.clear();
    	selectedreservation.add((String)(Object)dataa.get(0));
    	selectedreservation.add((String)(Object)dataa.get(1));
    	selectedreservation.add((String)(Object)dataa.get(2));
    	//selectedreservation.add((String)(Object)dataa.get(3));
    	//selectedreservation.add((String)(Object)dataa.get(4));
    	ModifierPanier.Modifier(selectedreservation);
		 try {
			 	Parent root;
			 	Stage primaryStage = new Stage();
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("/gui/ModifierPanier.fxml"));
		        root = loader.load();
		        Scene scene = new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("gui/style/application.css").toExternalForm());
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
    void DeleteReservation(ActionEvent event) {
    	ObservableList<ObservableList> dataa = reservationsTable.getSelectionModel().getSelectedItem();
    	try {
			String sql =  "DELETE FROM `panier` WHERE `id` = '"+(String)(Object)dataa.get(0)+"'" ;
			Statement smt = Main.con.createStatement() ;
	        smt.executeUpdate((sql)) ;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("You Deleted The panier "+(String)(Object)dataa.get(1));
			Stage alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertstage.getIcons().add(new Image("/images/logo.jpeg")); // To add an icon
			alert.showAndWait();
			loadtable();
		}catch(Exception e) {
			System.out.println(e);
		}
    }
    	
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
    	listsearch.add("iduser");
    	listsearch.add("idbook");
    	listsearch.add("dateemprunt");
    	listsearch.add("dateretour");
    	searchcategory.getItems().addAll(listsearch);
    }
    
           public void CreerPDF(String pan) {
       // panier c = reservationsTable.getSelectionModel().getSelectedItem();
        
        
        String myWeb = reservationsTable.getSelectionModel().getSelectedItem().toString();
         Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("ex.pdf"));
            document.open();
            document.add(new Paragraph(myWeb));
            document.close();
            System.out.println("PDF created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    private void pdf(TableView tableView) {
        Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream("table_data.pdf"));
        document.open();
        
        // Add table header
        PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());
        for (TableColumn column : reservationsTable.getColumns()) {
            pdfTable.addCell(new PdfPCell(new Phrase(column.getText())));
        }
        
        // Add table data
        for (Object item : tableView.getItems()) {
            for (TableColumn column : reservationsTable.getColumns()) {
                Object cellValue = column.getCellData(item);
                pdfTable.addCell(new PdfPCell(new Phrase(cellValue.toString())));
            }
        }
        
        document.add(pdfTable);
        document.close();
        System.out.println("PDF created successfully");
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void pdf(ActionEvent event) {
         Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream("table_data"+ConnectionController.idU+".pdf"));
        document.open();
        
        // Add table header
        PdfPTable pdfTable = new PdfPTable(reservationsTable.getColumns().size());
        for (TableColumn column : reservationsTable.getColumns()) {
            pdfTable.addCell(new PdfPCell(new Phrase(column.getText())));
        }
        
        // Add table data
        for (Object item : reservationsTable.getItems()) {
            for (TableColumn column : reservationsTable.getColumns()) {
                Object cellValue = column.getCellData(item);
                pdfTable.addCell(new PdfPCell(new Phrase(cellValue.toString())));
            }
        }
        
        document.add(pdfTable);
        document.close();
        System.out.println("PDF created successfully");
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
   


}
