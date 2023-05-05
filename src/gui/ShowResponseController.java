/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Gui;

import Model.Response;
import Service.ServiceResponse;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ShowResponseController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Button tfremove;
    @FXML
    private TableView<Response> tftableview;
   
    
    @FXML
    private TableColumn<Response, String> tctext;
    @FXML
    private TableColumn<Response, Date> tcdate;
    @FXML
    private TextField searchField;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
Service.ServiceResponse sc = new ServiceResponse();
         List<Response> lc = sc.displayResponse();
          ObservableList<Response> obsList = FXCollections.observableArrayList(lc);
                    
          tctext.setCellValueFactory(new PropertyValueFactory<Response, String>("text"));
          tcdate.setCellValueFactory(new PropertyValueFactory<Response, Date>("date"));
          
           // add an event listener to the search field
    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
       searchResponse();
    });
          tftableview.setItems(obsList);

    }    
    
    @FXML
    private void remove(ActionEvent event) {
         Response r=tftableview.getSelectionModel().getSelectedItem();
           
        if ( r== null) {
        // show an error message if no post is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please select a comment");
        alert.showAndWait();
        return;
    }
        else{
          ServiceResponse service = new ServiceResponse();
          Dialog<ButtonType> confirmationDialog = new Dialog<>();
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Are you sure to delete this comment?");

        // set the graphic for the confirmation dialog window
        Stage stage = (Stage) confirmationDialog.getDialogPane().getScene().getWindow();

        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationDialog.getDialogPane().getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            // delete the post
            service.deleteResponse(r.getId());
            refreshtable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Comment deleted successfully");
            alert.showAndWait();
        }

    }
    
}
    
    
    
    public void refreshtable()
    {
        ServiceResponse sc = new ServiceResponse();
         List<Response> lc = sc.displayResponse();
          ObservableList<Response> obsList = FXCollections.observableArrayList(lc);
         
        
          tctext.setCellValueFactory(new PropertyValueFactory<Response, String>("text"));
          tcdate.setCellValueFactory(new PropertyValueFactory<Response, Date>("date"));
          
           // add an event listener to the search field
    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
       searchResponse();
    });
          tftableview.setItems(obsList);
    }
   
    
    public void searchResponse() {
    ServiceResponse service=new ServiceResponse();
    String query = searchField.getText();
    List<Response> searchResults = service.searchBy(query);
    tftableview.setItems(FXCollections.observableArrayList(searchResults));
}
    
}
