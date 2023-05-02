/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author pc
 */
import java.sql.*;

public class Base {
    private Connection cnx;
    private String url;
    private String username;
    private String password;

    public Base(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password =password;
    }

    public Connection connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        try {
            this.cnx = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.println(this.cnx + " : connection established !");
        } catch (SQLException e) {
            System.err.println("Error connecting to MySQL database: " + e.getMessage());
        }
        
        return cnx;
    }

    public ResultSet useStatment(String sqlQuery) throws SQLException{
        try  {
            Statement stmt = this.cnx.createStatement();
            ResultSet result = stmt.executeQuery(sqlQuery);
            
            return result;
        }catch(SQLException ex){
            System.out.print("requette filed : ");
            System.out.println(ex.toString());
        }
        return null;
    }

    public int insertIntoBase(String sqlQuery) throws ClassNotFoundException, SQLException{
        int result;
        PreparedStatement stm = this.connection().prepareStatement(sqlQuery);
        result = stm.executeUpdate();
        return result;

    }

    public int  deleteFrom(String sqlQuery) throws SQLException, ClassNotFoundException {
        int result;
        PreparedStatement stm = this.connection().prepareStatement(sqlQuery);
        result = stm.executeUpdate();
        return result;
    }
}
