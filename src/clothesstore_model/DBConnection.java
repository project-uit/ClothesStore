/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author quochung
 */
public class DBConnection {

    public DBConnection() {
    }
    
    public Connection getConnecttion()
    {       
        try{
            String url="jdbc:mysql://localhost:3306/clothesshop";
            String user = "root";
            String password= "tandieu";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,user,password);          
            return con;
        }catch(ClassNotFoundException  e)
        {
            System.out.println("Loading driver is failed");
        } catch (SQLException ex) {
           System.out.println("Error "+ex.getMessage());
        }
        return null;
    }
}
