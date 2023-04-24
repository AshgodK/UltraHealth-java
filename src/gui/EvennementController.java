/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


import Entity.Evennement;
import Entity.EventCategory;
import Service.EvennementServ;
import Service.EventCategServ;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EvennementController implements Initializable {

    @FXML
    private TableView<Evennement> tableViewEv;
    @FXML
    private TextField titre;
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private TextField adr;
    @FXML
    private TextArea desc;
    @FXML
    private ChoiceBox<String> categorie;
    @FXML
    private TableColumn<?, ?> id_event;
    @FXML
    private TableColumn<?, ?> titre_event;
    @FXML
    private TableColumn<?, ?> dateD_event;
    @FXML
    private TableColumn<?, ?> dateF_event;
    @FXML
    private TableColumn<?, ?> cat_event;
    @FXML
    private TableColumn<?, ?> adr_event;
    @FXML
    private TableColumn<?, ?> dec_event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EventCategServ ev = new EventCategServ();
        //List<String> categories = ev.GetCategoriesTitle();
        categorie.getItems().addAll(ev.GetCategoriesTitle());
    }  
    
    @FXML
    private void ajouter(javafx.event.ActionEvent event) 
    {
        
         String ttr=titre.getText();
        String selectedCategory = categorie.getValue();
        String dct=desc.getText();
        String adre=adr.getText();
            LocalDate dateD=date_debut.getValue();
            
            LocalDate dateF=date_fin.getValue();
            
            
            
        
        
         if (ttr.isEmpty()||  dct.isEmpty()||  selectedCategory.isEmpty()||adre.isEmpty()||dateF == null||dateD == null) 
         {
            // Alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
           
         } 
         else 
         { 
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             String dateString = dateD.format(formatter);
             DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             String dateStringF = dateF.format(formatterF);
             EventCategServ ecs= new EventCategServ();
             EvennementServ Evnt=new EvennementServ();
             int cat = ecs.GetCategorieID(selectedCategory);
             if (cat == -1) {
    // Category not found, show error message and return
    Alert alert = new Alert(Alert.AlertType.ERROR, "Selected category not found.");
    alert.showAndWait();
    return;
}
             Evennement newEvent = new Evennement();
             newEvent.setDate_deb(dateString);
             newEvent.setDate_fin(dateStringF);
             newEvent.setTitre(ttr);
             newEvent.setDescription(dct);
             newEvent.setAdresse(adre);
             Evnt.AjouterEvent(newEvent,cat);
             
         
           
             
             
             
         }        
    }
    
    @FXML
    public void AfficherEvent() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    List<Evennement> lE = ev.GetEvennements();
    
   
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);

    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
    dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    tableViewEv.setItems(EVList);
}
    
    
    @FXML
    public void updateEvent()
    {
        int SelectedRowIndex = tableViewEv.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tableViewEv.getColumns().indexOf(id_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEv.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
           int id_modif = Integer.parseInt(IdCell);
           //EventCategServ ec = new EventCategServ();
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous modifier event dont l'ID : " + IdCell + " ?");
            Optional<ButtonType> result = A.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
               String ttr=titre.getText();
        String selectedCategory = categorie.getValue();
        String dct=desc.getText();
        String adre=adr.getText();
            LocalDate dateD=date_debut.getValue();
            
            LocalDate dateF=date_fin.getValue();
        
        
         if (ttr.isEmpty()||  dct.isEmpty()||  selectedCategory.isEmpty()||adre.isEmpty()||dateF == null||dateD == null) 
         {
            // Alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
           
         } 
         else 
         { 
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             String dateString = dateD.format(formatter);
             DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             String dateStringF = dateF.format(formatterF);
             EventCategServ ecs= new EventCategServ();
             EvennementServ Evnt=new EvennementServ();
             int cat = ecs.GetCategorieID(selectedCategory);
             if (cat == -1) {
    // Category not found, show error message and return
    Alert alert = new Alert(Alert.AlertType.ERROR, "Selected category not found.");
    alert.showAndWait();
    return;
}
             Evennement newEvent = new Evennement();
             newEvent.setDate_deb(dateString);
             newEvent.setDate_fin(dateStringF);
             newEvent.setTitre(ttr);
             newEvent.setDescription(dct);
             newEvent.setAdresse(adre);
             Evnt.ModiferrEvent(newEvent, id_modif, cat);
             
         
           
             
             
             
         }  
                
            }
        }
    }
    
    
     public void delEvent()
    {
        int SelectedRowIndex = tableViewEv.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tableViewEv.getColumns().indexOf(id_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEv.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
            int id_supp = Integer.parseInt(IdCell);
            EvennementServ ec = new EvennementServ();
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous Supprimer event  dont l'ID : " + IdCell + " ?");
            Optional<ButtonType> result = A.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ec.supprimerEvent(id_supp);
                A.setAlertType(Alert.AlertType.INFORMATION);
                A.setContentText("event Supprimé ! ");
                A.show();
                
            }

        }
    }

    
}
