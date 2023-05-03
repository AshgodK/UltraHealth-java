/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.Rendezvous;
import Service.servicerendez;
import Util.MyConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RendezvousController implements Initializable {

    @FXML
    private TableView<Rendezvous> tablerendez;
    @FXML
    private TableColumn<Rendezvous,Integer> idt;
    @FXML
    private TableColumn<Rendezvous, String> datet;
    @FXML
    private TableColumn<Rendezvous, String> etatt;
    @FXML
    private TableColumn<Rendezvous, String> messaget;
    @FXML
    private TableColumn<Rendezvous, String> typet;
    @FXML
    private TextField id;
    @FXML
    private DatePicker date;
    @FXML
    private TextField et;
    @FXML
    private TextField msg;
    @FXML
    private TextField ty;
    @FXML
    private Button ajou;
    @FXML
    private Button vid;
    @FXML
    private Button sup;
    @FXML
    private Button modi;
 public ObservableList<Rendezvous> data=FXCollections.observableArrayList();
     int index=-1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        show();
    }  
    
    
    
    
    ObservableList<Rendezvous> Rendezvous;
     MyConnection cnxfv = null;
     Statement st = null;
     servicerendez   rcd = new servicerendez();
       public void updateTable(){
        Rendezvous= (ObservableList<Rendezvous>) rcd.Recuperer();
        tablerendez.getItems().setAll(Rendezvous);
       }

    @FXML
    private void selection(MouseEvent event) {
      index=tablerendez.getSelectionModel().getSelectedIndex();
         if(index<=-1){
         return ;
     }
    id.setText(idt.getCellData(index).toString());
   
     et.setText(etatt.getCellData(index));
     msg.setText(messaget.getCellData(index));
     ty.setText(typet.getCellData(index));
    }
    @FXML
    private void entier(KeyEvent event) {
     if (event.getCharacter().matches(("[^\\e\t\r\\d+$]"))){
    event.consume();
    }  
    }

    @FXML
    private void str(KeyEvent event) {
    if (event.getCharacter().matches("[^a-zA-Z]")){
    event.consume();
                }
    }

    @FXML
    private void stra(KeyEvent event) {
        if (event.getCharacter().matches("[^a-zA-Z]")){
    event.consume();
                }
    }

    @FXML
    private void straaa(KeyEvent event) {
        if (event.getCharacter().matches("[^a-zA-Z]")){
    event.consume();
                }
        
        
        
    }

    @FXML
    private void ajouter(ActionEvent event) {
    
        String t1 = id.getText();
            
           
       
            
            
        if (t1.isEmpty()||   et.getText().isEmpty()||msg.getText().isEmpty()||typet.getText().isEmpty()||date.getValue()==null) {
            // Alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
           
        } else { 
            servicerendez rc = new servicerendez();
            
        
                
  
        int var2=Integer.parseInt(t1);
      
        String var3 =  et.getText();
        String var4 =  msg.getText();
            String var5 =  ty.getText();       
        
        
        
      
         
        

      Rendezvous r =new Rendezvous();
        r.setId(var2);
        r.setDate_rdv(date.getValue().toString());
      
       r.setEtat(var3);
      r.setMessage(var4);
      r.setType_lieu(var5);
     

      rc.Ajouterrendezvous(r);
        
        
     

     
     updateTable();
       
       
        }
    
    }

    @FXML
    private void vider(ActionEvent event) {
        id.clear();
       et.clear();
       msg.clear();
       ty.clear();
    }

    @FXML
    private void suprimer(ActionEvent event) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet élément ?");
        alert.setContentText("Cette action est irréversible.");

        // ajouter des boutons personnalisés à l'alerte
        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");

        alert.getButtonTypes().setAll(ouiButton, nonButton);

        // afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(reponse ->{
            if (reponse == ouiButton) {
          Rendezvous v = new Rendezvous();   
              v= tablerendez.getSelectionModel().getSelectedItem();
              rcd.supprimerrendez(v);
              updateTable();}
              else if (reponse == nonButton) {
                // l'utilisateur a cliqué sur "Non", ne rien faire
            }
        });
    }

    @FXML
    private void modifier(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modifier");
        alert.setHeaderText("Êtes-vous sûr de vouloir modifier cet élément ?");
        alert.setContentText("Cette action est irréversible.");
        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");
           alert.getButtonTypes().setAll(ouiButton, nonButton);

        // afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ouiButton){

       servicerendez rc = new servicerendez();
         String var1=id.getText();
        String var2=et.getText();
        String var3=msg.getText();
        String var4=ty.getText();
        String var6=date.getValue().toString();
        
        int var5=Integer.parseInt(var1);
      
       
      Rendezvous r = new Rendezvous() ;
         r.setId(var5);
      r.setEtat(var2);
      r.setMessage(var3);
      r.setType_lieu(var4);
      r.setDate_rdv(var6);
      
        r=tablerendez.getSelectionModel().getSelectedItem();
        
        rc.modifierrendezvous(r,var5,var2,var3,var4,var6);
        updateTable();
        }
      else if (reponse == nonButton) {
                // l'utilisateur a cliqué sur "Non", ne rien faire
            }
        }); 
        
    
    }

    private void show() {
        try {
            String requete="SELECT id, date_rdv,  etat, message, type_lieu FROM rendez"; 
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(requete);
            while(rs.next()){
              data.add(new Rendezvous(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
               
                  idt.setCellValueFactory(new PropertyValueFactory<Rendezvous,Integer>("id")); 
                 datet.setCellValueFactory(new PropertyValueFactory<Rendezvous,String>("date_rdv"));
                   etatt.setCellValueFactory(new PropertyValueFactory<Rendezvous,String>("etat")); 
                     messaget.setCellValueFactory(new PropertyValueFactory<Rendezvous,String>("message")); 
                      typet.setCellValueFactory(new PropertyValueFactory<Rendezvous,String>("type_lieu")); 
                 tablerendez.setItems(data);
    }

    
}
