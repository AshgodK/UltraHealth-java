/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class ConnectionRdv {
    public String url="jdbc:mysql://localhost:3306/java"; 
    public String login="root";
    public String pwd="";
    Connection cnx;
    public static ConnectionRdv instance;
    
    private ConnectionRdv(){
        try {
           cnx = DriverManager.getConnection(url ,login ,pwd);
           System.out.println("Connexion etablie!");
        } catch (SQLException ex) {
           System.err.print(ex.getMessage());
        }
    }
    public Connection getCnx() {
        return cnx;
    }
    public static ConnectionRdv getInstance(){
        if(instance == null){
        instance = new ConnectionRdv();
        }
        return instance;
    }
}