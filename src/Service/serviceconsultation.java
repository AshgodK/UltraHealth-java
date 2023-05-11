/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Consultation;
import Entity.Rendezvous;
import util.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ASUS
 */
public class serviceconsultation implements Iconsultation<Consultation>
{
    
    Connection cnxfl;

    public serviceconsultation() {
                cnxfl=MyConnection.getInstance().getCnx();
    }

   

    

    @Override
    public void Ajouterconsultation(Consultation c) {
         try {
                String req = "insert into consultation(nom,prenom,numero,description)"
                        +"values('"+c.getNom()+"','"+c.getPrenom()+"','"+c.getNum_seance()+"','"+c.getDescription()
                       +"')";
                Statement st = cnxfl.createStatement();
                st.executeUpdate(req);
                System.out.println("consultation  ajouteé avec succeés");
            } catch (SQLException ex) {
                
                System.out.println(ex.getMessage()); 
            }  
    }

    @Override
    public void modifierconsultation(Consultation l,String nom, String prenom, int num_seance, String description) {
         try {
            String req = "UPDATE consultation SET " + "  nom=?, prenom= ? ,numero=? ,description=?  where id=" + l.getId();
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
          
            
          
           pst.setString(1,nom);
            
               pst.setString(2,prenom);
                pst.setInt(3,num_seance);
                 pst.setString(4,description);
                
             
            pst.executeUpdate();
            System.out.println("la consultation est   modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }

    @Override
    public void supprimerconsultation(Consultation c) {
        try {
            String req = "DELETE FROM consultation WHERE id=" + c.getId();
            Statement st = MyConnection.getInstance().getCnx().createStatement();

            st.executeUpdate(req);
            System.out.println(" la consultation est   supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Consultation> Recupererconsultation() {
        
         ObservableList<Consultation> myList = FXCollections.observableArrayList();
        try {

            String requete2 = "Select  id,nom,prenom,numero, description  FROM consultation";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
                    Consultation con = new Consultation();
                      con.setId(rs.getInt("id"));
                   con.setNom(rs.getString("nom"));
                   con.setPrenom(rs.getString("prenom"));
                   con.setNum_seance(rs.getInt("numero"));
                   con.setDescription(rs.getString("description"));
                   
              
                myList.add(con);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return myList;

    
    }
    
}
