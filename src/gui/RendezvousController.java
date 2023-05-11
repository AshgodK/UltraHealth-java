/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.Rendezvous;
import Service.servicerendez;
import java.io.IOException;
import util.MyConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;














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
    private TableColumn<Rendezvous, LocalTime> heuret;
  
    private TextField id;
   @FXML
    private DatePicker date;
    private TextField heure;
    
    private servicerendez rcd;  
    
   
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
    //private Spinner<?> heureSpinner;
    @FXML
    private TextField timeH;
    @FXML
    private Spinner<?> heureSpinner;
    @FXML
    private ImageView home;
    @FXML
    private Button consult;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         rcd = new servicerendez(); // Initialiser la variable rcd
        rcd = new servicerendez(); // Initialiser la variable rcd

        show();
     
    
    
    
   
  ObservableList<Rendezvous> rendezvousList;
MyConnection cnxfv = null;
Statement st = null;
servicerendez rcd = new servicerendez();


}

     @FXML
private void selection(javafx.scene.input.MouseEvent event) {
    index = tablerendez.getSelectionModel().getSelectedIndex();
    if (index <= -1) {
        return;
    }
    Rendezvous r = data.get(index);
    //id.setText(Integer.toString(r.getId()));
}

    private void entier(KeyEvent event) {
        if (!event.getCharacter().matches("[\\d+]")) {
            event.consume();
        }
    }


    private void str(KeyEvent event) {
        if (!event.getCharacter().matches("[a-zA-Z]")) {
            event.consume();
        }
    }

 /*   @FXML
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
*/
    
    
    
  /*@FXML
    private void Ajouter(ActionEvent event) {
       
        
    String t1 = id.getText();
        LocalDate selectedDate = date.getValue();
        LocalTime selectedHeure = heure.getText();

        if ( selectedDate == null || selectedHeure.isEmpty()) {
            // Alert if any field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields.");
            alert.showAndWait();
        } else {
            int var2 = Integer.parseInt(t1);
            LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.parse(selectedHeure));

            servicerendez rc = new servicerendez(); // Initialize the service class
            Rendezvous r = new Rendezvous(5, dateTime, LocalTime.parse(selectedHeure));
            rc.Ajouterrendezvous(r);

            updateTable();
        }
    }
*/
    
  @FXML
private void Ajouter(ActionEvent event) {
    LocalDate selectedDate = date.getValue();
    String h = timeH.getText();
    LocalTime selectedHeure = null;
    try {
        selectedHeure = LocalTime.parse(h, DateTimeFormatter.ofPattern("HH:mm:ss"));
    } catch (DateTimeParseException e) {
        // Alert if time string is not in ISO_LOCAL_TIME format
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the time in HH:mm:ss format.");
        alert.showAndWait();
        return;
    }

    if (selectedDate == null) {
        // Alert if date field is empty
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a date.");
        alert.showAndWait();
        return;
    }

    LocalDateTime dateTime = LocalDateTime.of(selectedDate, selectedHeure);

    servicerendez rc = new servicerendez(); // Initialize the service class
    Rendezvous r = new Rendezvous(5, dateTime, selectedHeure);
    rc.Ajouterrendezvous(r);

    updateTable();
}



    @FXML
    private void vider(ActionEvent event) {
       // id.clear();
        date.setValue(null);
        timeH.clear();
      
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
          
         // Rendezvous v = rendezvousList.get(index);

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
        alert.setTitle("Confirmation to modify");
        alert.setHeaderText("Are you sure you want to modify this item?");
        alert.setContentText("This action is irreversible.");
        ButtonType ouiButton = new ButtonType("Yes");
        ButtonType nonButton = new ButtonType("No");
        alert.getButtonTypes().setAll(ouiButton, nonButton);
        
         // Créer une instance de servicerendez
       //  servicerendez rc = new servicerendez();
         
        
        
       


        // show the alert and wait for the user's response
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ouiButton) {
                servicerendez rc = new servicerendez();
                 // Appeler la méthode Recuperer() sur l'instance rc
             List<Rendezvous> rendezvousList = rc.Recuperer();
             
             
         // Utilisez rendezvousList comme nécessaire
          Rendezvous r = rendezvousList.get(index);
                String var1 = id.getText();
                LocalDate selectedDate = date.getValue();
                String selectedHeure = heure.getText();

                if (var1.isEmpty() || selectedDate == null || selectedHeure.isEmpty()) {
                    // Alert if any field is empty
                    Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields.");
                    emptyFieldsAlert.showAndWait();
                } else {
                    int var5 = Integer.parseInt(var1);
                    LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.parse(selectedHeure));


                    rc.modifierrendezvous(r, var5, dateTime);

                    updateTable();
                }
            } else if (reponse == nonButton) {
                // User clicked "No", do nothing
            }
        });
    }

  private void show() {
    try {
        String requete = "SELECT id, date_rdv, heure FROM rendez_vous";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            int id = rs.getInt("id");
            LocalDateTime date_rdv = rs.getTimestamp("date_rdv").toLocalDateTime();
            LocalTime heure = rs.getTime("heure").toLocalTime();
            data.add(new Rendezvous(id, date_rdv, heure));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }


    
    idt.setCellValueFactory(new PropertyValueFactory<Rendezvous, Integer>("id"));
    datet.setCellValueFactory(new PropertyValueFactory<Rendezvous, String>("date_rdv"));
    heuret.setCellValueFactory(new PropertyValueFactory<Rendezvous, LocalTime>("heure"));

    tablerendez.setItems(data);
}
  
  
  private void updateTable() {
    data.clear();
    data.addAll(rcd.Recuperer());
    tablerendez.setItems(data);
}

    @FXML
    private void goHome(MouseEvent event) {
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
    private void goconsult(MouseEvent event) {
         try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/consultation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) consult.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

}
  
