/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hp
 */
public class MyConnection {
      public String url="jdbc:mysql://localhost:3306/ultrahealth";
    public String login="root";
    public String pwd="";
    Connection cnx;
    public static MyConnection instance;
    
    
     private MyConnection(){
        try {
           cnx = DriverManager.getConnection(url ,login ,pwd);
           System.out.println("Connexion établie!");
        } catch (SQLException ex) {
           System.err.print(ex.getMessage());
        }
    }
     
     
     
     public static MyConnection getInstance(){
        if(instance == null){
        instance = new MyConnection();
        }
        return instance;
    }
     
      public Connection getCnx() {
        return cnx;
    }
    
}
