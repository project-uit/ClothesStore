/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author quochung
 */
public class nhomhang_model extends RecursiveTreeObject<nhomhang_model>{
    private StringProperty tennhomhang;

    public nhomhang_model() {
    }

    public nhomhang_model(StringProperty tennhomhang) {
        this.tennhomhang = tennhomhang;
    }

    public StringProperty getTennhomhang() {
        return tennhomhang;
    }

    public void setTennhomhang(StringProperty tennhomhang) {
        this.tennhomhang = tennhomhang;
    }
    
        
    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "insert into nhomhang values(?)";
                    
            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1,tennhomhang.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {

            }
        }
        return false;
    }

    public boolean delete() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "delete from nhomhang where tennhomhang = ?";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1,tennhomhang.get());

                int check = ptm.executeUpdate();
                if (check != 0) {

                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {

            }
        }
        return false;
    }

     public ObservableList<nhomhang_model> getNHList()  {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<nhomhang_model> nhList = FXCollections.observableArrayList();
        
        if(con!=null){
            try (
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from nhomhang");)
              
            {
                while (rs.next()) {
                    StringProperty tennh;                    
                    String ten_nhomhang = rs.getString("tennhomhang"); 
                    tennh = new SimpleStringProperty(ten_nhomhang);
                    
                    nhomhang_model nhomhang = new nhomhang_model(tennh);
                    nhList.add(nhomhang);
                  
                }
               
            } catch (SQLException ex) {
                
            }
           
        }
        return nhList;
        
    }
      public void getNHList(JFXComboBox cmb)  {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();      
        if(con!=null){
            try (
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from nhomhang");)
              
            {
                while (rs.next()) {                                   
                    String ten_nhomhang = rs.getString("tennhomhang");                    
                    cmb.getItems().add(ten_nhomhang);             
                }
               
            } catch (SQLException ex) {
                
            }
           
        }     
    }
}
