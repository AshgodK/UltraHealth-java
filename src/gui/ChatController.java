/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author dhafeur
 */
public class ChatController implements Initializable {
    @FXML
    private AnchorPane an;
    @FXML
    private TextArea textchat;
    @FXML
    private TextField ASK;
private Map<String, String> responses;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        responses = new HashMap<>();
        // TODO
         responses.put("bonjour", "comment puis je vous aidez");
        responses.put("donner moi les nombres des rendez-vous? ", "100");
        responses.put("donnez moi le nombre des entrepreneur", "95");
        responses.put("salut bot", "comment puis je vous aidez? ");
         responses.put("qui etes vous?", "je suis un bot creer par devil's ");
          responses.put("AppMonsters", "nous sommes une equipe de cinq membres qui fait une application SandBox  ");
             TextFields.bindAutoCompletion(ASK, responses.keySet());
           TextField textField =  TextFields.createClearablePasswordField();
  
    }    

    @FXML
    private void UserA(ActionEvent event) {
       String input = ASK.getText();
        String response = responses.getOrDefault(input, "désolé j'ai pas la réponse ");
        textchat.appendText("Utilisateur: " + input + "\n");
        textchat.appendText("Chatbot: " + response + "\n\n");
        ASK.clear();}
    
}