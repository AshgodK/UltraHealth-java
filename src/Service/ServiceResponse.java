/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Question;
import Model.Response;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class ServiceResponse implements Interfaces.ResponseInterface{
    
       
    Connection cnx;
    
     public ServiceResponse() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void addResponse(Response r) {
          try {
            PreparedStatement stm = cnx.prepareStatement("insert into response (question_id, text, date) values (?,?,?)");
            
            stm.setInt(1, r.getQuestion().getId());
            stm.setString(2, r.getText());
            stm.setDate(3,  new java.sql.Date(r.getDate().getTime()));
           
            stm.executeUpdate();
            
            System.out.println("Commentaire ajouté avec succés!!!");
          
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    


    }

    @Override
    public void deleteResponse(int id) {

           
     try
    { 
      Statement st = cnx.createStatement();
      String req = "DELETE FROM response WHERE id = "+id+"";
                st.executeUpdate(req);      
      System.out.println("Commentaire supprimé avec succès...");
    } 
     catch (SQLException ex) {
       System.out.println(ex.getMessage());        
       }
        
    }

    @Override
    public void updateResponse(Response r) {

           try {
            String req = "update response set text=? where id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            
            ps.setString(1, r.getText());
          
            ps.setInt(2, (int) r.getId());
          
            ps.executeUpdate();
            System.out.println("Commentaire modifié avec succés!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Response> displayResponse() {
 List<Response> response = new ArrayList<>();
       try {
            String req = "select * from response ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Response r = new Response();
                r.setId(rs.getInt(1));
                Question q = new Question();
                q.setId(rs.getInt(2));
                r.setQuestion(q);
               
                r.setText(rs.getString("Text"));
            
                r.setDate(rs.getDate("Date"));
               
                response.add(r);
            }
            System.out.print(response);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
        

    }
    
    public List<Response> searchBy(String query) {
    List<Response> searchResults = new ArrayList<>();
    try {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM response WHERE LOWER(text) LIKE ?");
        ps.setString(1, "%" + query.toLowerCase() + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Response r = new Response();
                r.setId(rs.getInt(1));
                Question question = new Question();
                question.setId(rs.getInt(2));
                r.setQuestion(question);
              
                r.setText(rs.getString("Text"));
            
                r.setDate(rs.getDate("Date"));
            searchResults.add(r);
          //  System.out.println(searchResults);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return searchResults;
}

    
    
}
