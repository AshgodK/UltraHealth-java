/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Consultation;
import Entity.Rendezvous;
import Service.serviceconsultation;
import util.MyConnection;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.FontsResourceAnchor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import org.controlsfx.control.textfield.TextFields;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ConsultationController implements Initializable {

   
    @FXML
    private Button ajout;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField numero;
    @FXML
    private TextField description;
    @FXML
    private Text nom2;
    @FXML
    private Text prenom2;
    @FXML
    private Text numero2;
    @FXML
    private Text description2;
    @FXML
    private TableColumn<Consultation,Integer> tableviewId;
    @FXML
    private TableColumn<Consultation,String> tableviewnom;
    @FXML
    private TableColumn<Consultation,String> tableviewPrenom;
    @FXML
    private TableColumn<Consultation,Integer> tableviewNum;
    @FXML
    private TableColumn<Consultation,String> tableviewDesc;
    @FXML
    private TableView<Consultation> tableconsul;
    public ObservableList<Consultation> dataCons=FXCollections.observableArrayList();
    int index=-1;
    @FXML
    private Button home;
    @FXML
    private Button createPdf;
    
   

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
   
    }
    
    
     MyConnection cnxfv = null;
     Statement st = null;
     serviceconsultation ser= new serviceconsultation();
     ObservableList<Consultation> Consultation;
     
      @FXML
      private void selection(MouseEvent event) {
      index=tableconsul.getSelectionModel().getSelectedIndex();
         if(index<=-1){
         return ;
     }
    //id.setText(idt.getCellData(index).toString());
   
     nom.setText(tableviewnom.getCellData(index));
     prenom.setText(tableviewPrenom.getCellData(index));
     description.setText(tableviewDesc.getCellData(index));
     numero.setText(tableviewNum.getCellData(index).toString());

    }
     
      

  @FXML
private void ajout(ActionEvent event) {
    String t1 = numero.getText();

    if (nom.getText().isEmpty() || prenom.getText().isEmpty() || numero.getText().isEmpty() || description.getText().isEmpty()) {
        // Alerte si un champ est vide
        Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
        alert.showAndWait();
    } else {
        serviceconsultation rc = new serviceconsultation();
        String var1 = nom.getText();
        String var2 = prenom.getText();
        String var4 = description.getText();
        String var6 = numero.getText();
        int num = Integer.parseInt(var6);

        Consultation c = new Consultation();
        c.setNom(var1);
        c.setPrenom(var2);
        int tl = Integer.parseInt(t1);
        c.setDescription(var4);
        c.setNum_seance(num);

        rc.Ajouterconsultation(c);
    }
}
public void updateTableCons(){
        Consultation= (ObservableList<Consultation>) ser.Recupererconsultation();
        tableconsul.getItems().setAll(Consultation);
       }
@FXML
private void supprimerCons(ActionEvent event) {
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
          Consultation v = new Consultation();   
              v= tableconsul.getSelectionModel().getSelectedItem();
              ser.supprimerconsultation(v);
              updateTableCons();}
              else if (reponse == nonButton) {
                // l'utilisateur a cliqué sur "Non", ne rien faire
            }
        });
    }

private void show() {
        try {
            String requete="Select  id,nom,prenom,numero, description  FROM consultation"; 
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(requete);
            while(rs.next()){
              dataCons.add(new Consultation(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(5),rs.getInt(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
               
                  tableviewId.setCellValueFactory(new PropertyValueFactory<Consultation,Integer>("id")); 
                 tableviewnom.setCellValueFactory(new PropertyValueFactory<Consultation,String>("nom"));
                   tableviewPrenom.setCellValueFactory(new PropertyValueFactory<Consultation,String>("prenom")); 
                     tableviewDesc.setCellValueFactory(new PropertyValueFactory<Consultation,String>("description")); 
                      tableviewNum.setCellValueFactory(new PropertyValueFactory<Consultation,Integer>("num_seance")); 
                 tableconsul.setItems(dataCons);
    }

@FXML
    private void vider(ActionEvent event) {
        nom.clear();
       prenom.clear();
       numero.clear();
       description.clear();
    }

     @FXML
    private void modifierCons(ActionEvent event) {
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

       serviceconsultation rc = new serviceconsultation();
         String var1=nom.getText();
        String var2=prenom.getText();
        String var3=description.getText();
        String var4=numero.getText();
        
        
        int var5=Integer.parseInt(var4);
      
       
      Consultation r = new Consultation() ;
         //r.setId(var5);
      r.setNom(var1);
      r.setPrenom(var2);
      r.setDescription(var3);
       r.setNum_seance(var5);
      
      
        r=tableconsul.getSelectionModel().getSelectedItem();
        
        ser.modifierconsultation(r,var1,var2,var5,var3);
        updateTableCons();
        }
      else if (reponse == nonButton) {
                // l'utilisateur a cliqué sur "Non", ne rien faire
            }
        }); 
        
    
    }

    
    
    @FXML
   private void CreatePDF(ActionEvent event) {
       
       String det = tableconsul.getSelectionModel().getSelectedItem().toString();
         Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("pdf/consultation.pdf"));
            document.open();
            document.add(new Paragraph(det));
            document.close();
            System.out.println("PDF created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    @FXML
    private void home(MouseEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/userDashBoard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) home.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void modifierCons(MouseEvent event) {
    }

   
    
}
