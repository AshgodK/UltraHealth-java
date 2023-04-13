/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entity.Rendezvous;
import Util.MyConnection;
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
public class servicerendez implements Irendezvous<Rendezvous>{
 Connection cnxfl;

    public servicerendez() {
                cnxfl=MyConnection.getInstance().getCnx();
    }

   
    
    @Override
    public void Ajouterrendezvous(Rendezvous r) {
     try {
                String req = "insert into rendez(id, date_rdv,  etat, message, type_lieu)"
                        +"values('"+r.getId()+"','"+r.getDate_rdv()+"','"+r.getEtat()+"','"+r.getMessage()+"','"+r.getType_lieu()
                       +"')";
                Statement st = cnxfl.createStatement();
                st.executeUpdate(req);
                System.out.println("rendez-vous ajouter avec succ");
            } catch (SQLException ex) {
                
                System.out.println(ex.getMessage());        }   }

    @Override
    public void modifierrendezvous(Rendezvous l,  int id,String etat, String message, String type_lieu) {
     try {
            String req = " UPDATE rendez SET " + "id=?,  etat=?, message = ? ,type_lieu=?  where id=" + l.getId();
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
          
            
          pst.setInt(1,id);
            pst.setString(2,etat);
            
               pst.setString(3, message);
                pst.setString(4, type_lieu);
             
            pst.executeUpdate();
            System.out.println("le rendez-vous   modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }   
    }

    @Override
    public void supprimerrendez(Rendezvous r) {
    try {
            String req = "DELETE FROM rendez WHERE id=" + r.getId();
            Statement st = MyConnection.getInstance().getCnx().createStatement();

            st.executeUpdate(req);
            System.out.println(" la rendez-vous esr   supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Rendezvous> Recuperer() {
       ObservableList<Rendezvous> myList = FXCollections.observableArrayList();
        try {

            String requete2 = "Select  id,date_rdv,  etat, message, type_lieu  FROM rendez";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
                    Rendezvous rec = new Rendezvous();
                      rec.setId(rs.getInt("id"));
                   rec.setDate_rdv(rs.getString("date_rdv"));
                   rec.setEtat(rs.getString("etat"));
                   rec.setMessage(rs.getString("message"));
                   rec.setType_lieu(rs.getString("type_lieu"));
                   
              
                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return myList;

    
}
    
}
