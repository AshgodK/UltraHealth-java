/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import Entities.Ingrediant;

import Service.ServiceIngrediant;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;


import com.itextpdf.text.Paragraph;
import java.io.File;
import java.util.Properties;
import javafx.scene.Node;
import javafx.scene.control.Alert;



import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




/**
 *
 * @author Mega-PC
 */
public  class FXMLIngrediantController  implements Initializable {
    private String username = "mailjavam@gmail.com";
private String password = "degfppsysconhzft";

    private Label label;
   
    @FXML
    private TextField tftitre;
    @FXML
    private Button stat;
    @FXML
    private TextField tfcaloris;
    @FXML
    private TextField tfpoids;
    @FXML
    private TableColumn<Ingrediant, Integer> idt;
    @FXML
    private TableColumn<Ingrediant, String> ttitre;
    @FXML
    private TableColumn<Ingrediant, Integer> tcaloris;
    @FXML
    private TableColumn<Ingrediant, Integer> tpoids;
    @FXML
    private TableView<Ingrediant> tablec;
    @FXML
    private TextField idsup;
    @FXML
    private TextField tfsearch;
    @FXML
    private TextField tftype;
    private int vartri = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    private void AjouterIngrediant(ActionEvent event) {
        ServiceIngrediant sc = new ServiceIngrediant();
        Ingrediant c = new Ingrediant();
        
        String titre = tftitre.getText().trim();
    if (titre.matches("[a-zA-Z\\s]+")) {
        c.setTitre(titre);
    } else {
        // Display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Titre");
        alert.setContentText("Titre must contain only letters");
        alert.showAndWait();
        return;
    }

    // Check that Caloris is a valid integer
    try {
        int caloris = Integer.parseInt(tfcaloris.getText().trim());
        c.setCaloris(caloris);
    } catch (NumberFormatException e) {
        // Display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Caloris");
        alert.setContentText("Caloris must be an integer");
        alert.showAndWait();
        return;
    }

    // Check that Poids is a valid integer
    try {
        int poids = Integer.parseInt(tfpoids.getText().trim());
        c.setPoids(poids);
    } catch (NumberFormatException e) {
        // Display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Poids");
        alert.setContentText("Poids must be an integer");
        alert.showAndWait();
        return;
    }

        sc.AjouterIngrediant(c);
        this.AfficherIngrediant(event);
    }

    @FXML
    private void AfficherIngrediant(ActionEvent event) {
        ServiceIngrediant sc = new ServiceIngrediant();
        ObservableList<Ingrediant> ingredients = sc.AfficherIngrediant();
        
        ttitre.setCellValueFactory(new PropertyValueFactory<Ingrediant, String>("titre"));
        tcaloris.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("caloris"));
        tpoids.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("poids"));
        tablec.setItems(ingredients);

    }

    @FXML
    private void selectionner(MouseEvent event) {

        Ingrediant c = tablec.getSelectionModel().getSelectedItem();

        idsup.setText(c.getTitre());
        tftitre.setText(c.getTitre());
        tfcaloris.setText(String.valueOf(c.getCaloris()));
        tfpoids.setText(String.valueOf(c.getPoids()));
        
    }

    @FXML
    private void SupprimerIngrediant(ActionEvent event) {
        ServiceIngrediant sc = new ServiceIngrediant();
        sc.supprimerIngrediant(idsup.getText());
        this.AfficherIngrediant(event);

    }
    
        

     @FXML
    private StackPane root;
    
    @FXML
    private void CreerQR(ActionEvent event) {
        Ingrediant c = tablec.getSelectionModel().getSelectedItem();
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myWeb = c.toString();
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
    // create a new PDF document
    @FXML
    private void CreerPDF(ActionEvent event) {
        Ingrediant c = tablec.getSelectionModel().getSelectedItem();
        
        
        String myWeb = c.toString();
         Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("try.pdf"));
            document.open();
            document.add(new Paragraph(myWeb));
            document.close();
            System.out.println("PDF created successfully");
            envoyer("salim.aloui@esprit.tn", "try");
            System.out.println("mail sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
         public void envoyer(String to,String pdf) throws IOException {
// Etape 1 : Création de la session

       
       
       Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host","smtp.gmail.com");
props.put("mail.smtp.port","587");
Session session = Session.getInstance(props,
new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(username, password);
}
});
try {
// Etape 2 : Création de l'objet Message
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(username));
message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(to));
message.setSubject("Ingrediant");
//message.setText("Bonjour, ce message est un test ...");
// Etape 3 : Envoyer le message

Multipart multipart = new MimeMultipart();

// create the text part
MimeBodyPart textPart = new MimeBodyPart();
textPart.setText("your PDFt has been created");

// create the pdf part
MimeBodyPart pdfPart = new MimeBodyPart();
pdfPart.attachFile(new File("D:/NetBeans/JavaApp/"+pdf+".pdf")); // specify the file path of the pdf
//System.out.println("D:/NetBeans/JavaApp/PDF"+pdf+".pdf");
// add the parts to the multipart message
multipart.addBodyPart(textPart);
multipart.addBodyPart(pdfPart);

// add the multipart message to the email message
message.setContent(multipart);
Transport.send(message);
System.out.println("Message_envoye");
} catch (MessagingException e) {
throw new RuntimeException(e);
} 
       }

        
       


    

    @FXML
    private void ModifierIngrediant(ActionEvent event) {
        ServiceIngrediant sc = new ServiceIngrediant();
    Ingrediant c = tablec.getSelectionModel().getSelectedItem();
    
        String titre = tftitre.getText().trim();
    if (titre.matches("[a-zA-Z\\s]+")) {
        c.setTitre(titre);
    } else {
        // Display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Titre");
        alert.setContentText("Titre must contain only letters");
        alert.showAndWait();
        return;
    }

    // Check that Caloris is a valid integer
    try {
        int caloris = Integer.parseInt(tfcaloris.getText().trim());
        c.setCaloris(caloris);
    } catch (NumberFormatException e) {
        // Display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Caloris");
        alert.setContentText("Caloris must be an integer");
        alert.showAndWait();
        return;
    }

    // Check that Poids is a valid integer
    try {
        int poids = Integer.parseInt(tfpoids.getText().trim());
        c.setPoids(poids);
    } catch (NumberFormatException e) {
        // Display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Poids");
        alert.setContentText("Poids must be an integer");
        alert.showAndWait();
        return;
    }
    
    sc.ModifierIngrediant(c);
    this.AfficherIngrediant(event);
    }

    @FXML
    private void searchkey(KeyEvent event) {
        ServiceIngrediant sc = new ServiceIngrediant();
        ObservableList<Ingrediant> ingredients = sc.search(tfsearch.getText());
        
        ttitre.setCellValueFactory(new PropertyValueFactory<Ingrediant, String>("titre"));
        tcaloris.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("caloris"));
        tpoids.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("poids"));
        tablec.setItems(ingredients);
    }

    @FXML
    private void tributton(MouseEvent event) {
        ServiceIngrediant sc = new ServiceIngrediant();
        ObservableList<Ingrediant> ingredients;
        if (vartri == 1) {
            vartri = 0;
            ingredients = sc.triasc();
        
        ttitre.setCellValueFactory(new PropertyValueFactory<Ingrediant, String>("titre"));
        tcaloris.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("caloris"));
        tpoids.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("poids"));
        tablec.setItems(ingredients);

        } else {
            vartri = 1;
            ingredients = sc.triadsc();
        
        ttitre.setCellValueFactory(new PropertyValueFactory<Ingrediant, String>("titre"));
        tcaloris.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("caloris"));
        tpoids.setCellValueFactory(new PropertyValueFactory<Ingrediant, Integer>("poids"));
        tablec.setItems(ingredients);

        }

    }
    
    @FXML
    private void handleGoToPlatButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("plat.fxml"));
    Parent root = loader.load();
    FXMLPlatController controller = loader.getController();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}
    @FXML
    private void handleGoToMenuButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
    Parent root = loader.load();
    FXMLMenuController controller = loader.getController();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}
    
   
}
