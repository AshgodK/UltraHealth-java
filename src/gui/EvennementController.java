/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


import Entity.Evennement;
import Entity.EventCategory;
import Entity.passe;
import Service.EvennementServ;
import Service.EventCategServ;
import Service.passeServ;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TextField prix;
      @FXML
    private TextField nbrP;
    @FXML
    private TextField sID;
    @FXML
    private TextArea desc;
    @FXML
    private ChoiceBox<String> categorie;
     @FXML
    private Button goToCat;
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
    @FXML
    private TableColumn<?, ?> prix_event;
     @FXML
    private TableColumn<?, ?> nbrP_event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EventCategServ ev = new EventCategServ();
    categorie.getItems().addAll(ev.GetCategoriesTitle());
        
    goToCat.setOnAction(event -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/eventCateg.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) goToCat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });
        
    }  
    
    @FXML
    private void ajouter(javafx.event.ActionEvent event) 
    {
        
         String ttr=titre.getText();
        String selectedCategory = categorie.getValue();
        String dct=desc.getText();
        String adre=adr.getText();
        String price=prix.getText();
        String nbP=nbrP.getText();
        
        
            LocalDate today = LocalDate.now();       
            LocalDate dateD=date_debut.getValue();            
            LocalDate dateF=date_fin.getValue();
            
            
            
        
        
         if (price.isEmpty()||nbP.isEmpty()||ttr.isEmpty()||  dct.isEmpty()||  selectedCategory.isEmpty()||adre.isEmpty()||dateF == null||dateD == null) 
         {
            // Alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
           
         } 
         else if (dateD.isBefore(today)||dateF.isBefore(dateD)) {
                  Alert alert = new Alert(Alert.AlertType.WARNING, "check date.");
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
              newEvent.setPrix(Float.parseFloat(price));
               newEvent.setNbrP(Integer.parseInt(nbP));
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
    prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
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
     
     
     public void AfficherEventByID() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    String id=sID.getText();
    List<Evennement> lE = ev.GetEvennementsByID(id);
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
    dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
     public void AfficherEventByTitle() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    String t=sID.getText();
    List<Evennement> lE = ev.GetEvennementsByTitle(t);
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
    dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
     
      public void AfficherEventByCat() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    String t=sID.getText();
    EventCategServ ecs= new EventCategServ();
             
             int cat = ecs.GetCategorieID(t);
             if (cat == -1) {
    // Category not found, show error message and return
    Alert alert = new Alert(Alert.AlertType.ERROR, "Selected category not found.");
    alert.showAndWait();
    return;
}
    List<Evennement> lE = ev.GetEvennementsBycat(cat);
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
    dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
      
       public void AfficherEventTriDD() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    List<Evennement> lE = ev.GetEvennementsTrierDD();
    
   
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);

    id_event.setCellValueFactory(new PropertyValueFactory<>("id"));
    titre_event.setCellValueFactory(new PropertyValueFactory<>("titre"));
    dec_event.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateD_event.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
    dateF_event.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    cat_event.setCellValueFactory(new PropertyValueFactory<>("cat_title"));
    adr_event.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    prix_event.setCellValueFactory(new PropertyValueFactory<>("prix"));
    nbrP_event.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
    tableViewEv.setItems(EVList);
}
       
       public void buyP()
       {
           int SelectedRowIndex = tableViewEv.getSelectionModel().getSelectedIndex();
        
        int ColumnID = tableViewEv.getColumns().indexOf(id_event);
        int ColumnTitle = tableViewEv.getColumns().indexOf(titre_event);
        int ColumnPrice = tableViewEv.getColumns().indexOf(prix_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEv.getColumns().get(ColumnID).getCellData(SelectedRowIndex).toString();
            String TitleCell = tableViewEv.getColumns().get(ColumnTitle).getCellData(SelectedRowIndex).toString();
           String PriceCell = tableViewEv.getColumns().get(ColumnPrice).getCellData(SelectedRowIndex).toString();
            int id_Evnt = Integer.parseInt(IdCell);
           float priceE= Float.parseFloat(PriceCell);
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous acheter un ticker a evennement  : " + TitleCell + " ?");
            Optional<ButtonType> result = A.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
              
               
               UUID uuid = UUID.randomUUID();
               String codeP=uuid.toString();
             
            EvennementServ ev = new EvennementServ();
             passeServ pass=new passeServ();
             
             passe newP = new passe();
            
             newP.setNomEvent(TitleCell);
             newP.setCode(codeP);
             newP.setPriEvent(priceE);
             pass.AjouterEvent(newP, id_Evnt);
             ev.updatenbrP(id_Evnt);
             
             
         
           
             
             
             
           
                
            }
        }
       }

    
}
