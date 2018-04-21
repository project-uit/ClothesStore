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
import javafx.scene.control.TableView;

/**
 *
 * @author quochung
 */
public class NhaSanXuat extends RecursiveTreeObject<NhaSanXuat>{

    private StringProperty ten_nhasanxuat;

    public NhaSanXuat(StringProperty ten_nhasanxuat) {
        this.ten_nhasanxuat = ten_nhasanxuat;
    }
   
    public NhaSanXuat() {
    }

    public StringProperty getTen_nhasanxuat() {
        return ten_nhasanxuat;
    }

    public void setTen_nhasanxuat(StringProperty ten_nhasanxuat) {
        this.ten_nhasanxuat = ten_nhasanxuat;
    }
   
    public boolean isEmpty() {
        return ten_nhasanxuat.get().isEmpty();
    }
    
   
    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "insert into nhasanxuat(tennhasanxuat) values(?)";
                    
            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, ten_nhasanxuat.get());
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

            String query = "delete from nhasanxuat where tennhasanxuat = ?";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, ten_nhasanxuat.get());

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

     public ObservableList<NhaSanXuat> getNSXList()  {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<NhaSanXuat> nsxList = FXCollections.observableArrayList();
        
        if(con!=null){
            try (
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from nhasanxuat");)
              
            {
                while (rs.next()) {
                    StringProperty tennsx;                    
                    String ten_nsx = rs.getString("tennhasanxuat"); 
                    tennsx = new SimpleStringProperty(ten_nsx);
                    
                    NhaSanXuat cus = new NhaSanXuat(tennsx);
                    nsxList.add(cus);
                  
                }
               
            } catch (SQLException ex) {
                
            }
           
        }
        return nsxList;
        
    }
      public void getNSXList(JFXComboBox cmb)  {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();      
        if(con!=null){
            try (
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from nhasanxuat");)
              
            {
                while (rs.next()) {                                   
                    String ten_nsx = rs.getString("tennhasanxuat");                    
                    cmb.getItems().add(ten_nsx);             
                }
               
            } catch (SQLException ex) {
                
            }
           
        }     
    }

   

   
}
