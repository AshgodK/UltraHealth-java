/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import Entity.Users;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javaapp.JavaApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.Base;

import java.util.Properties;
import javafx.scene.control.TextField;
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
 * FXML Controller class
 *
 * @author pc
 */
public class SignUpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String usernameMail = "mailjavam@gmail.com";
private String passwordMail = "degfppsysconhzft";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
     private Users user;
    private boolean roleClient = false;
    private boolean roleDoctor = false;
    private boolean rolePatient = false;
    private boolean roleNutritionist = false;
    private Base db;

    private int id;
    private Date birthDay;
    private String cin, firstName, lastName, zone, email, password;
    

    @FXML
    private DatePicker birthDayField;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField cinField;

    @FXML
    private Label cinError;


    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNamefield;

    @FXML
    private Label emailError;

    @FXML
    private Label emptyVerify;


    @FXML
    private PasswordField passwordField;

    @FXML
    private Label roleLable;


    @FXML
    private TextField zoneField;

    @FXML
    private Button ClientBtn;

    @FXML
    private Button doctorBtn;

    @FXML
    private Button nutBTN;

    @FXML
    private Button patientBtn;

    @FXML
    void btnSignUpClicked(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        int cinConvert ;
        
        this.user = new Users();
        try {
           this.db = new Base("jdbc:mysql://localhost:3306/java", "root", "");
           db.connection();
       } catch (SQLException e) {
           System.err.println("Error connecting to MySQL database: " + e.getMessage());
       } catch (ClassNotFoundException e) {
           System.err.println("MySQL JDBC driver not found: " + e.getMessage());
       }
       
        
        Date date = this.user.converter(this.birthDayField.getValue());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Random rand = new Random();
        int randomId = rand.nextInt(9999);

        this.id = randomId;
        this.cin = this.cinField.getText();
        this.firstName = this.firstNameField.getText();
        this.lastName = this.lastNamefield.getText();
        this.zone = this.zoneField.getText();
        this.birthDay = sqlDate;
        this.email = this.emailField.getText();
        this.password = this.passwordField.getText();
        
        try{
            cinConvert = Integer.parseInt(this.cinField.getText());
        }catch(NumberFormatException ex){
            this.cinError.setText("CIN must be a number ");
        }

        if(roleClient == true){
            this.user.setRole("CLIENT");
        }else if(roleDoctor == true){
            this.user.setRole("DOCTOR");
        }else if(roleNutritionist == true){
            this.user.setRole("NUTRITIONIST");
        }else{
            this.user.setRole("PATIENT");
        }

        String sqlQuery = "SELECT * from users WHERE email = '"+this.email+"'";
        ResultSet result = this.db.useStatment(sqlQuery);
        if(result.next()){
            System.out.println("account already exist\n->ID : "+result.getInt(1));
            this.emailError.setText("Email allready Exist !");
        }else if(controle()){
            this.emptyVerify.setText("one or much field are empty please finish the sign up form !");
        }else if(!emailVerificationSyntax(this.email)){
            this.emailError.setText("you email needs to be at this format : example@gmail.com");
        }else{
            this.user.setId(this.id);
            this.user.setCin(this.cin);
            this.user.setFirstName(this.firstName);
            this.user.setLastName(this.lastName);
            this.user.setZone(this.zone);
            this.user.setBirthDay(this.birthDay);
            this.user.setEmail(this.email);
            this.user.setPassword(this.password);

            sqlQuery = "INSERT users (`id`, `cin`, `firstname`, `lastname`, `zone`, `birthday`, `role`, `email`, `password`) VALUES('"+this.user.getId()+"','"+this.user.getCin()+"','"+this.user.getFirstName()+"','"+this.user.getLastName()+"','"+this.user.getZone()+"','"+this.user.getBirthDay()+"','"+this.user.getRole()+"','"+this.user.getEmail()+"','"+this.user.getPassword()+"')";

        int res = this.db.insertIntoBase(sqlQuery);
        if(res==1){
            System.out.println(res);
            System.out.println("Console add /> add with success");
            //envoyer(this.email);
        }else{
            System.out.println("Console add /> Error Fail");
        }
            JavaApp.setRoot("connection");
        }

        
        
    }

    private boolean controle() {
        if(this.cinField.getText().isEmpty()&&this.firstNameField.getText().isEmpty()&&this.lastNamefield.getText().isEmpty()&&this.zoneField.getText().isEmpty()&&this.emailField.getText().isEmpty()&&this.passwordField.getText().isEmpty()){
            return true;
        }
        return false;
    }

    @FXML
    void ClientBtnClicked(ActionEvent event) {
        this.roleLable.setText(this.ClientBtn.getText());
        this.ClientBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
        public void handle(ActionEvent arg0) {
            roleClient = true;
        }

        });
         
    }

    @FXML
    void doctorBtnClicked(ActionEvent event) {
        this.roleLable.setText(this.doctorBtn.getText());
        this.doctorBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
        public void handle(ActionEvent arg0) {
            roleDoctor = true;
        }

        });

    }

    @FXML
    void nutBTNClicked(ActionEvent event) {
        this.roleLable.setText(this.nutBTN.getText());
        ClientBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
        public void handle(ActionEvent arg0) {
            roleNutritionist = true;
        }

        });

    }

    @FXML
    void patientBtnClicked(ActionEvent event) {
        this.roleLable.setText(this.patientBtn.getText());
        ClientBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
        public void handle(ActionEvent arg0) {
            rolePatient = true;
        }

        });

    }

    public boolean emailVerificationSyntax(String email){
       if(email.contains("@gmail.com") || email.contains("@yahoo.fr")|| email.contains("@esprit.tn")){
        return true;
       }
        return false;
    }
    public boolean roleClient() {
        return roleClient;
    }

    public boolean roleDoctor() {
        return roleDoctor;
    }

    public boolean roleNutritionist() {
        return roleNutritionist;
    }

    public boolean rolePatientt() {
        return rolePatient;
    }
    
    public void envoyer(String to) throws IOException {
// Etape 1 : Création de la session

       
       
       Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host","smtp.gmail.com");
props.put("mail.smtp.port","587");
Session session = Session.getInstance(props,
new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(usernameMail, passwordMail);
}
});
try {
// Etape 2 : Création de l'objet Message
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(usernameMail));
message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(to));
message.setSubject("compte");
//message.setText("Bonjour, ce message est un test ...");
// Etape 3 : Envoyer le message

Multipart multipart = new MimeMultipart();

// create the text part
MimeBodyPart textPart = new MimeBodyPart();
textPart.setText("your account has been created");

// create the pdf part
MimeBodyPart pdfPart = new MimeBodyPart();
//pdfPart.attachFile(new File("D:/NetBeans/JavaApplication3/"+pdf+".pdf")); // specify the file path of the pdf

// add the parts to the multipart message
multipart.addBodyPart(textPart);
//multipart.addBodyPart(pdfPart);

// add the multipart message to the email message
message.setContent(multipart);
Transport.send(message);
System.out.println("Message_envoye");
} catch (MessagingException e) {
throw new RuntimeException(e);
} 
       }
    
}
