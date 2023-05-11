/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;


import Entities.Ingrediant;
import Entity.Evennement;
import Entity.EventCategory;
import Entity.passe;
import Service.EvennementServ;
import Service.EventCategServ;
import Service.mail;
import Service.passeServ;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class EvennementController implements Initializable {
    private static String username = "mailjavam@gmail.com";
    private static String password = "degfppsysconhzft";

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
    @FXML
    private Button ProfileB;
    @FXML
    private Button QRCODE;
    @FXML
    private Button imgbutton;
    
    private String imgP=null;

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
    ProfileB.setOnAction(event -> {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AdminDashBoard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ProfileB.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });
    
    
        
    }
    
    @FXML
   private void imgPick(javafx.event.ActionEvent event) 
   {
       FileChooser fc =new FileChooser();
    File file=fc.showOpenDialog(null);
        System.out.println(file.getAbsolutePath());
        imgP=file.getAbsolutePath();
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
               newEvent.setEventimg(imgP);
             Evnt.AjouterEvent(newEvent,cat);
             
         
           
             
             
             
         }        
    }
    
    @FXML
    public void AfficherEvent() {
    tableViewEv.getItems().clear();
    EvennementServ ev = new EvennementServ();
    List<Evennement> lE = ev.GetEvennements();
    
   
    ObservableList<Evennement> EVList = FXCollections.observableArrayList(lE);
id_event.setVisible(false);
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
    public void pid()
    {
         passeServ p=new passeServ();
                 List<Integer> l=p.GetUsersID(9);
                 List<String> TO = new ArrayList<String>();
                for (int i = 0; i < l.size(); i++) {
                System.out.println(l.get(i));
                //System.out.println(p.getMail(l.get(i)));
                TO.add(p.getMail(l.get(i)));
                //p.getMail(i);
                  }
                 for (int i = 0; i < TO.size(); i++) {
                System.out.println(TO.get(i));
               // System.out.println(p.getMail(l.get(i)));
               // TO.add(p.getMail(TO.get(i)));
                //p.getMail(i);
                  }
    }
    
    @FXML
     public void delEvent() throws MessagingException
    {
        int SelectedRowIndex = tableViewEv.getSelectionModel().getSelectedIndex();
        
        int ColumnIndex = tableViewEv.getColumns().indexOf(id_event);
        int ColumnIndex2 = tableViewEv.getColumns().indexOf(titre_event);
        
        
        Alert A = new Alert(Alert.AlertType.CONFIRMATION);
        if (SelectedRowIndex == -1) {
            A.setAlertType(Alert.AlertType.WARNING);
            A.setContentText("Selectionnez une colonne ! ");
            A.show();
        } else {
            String IdCell = tableViewEv.getColumns().get(ColumnIndex).getCellData(SelectedRowIndex).toString();
            int id_supp = Integer.parseInt(IdCell);
            String IdCell2 = tableViewEv.getColumns().get(ColumnIndex2).getCellData(SelectedRowIndex).toString();
            String body="an Event you plan on seeing has been cancelled  : " ;
           
            EvennementServ ec = new EvennementServ();
            A.setAlertType(Alert.AlertType.CONFIRMATION);

            A.setContentText("Voulez vous Supprimer event  dont l'ID : " + IdCell + " ?");
            Optional<ButtonType> result = A.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                
                 passeServ p=new passeServ();
                 List<Integer> l=p.GetUsersID(id_supp);
                 List<String> TO = new ArrayList<String>();
                for (int i = 0; i < l.size(); i++) {
                System.out.println(l.get(i));
                //System.out.println(p.getMail(l.get(i)));
                TO.add(p.getMail(l.get(i)));
                //p.getMail(i);
                  }
                sendEmail(TO,"event Supprimé ! " , "an Event you plan on seeing has been cancelled  : ");
                ec.supprimerEvent(id_supp);
                
                A.setAlertType(Alert.AlertType.INFORMATION);
                A.setContentText("event Supprimé ! ");
                A.show();
                
                
            }

        }
    }
     
     
    @FXML
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
    @FXML
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
     
    @FXML
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
      
    @FXML
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
       
    @FXML
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
             mail m=new mail();
            
             newP.setNomEvent(TitleCell);
             newP.setCode(codeP);
             newP.setPriEvent(priceE);
             pass.AjouterP(newP, id_Evnt,ConnectionController.idU);
             ev.updatenbrP(id_Evnt);
             m.mailto();
   
            }
        }
       }
       
       

        public static void sendEmail(List<String> recipients, String subject, String body) throws MessagingException {
        
       // your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
     protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
       return new javax.mail.PasswordAuthentication(username, password);
}
});

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        
        // add recipients
        Address[] recipientsAddress = new Address[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            recipientsAddress[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, recipientsAddress);

        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);

        System.out.println("Email sent successfully.");
    }
    @FXML
         private void QR(ActionEvent event) {
       // Ingrediant c = tableViewEv.getSelectionModel().getSelectedItem();
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myWeb = tableViewEv.getSelectionModel().getSelectedItem().toString();
        int width = 300;
        int height = 300;
        String fileType = "png";
        
        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            
            System.out.println("Success...");
            
        } catch (WriterException ex) {
            
        }
        
        ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        
        StackPane root = new StackPane();
        root.getChildren().add(qrView);
        
        Scene scene = new Scene(root, 350, 350);
        Stage newStage = new Stage();
        newStage.setTitle("Hello World!");
        newStage.setScene(scene);
        newStage.show();
    }
    
}
