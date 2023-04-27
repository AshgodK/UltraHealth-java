/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entity.passe;
import java.sql.Connection;
import java.sql.Statement;
import util.MyConnection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


/**
 *
 * @author pc
 */
public class passeServ extends passe{
    
    Connection cnx;
    
    public void passeServ()
     {
         cnx=MyConnection.getInstance().getCnx();
     }
    
    public void AjouterEvent(passe p,int id_ev)
    {
     try {
          cnx=MyConnection.getInstance().getCnx();
          String req = "insert into passe (evennement_id,  code, prix)"
                        +"values('"+id_ev+"','"+p.getCode()+"','"+p.getPriEvent() +"')";
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("ticket ajouter avec succ");
            } catch (SQLException ex) {
                
                System.out.println(ex.getMessage());        
            }   
    
    
    }
    
    
}
