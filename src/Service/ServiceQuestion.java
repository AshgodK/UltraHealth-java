/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

//import Gui.ConnectionController;
import Interfaces.QuestionInterface;
import Model.Question;
import Model.Response;
import Utils.MyConnection;
import gui.ConnectionController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author hp
 */
public class ServiceQuestion implements QuestionInterface{
      Connection cnx;
    
     public ServiceQuestion() {
        cnx = MyConnection.getInstance().getCnx();
    }
     
    public void addQuestion(Question question) {
        try {
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        
            PreparedStatement stm = cnx.prepareStatement("insert into question (title, categorie, createDate, content) values (?,?,?,?)");
            
          
            stm.setString(1, question.getTitle());
            stm.setString(2, question.getCategorie());    
            stm.setDate(3, sqlDate );

            stm.setString(4, question.getContent());

                
            stm.executeUpdate();
            
            System.out.println("Post ajouté avec succés!!!");
          
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
   
    public List<Question> searchByTitle(String query) {
        List<Question> searchResults = new ArrayList<>();
    try {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM question WHERE LOWER(title) LIKE ?");
        ps.setString(1, "%" + query.toLowerCase() + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Question p = new Question();
                p.setTitle(rs.getString("title"));
                p.setCategorie(rs.getString("categorie"));              
                p.setCreateDate(rs.getDate("createDate"));
                p.setContent(rs.getString("content"));

              
            searchResults.add(p);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return searchResults;
    }
       
    @Override
    public void updateQuestion(Question p) {
        
        try {
            String req = "update question set title=?,categorie=?,content=?,create_Date=? where id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getCategorie());
            ps.setString(3, p.getContent());
           ps.setDate(4, new java.sql.Date(p.getCreateDate().getTime()) );
            ps.setInt(5, (int) p.getId());
          
            ps.executeUpdate();
            System.out.println("Post modifié avec succés!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
     @Override
    public void deleteQuestion(int id) {   
        
     try {
         Statement st = cnx.createStatement();
         String req = "DELETE FROM response WHERE question_id  = " + id; // delete associated comments first
         st.executeUpdate(req);

         String req2 = "DELETE FROM question WHERE id = " + id; // delete associated rates first
         st.executeUpdate(req2);

        
         System.out.println("Post deleted successfully.");
     } catch (SQLException ex) {
         System.out.println(ex.getMessage());        
     }
    
} 
    
      @Override
    public int nbrComments(int id) {
         List<Response> cmnts = new ArrayList<>();
        try {
            String req = "select * from response where question_id  = " +id+"";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
              Response c = new Response();
                c.setId(rs.getInt(1));
                Question post = new Question();
                post.setId(rs.getInt(2));
                c.setQuestion(post);
          
                c.setText(rs.getString("Text"));
            
                c.setDate(rs.getDate("Date"));
               
                cmnts.add(c);
    
            }
            System.out.print(cmnts);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cmnts.size(); 
    }
    
      @Override
    public ObservableList<Question> displayPosts() {
       ObservableList<Question> posts = FXCollections.observableArrayList();
       try {
            String req = "select * from question ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Question p = new Question();
                p.setTitle(rs.getString("title"));
                p.setCategorie(rs.getString("categorie"));
                p.setContent(rs.getString("content"));
                p.setCreateDate(rs.getDate("createDate"));
                posts.add(p);
            }
            System.out.print(posts);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }

    
    


}
