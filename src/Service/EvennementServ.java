/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.sql.Connection;
import java.sql.Statement;
import util.MyConnection;
import java.sql.SQLException;
import Entity.Evennement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author pc
 */
public class EvennementServ extends Evennement
{
    
    Connection cnx;
     public void EvennementServ()
     {
         cnx=MyConnection.getInstance().getCnx();
     }
    
    
     public void AjouterEvent(Evennement e,int id_cat)
    {
     try {
          cnx=MyConnection.getInstance().getCnx();
          String req = "insert into evennement(date_debut,  date_fin, adresse, category_id, titre, description)"
                        +"values('"+e.getDate_deb()+"','"+e.getDate_fin()+"','"+e.getAdresse()+"','"+id_cat+"','"+e.getTitre()+"','"+e.getDescription()
                       +"')";
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("event ajouter avec succ");
            } catch (SQLException ex) {
                
                System.out.println(ex.getMessage());        
            }   
    
    
    }
     
      public List<Evennement> GetEvennements() 
    {
       List<Evennement> list=new ArrayList<>();
        try {
            cnx=MyConnection.getInstance().getCnx();

            String req = "SELECT * FROM evennement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                    Evennement e = new Evennement();
                   Long id = rs.getLong("id");
        String date_debut = rs.getString("date_debut");
        String date_fin = rs.getString("date_fin");
        String adresse = rs.getString("adresse");
        int category_id = rs.getInt("category_id");
        String titre = rs.getString("titre");
        String decription = rs.getString("description");
        
        
         e.setId(id);
            e.setDate_deb(date_debut);
            e.setDate_fin(date_fin);
            e.setAdresse(adresse);
            e.setTitre(titre);
            e.setDescription(decription);
            EventCategServ ec=new EventCategServ();
            e.setCat_title(ec.GetCategorieTitre(category_id));
                  
                  
              
                list.add(e);
                

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return list;
    }
      
      public void ModiferrEvent(Evennement e,int id,int id_cat) {
    try {
        cnx = MyConnection.getInstance().getCnx();
        String req = "UPDATE evennement SET titre=?, description=?, date_debut=?, date_fin=?, category_id=?, adresse=? WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(req);
          
        pst.setString(1, e.getTitre());
        pst.setString(2, e.getDescription());
        pst.setString(3, e.getDate_deb());
        pst.setString(4, e.getDate_fin());
        pst.setInt(5, id_cat);
        pst.setString(6, e.getAdresse());
        pst.setInt(7, id);
          
        pst.executeUpdate();
        System.out.println("Event modifié avec succès");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());        
    }   
}
      
      public void supprimerEvent(int id) {
     try {
          cnx=MyConnection.getInstance().getCnx();
            String req = "DELETE FROM evennement WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("event deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    
}
